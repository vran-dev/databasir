package com.databasir.core.domain.project.service;

import com.databasir.core.BaseTest;
import com.databasir.core.domain.project.data.DataSourcePropertyValue;
import com.databasir.core.domain.project.data.ProjectCreateRequest;
import com.databasir.core.domain.project.data.ProjectCreateRequest.DataSourceCreateRequest;
import com.databasir.core.domain.project.data.ProjectCreateRequest.ProjectSyncRuleCreateRequest;
import com.databasir.core.domain.project.data.ProjectTestConnectionRequest;
import com.databasir.core.domain.project.data.ProjectUpdateRequest;
import com.databasir.core.domain.project.data.ProjectUpdateRequest.ProjectSyncRuleUpdateRequest;
import com.databasir.core.infrastructure.connection.DatabaseTypes;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.ProjectSyncRuleDao;
import com.databasir.dao.tables.pojos.ProjectSyncRulePojo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
class ProjectServiceTest extends BaseTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectSyncRuleDao projectSyncRuleDao;

    @Value("${databasir.db.username}")
    private String dbUsername;

    @Value("${databasir.db.password}")
    private String dbPassword;

    @Value("${databasir.db.url}")
    private String dbUrl;

    @Test
    void create() {
        ProjectCreateRequest request = new ProjectCreateRequest();
        request.setName("ut");
        request.setDescription(Optional.of("integration test"));
        request.setGroupId(-1000);

        DataSourceCreateRequest dataSource = new DataSourceCreateRequest();
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setUrl("localhost:3306");
        dataSource.setDatabaseName("databasir");
        dataSource.setSchemaName("databasir");
        dataSource.setDatabaseType(DatabaseTypes.MYSQL);
        dataSource.setProperties(List.of(new DataSourcePropertyValue("useSSL", "false")));
        request.setDataSource(dataSource);

        ProjectSyncRuleCreateRequest syncRule = new ProjectSyncRuleCreateRequest();
        syncRule.setIgnoreTableNameRegexes(List.of("flywway.*", "demo.*"));
        syncRule.setIgnoreColumnNameRegexes(List.of("id.*", "demo.*"));
        syncRule.setIsAutoSync(true);
        syncRule.setAutoSyncCron("0 0 0-23 0 0 ? *");
        request.setProjectSyncRule(syncRule);

        Integer id = projectService.create(request);
        Assertions.assertNotNull(id);
    }

    @Test
    @Sql("classpath:sql/domain/project/Update.sql")
    void update() {
        ProjectUpdateRequest request = new ProjectUpdateRequest();
        request.setId(-1000);
        request.setName("ut");
        request.setDescription(Optional.of("integration test"));

        var dataSource = new ProjectUpdateRequest.DataSourceUpdateRequest();
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setUrl("localhost:3306");
        dataSource.setDatabaseName("databasir");
        dataSource.setSchemaName("databasir");
        dataSource.setDatabaseType(DatabaseTypes.MYSQL);
        dataSource.setProperties(List.of(new DataSourcePropertyValue("useSSL", "false")));
        request.setDataSource(dataSource);

        var syncRule = new ProjectSyncRuleUpdateRequest();
        syncRule.setIgnoreTableNameRegexes(List.of("flywway.*", "demo.*"));
        syncRule.setIgnoreColumnNameRegexes(List.of("id.*", "demo.*"));
        syncRule.setIsAutoSync(true);
        syncRule.setAutoSyncCron("0 0 0-23 0 0 ? *");
        request.setProjectSyncRule(syncRule);

        projectService.update(-999, request);
    }

    @Test
    @Sql("classpath:sql/domain/project/Delete.sql")
    void delete() {
        int projectId = -1000;
        projectService.delete(projectId);
        Assertions.assertFalse(projectDao.existsById(projectId));
        ProjectSyncRulePojo syncRule = projectSyncRuleDao.selectByProjectId(projectId);
        Assertions.assertNotNull(syncRule);
        Assertions.assertNotNull(syncRule.getAutoSyncCron());
        Assertions.assertFalse(syncRule.getIsAutoSync());
    }

    @Test
    void testConnection() {
        ProjectTestConnectionRequest request = new ProjectTestConnectionRequest();
        request.setUsername(dbUsername);
        request.setPassword(dbPassword);
        request.setUrl(dbUrl);
        request.setDatabaseName("databasir");
        request.setSchemaName("databasir");
        request.setDatabaseType(DatabaseTypes.MYSQL);
        request.setProperties(List.of(new DataSourcePropertyValue("useSSL", "false")));
        projectService.testConnection(request);
    }
}