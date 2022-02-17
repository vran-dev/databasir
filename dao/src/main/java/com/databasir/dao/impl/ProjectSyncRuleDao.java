package com.databasir.dao.impl;

import com.databasir.dao.exception.DataNotExistsException;
import com.databasir.dao.tables.ProjectSyncRule;
import com.databasir.dao.tables.pojos.ProjectSyncRulePojo;
import com.databasir.dao.tables.records.ProjectSyncRuleRecord;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.databasir.dao.Tables.PROJECT_SYNC_RULE;

@Repository
public class ProjectSyncRuleDao extends BaseDao<ProjectSyncRulePojo> {

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

    public void deleteByProjectId(Integer projectId) {
        getDslContext()
                .deleteFrom(PROJECT_SYNC_RULE).where(PROJECT_SYNC_RULE.PROJECT_ID.eq(projectId))
                .execute();
    }

    public List<ProjectSyncRulePojo> selectInProjectIds(List<Integer> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .selectFrom(PROJECT_SYNC_RULE).where(PROJECT_SYNC_RULE.PROJECT_ID.in(projectIds))
                .fetchInto(ProjectSyncRulePojo.class);
    }

    public List<ProjectSyncRulePojo> selectByIsAutoSyncAndProjectIds(boolean isAutoSync, List<Integer> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .selectFrom(PROJECT_SYNC_RULE).where(
                        PROJECT_SYNC_RULE.IS_AUTO_SYNC.eq(isAutoSync)
                                .and(PROJECT_SYNC_RULE.PROJECT_ID.in(projectIds))
                )
                .fetchInto(ProjectSyncRulePojo.class);
    }

    public List<ProjectSyncRulePojo> selectByIsAutoSyncAndNotInProjectIds(boolean isAutoSync, List<Integer> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            return getDslContext()
                    .selectFrom(PROJECT_SYNC_RULE)
                    .where(PROJECT_SYNC_RULE.IS_AUTO_SYNC.eq(isAutoSync)
                            .and(PROJECT_SYNC_RULE.PROJECT_ID.notIn(projectIds)))
                    .fetchInto(ProjectSyncRulePojo.class);
        } else {
            return getDslContext()
                    .selectFrom(PROJECT_SYNC_RULE).where(PROJECT_SYNC_RULE.IS_AUTO_SYNC.eq(isAutoSync))
                    .fetchInto(ProjectSyncRulePojo.class);
        }
    }

    public boolean existsByProjectIdAndCron(int projectId, String cron) {
        return dslContext.fetchExists(PROJECT_SYNC_RULE,
                PROJECT_SYNC_RULE.PROJECT_ID.eq(projectId)
                        .and(PROJECT_SYNC_RULE.AUTO_SYNC_CRON.eq(cron)));
    }

    public void disableAutoSyncByProjectId(Integer projectId) {
        dslContext
                .update(PROJECT_SYNC_RULE).set(PROJECT_SYNC_RULE.IS_AUTO_SYNC, false)
                .where(PROJECT_SYNC_RULE.PROJECT_ID.eq(projectId))
                .execute();

    }

    public void disableAutoSyncByProjectIds(List<Integer> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            return;
        }
        dslContext
                .update(PROJECT_SYNC_RULE).set(PROJECT_SYNC_RULE.IS_AUTO_SYNC, false)
                .where(PROJECT_SYNC_RULE.PROJECT_ID.in(projectIds))
                .execute();
    }
}
