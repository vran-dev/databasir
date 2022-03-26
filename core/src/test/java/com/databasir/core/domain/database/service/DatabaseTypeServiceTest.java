package com.databasir.core.domain.database.service;

import com.databasir.common.DatabasirException;
import com.databasir.core.BaseTest;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.database.data.DatabaseTypeCreateRequest;
import com.databasir.core.domain.database.data.DatabaseTypeUpdateRequest;
import com.databasir.dao.impl.DatabaseTypeDao;
import com.databasir.dao.tables.pojos.DatabaseTypePojo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class DatabaseTypeServiceTest extends BaseTest {

    @Autowired
    private DatabaseTypeService databaseTypeService;

    @Autowired
    private DatabaseTypeDao databaseTypeDao;

    @Test
    void create() {
        DatabaseTypeCreateRequest request = new DatabaseTypeCreateRequest();
        request.setDatabaseType("ut-mysql");
        request.setIcon("");
        request.setDescription("integration test");
        request.setJdbcDriverFileUrl("some url");
        request.setJdbcDriverClassName("com.mysql.jdbc.Driver");
        request.setJdbcProtocol("jdbc:mysql");
        request.setUrlPattern("{{jdbc.protocol}}//{{db.url}}/{{db.schema}}");
        Integer id = databaseTypeService.create(request);
        Assertions.assertNotNull(id);
    }

    @Test
    @Sql("classpath:sql/domain/database/CreateDuplicate.sql")
    void createWhenDatabaseTypeDuplicate() {
        DatabaseTypeCreateRequest request = new DatabaseTypeCreateRequest();
        request.setDatabaseType("ut-mysql");
        request.setIcon("");
        request.setDescription("integration test");
        request.setJdbcDriverFileUrl("some url");
        request.setJdbcDriverClassName("com.mysql.jdbc.Driver");
        request.setJdbcProtocol("jdbc:mysql");
        request.setUrlPattern("{{jdbc.protocol}}//{{db.url}}/{{db.schema}}");

        DatabasirException err = Assertions.assertThrows(DatabasirException.class,
                () -> databaseTypeService.create(request));
        Assertions.assertEquals(DomainErrors.DATABASE_TYPE_NAME_DUPLICATE.getErrCode(), err.getErrCode());
    }

    @Test
    @Sql("classpath:sql/domain/database/Update.sql")
    void update() {
        DatabaseTypeUpdateRequest request = new DatabaseTypeUpdateRequest();
        request.setId(-1000);
        request.setIcon("");
        request.setDatabaseType("new-type");
        request.setDescription("integration test");
        request.setJdbcDriverFileUrl("some url");
        request.setJdbcDriverClassName("com.mysql.jdbc.Driver");
        request.setJdbcProtocol("jdbc:postgresql");
        request.setUrlPattern("{{jdbc.protocol}}//{{db.url}}/{{db.schema}}");
        databaseTypeService.update(request);

        DatabaseTypePojo pojo = databaseTypeDao.selectByDatabaseType("new-type");
        Assertions.assertNotNull(pojo);
        Assertions.assertEquals("integration test", pojo.getDescription());
        Assertions.assertEquals("jdbc:postgresql", pojo.getJdbcProtocol());
        Assertions.assertEquals("{{jdbc.protocol}}//{{db.url}}/{{db.schema}}", pojo.getUrlPattern());
    }

    @Test
    @Sql("classpath:sql/domain/database/DeleteById.sql")
    void deleteById() {
        int id = -1000;
        databaseTypeService.deleteById(id);
        Assertions.assertFalse(databaseTypeDao.existsById(id));
    }

    @Test
    void deleteByIdWhenNotExists() {
        int id = -1000;
        databaseTypeService.deleteById(id);
        Assertions.assertFalse(databaseTypeDao.existsById(id));
    }

}