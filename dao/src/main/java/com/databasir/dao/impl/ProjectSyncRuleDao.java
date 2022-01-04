package com.databasir.dao.impl;

import com.databasir.dao.exception.DataNotExistsException;
import com.databasir.dao.tables.ProjectSyncRule;
import com.databasir.dao.tables.pojos.ProjectSyncRulePojo;
import com.databasir.dao.tables.records.ProjectSyncRuleRecord;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.databasir.dao.Tables.PROJECT_SYNC_RULE;

@Repository
public class ProjectSyncRuleDao extends BaseDao<ProjectSyncRuleRecord, ProjectSyncRulePojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public ProjectSyncRuleDao() {
        super(PROJECT_SYNC_RULE, ProjectSyncRulePojo.class);
    }

    public Optional<ProjectSyncRulePojo> selectOptionalByProjectId(Integer projectId) {
        return getDslContext()
                .select(PROJECT_SYNC_RULE.fields()).from(PROJECT_SYNC_RULE).where(PROJECT_SYNC_RULE.PROJECT_ID.eq(projectId))
                .fetchOptionalInto(ProjectSyncRulePojo.class);
    }

    public ProjectSyncRulePojo selectByProjectId(Integer projectId) {
        return getDslContext()
                .select(PROJECT_SYNC_RULE.fields()).from(PROJECT_SYNC_RULE).where(PROJECT_SYNC_RULE.PROJECT_ID.eq(projectId))
                .fetchOptionalInto(ProjectSyncRulePojo.class)
                .orElseThrow(() -> new DataNotExistsException("data not exists in " + table().getName() + " with projectId = " + projectId));
    }

    public int updateByProjectId(ProjectSyncRulePojo rule) {
        ProjectSyncRule table = PROJECT_SYNC_RULE;
        ProjectSyncRuleRecord record = getDslContext().newRecord(table, rule);
        record.changed(table.ID, false);
        record.changed(table.PROJECT_ID, false);
        return getDslContext()
                .update(table).set(record).where(table.PROJECT_ID.eq(rule.getProjectId()))
                .execute();
    }
}
