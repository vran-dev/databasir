package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.common.codec.Aes;
import com.databasir.common.codec.Rsa;
import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.dao.enums.DocumentTemplatePropertyType;
import com.databasir.dao.impl.DocumentTemplatePropertyDao;
import com.databasir.dao.impl.SysKeyDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.impl.UserRoleDao;
import com.databasir.dao.tables.pojos.DocumentTemplatePropertyPojo;
import com.databasir.dao.tables.pojos.SysKeyPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import com.databasir.dao.tables.pojos.UserRolePojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.databasir.core.infrastructure.constant.RoleConstants.SYS_OWNER;

@Component
@Slf4j
@RequiredArgsConstructor
public class SystemStartedEventSubscriber {

    private final SysKeyDao sysKeyDao;

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    private final DocumentTemplatePropertyDao documentTemplatePropertyDao;

    @SuppressWarnings("checkstyle:all")
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @EventListener(classes = ContextRefreshedEvent.class)
    public void onStarted(ContextRefreshedEvent event) {
        log.info("begin to init system data");
        initSysOwnerIfNecessary();
        initDatabaseTypesIfNecessary();
        initTemplatePropertiesIfNecessary();
        log.info("system data init finished");
    }

    private void initTemplatePropertiesIfNecessary() {
        List<String> ignoreFields = List.of("createAt", "discussionCount", "id",
                "columns", "indexes", "triggers", "foreignKeys");
        Map<String, String> fieldChineseMap = fieldChineseMap();
        BiFunction<Field, DocumentTemplatePropertyType, DocumentTemplatePropertyPojo> mapping = (field, type) -> {
            String key = field.getName();
            String def = field.getName();
            DocumentTemplatePropertyPojo pojo = new DocumentTemplatePropertyPojo();
            pojo.setType(type);
            pojo.setKey(key);
            pojo.setDefaultValue(fieldChineseMap.getOrDefault(key, def));
            return pojo;
        };
        // table field name;
        Field[] fields = TableDocumentResponse.class.getDeclaredFields();
        List<DocumentTemplatePropertyPojo> tableProperties = Arrays.stream(fields)
                .filter(field -> !ignoreFields.contains(field.getName()))
                .map(field -> mapping.apply(field, DocumentTemplatePropertyType.TABLE_FIELD_NAME))
                .collect(Collectors.toList());

        // column field name;
        Field[] columnFields = TableDocumentResponse.ColumnDocumentResponse.class.getDeclaredFields();
        List<DocumentTemplatePropertyPojo> columnProperties = Arrays.stream(columnFields)
                .filter(f -> !ignoreFields.contains(f.getName()))
                .map(field -> mapping.apply(field, DocumentTemplatePropertyType.COLUMN_FIELD_NAME))
                .collect(Collectors.toList());

        // index field name;
        Field[] indexFields = TableDocumentResponse.IndexDocumentResponse.class.getDeclaredFields();
        List<DocumentTemplatePropertyPojo> indexProperties = Arrays.stream(indexFields)
                .filter(f -> !ignoreFields.contains(f.getName()))
                .map(field -> mapping.apply(field, DocumentTemplatePropertyType.INDEX_FIELD_NAME))
                .collect(Collectors.toList());

        // foreign key field name;
        Field[] fkFields = TableDocumentResponse.ForeignKeyDocumentResponse.class.getDeclaredFields();
        List<DocumentTemplatePropertyPojo> fkProperties = Arrays.stream(fkFields)
                .filter(f -> !ignoreFields.contains(f.getName()))
                .map(field -> mapping.apply(field, DocumentTemplatePropertyType.FOREIGN_KEY_FIELD_NAME))
                .collect(Collectors.toList());

        // trigger field name;
        Field[] triggerFields = TableDocumentResponse.TriggerDocumentResponse.class.getDeclaredFields();
        List<DocumentTemplatePropertyPojo> triggerProperties = Arrays.stream(triggerFields)
                .filter(f -> !ignoreFields.contains(f.getName()))
                .map(field -> mapping.apply(field, DocumentTemplatePropertyType.TRIGGER_FIELD_NAME))
                .collect(Collectors.toList());

        List<DocumentTemplatePropertyPojo> properties = new ArrayList<>();
        properties.addAll(tableProperties);
        properties.addAll(columnProperties);
        properties.addAll(indexProperties);
        properties.addAll(fkProperties);
        properties.addAll(triggerProperties);
        documentTemplatePropertyDao.batchInsertOnDuplicateUpdateDefaultValue(properties);

    }

    private Map<String, String> fieldChineseMap() {
        Map<String, String> map = new HashMap<>(32);
        map.put("name", "名称");
        map.put("type", "类型");
        map.put("comment", "注释");
        map.put("description", "描述");
        map.put("size", "长度");
        map.put("decimalDigits", "浮点精度");
        map.put("isPrimaryKey", "主键");
        map.put("nullable", "可空");
        map.put("autoIncrement", "自增");
        map.put("defaultValue", "默认值");
        map.put("isUnique", "唯一");
        map.put("columnNames", "列名");
        map.put("fkName", "外键名");
        map.put("fkTableName", "外键表名");
        map.put("fkColumnName", "外键列名");
        map.put("pkName", "主键名");
        map.put("pkTableName", "主键表名");
        map.put("pkColumnName", "主键列名");
        map.put("updateRule", "更新规则");
        map.put("deleteRule", "删除规则");
        map.put("timing", "触发时机");
        map.put("manipulation", "触发器");
        map.put("statement", "表达式");
        map.put("triggerCreateAt", "创建时间");
        return map;
    }

    private void initDatabaseTypesIfNecessary() {
        // TODO
    }

    private void initSysOwnerIfNecessary() {
        sysKeyDao.selectOptionTopOne()
                .orElseGet(() -> {
                    SysKeyPojo pojo = new SysKeyPojo();
                    pojo.setAesKey(Aes.randomBase64Key());
                    Rsa.RsaBase64Key key = Rsa.generateBase64Key();
                    pojo.setRsaPublicKey(key.getPublicBase64Key());
                    pojo.setRsaPrivateKey(key.getPrivateBase64Key());
                    sysKeyDao.insertAndReturnId(pojo);
                    return pojo;
                });

        String email = "N/A";
        String username = "databasir";
        Optional<UserPojo> userOpt = userDao.selectByEmailOrUsername(username);
        if (!userOpt.isPresent()) {
            UserPojo admin = new UserPojo();
            admin.setEmail(email);
            admin.setUsername(username);
            admin.setPassword(bCryptPasswordEncoder.encode(username));
            admin.setEnabled(true);
            admin.setNickname("Databasir Admin");
            Integer userId = userDao.insertAndReturnId(admin);
            UserRolePojo role = new UserRolePojo();
            role.setUserId(userId);
            role.setRole(SYS_OWNER);
            userRoleDao.insertAndReturnId(role);
        }
    }

}
