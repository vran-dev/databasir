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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

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
        List<String> ignoreFields = List.of("createAt", "discussionCount", "id");
        BiFunction<Field, DocumentTemplatePropertyType, DocumentTemplatePropertyPojo> mapping = (field, type) -> {
            String key = field.getName();
            String def = field.getName();
            DocumentTemplatePropertyPojo pojo = new DocumentTemplatePropertyPojo();
            pojo.setType(type);
            pojo.setKey(key);
            pojo.setDefaultValue(def);
            return pojo;
        };
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
        properties.addAll(columnProperties);
        properties.addAll(indexProperties);
        properties.addAll(fkProperties);
        properties.addAll(triggerProperties);
        documentTemplatePropertyDao.batchInsertOnDuplicateIgnore(properties);

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
            role.setRole("SYS_OWNER");
            userRoleDao.insertAndReturnId(role);
        }
    }

}
