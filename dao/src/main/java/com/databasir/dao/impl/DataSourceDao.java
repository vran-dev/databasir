package com.databasir.dao.impl;

import com.databasir.dao.exception.DataNotExistsException;
import com.databasir.dao.tables.pojos.DataSource;
import com.databasir.dao.tables.records.DataSourceRecord;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.databasir.dao.Tables.DATA_SOURCE;

@Repository
public class DataSourceDao extends BaseDao<DataSource> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DataSourceDao() {
        super(DATA_SOURCE, DataSource.class);
    }

    public Optional<DataSource> selectOptionalByProjectId(Integer projectId) {
        return getDslContext()
                .select(DATA_SOURCE.fields()).from(DATA_SOURCE).where(DATA_SOURCE.PROJECT_ID.eq(projectId))
                .fetchOptionalInto(DataSource.class);
    }

    public DataSource selectByProjectId(Integer projectId) {
        return getDslContext()
                .select(DATA_SOURCE.fields()).from(DATA_SOURCE).where(DATA_SOURCE.PROJECT_ID.eq(projectId))
                .fetchOptionalInto(DataSource.class)
                .orElseThrow(() -> new DataNotExistsException("data not exists in "
                        + table().getName()
                        + " with schemaSourceId = "
                        + projectId));
    }

    public int updateByProjectId(DataSource dataSource) {
        DataSourceRecord record = getDslContext().newRecord(DATA_SOURCE, dataSource);
        record.changed(DATA_SOURCE.ID, false);
        record.changed(DATA_SOURCE.PROJECT_ID, false);
        if (dataSource.getPassword() == null || dataSource.getPassword().trim().equals("")) {
            record.changed(DATA_SOURCE.PASSWORD, false);
        }
        return getDslContext()
                .update(DATA_SOURCE).set(record).where(DATA_SOURCE.PROJECT_ID.eq(dataSource.getProjectId()))
                .execute();
    }

    public List<DataSource> selectInProjectIds(List<Integer> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .select(DATA_SOURCE.fields()).from(DATA_SOURCE).where(DATA_SOURCE.PROJECT_ID.in(projectIds))
                .fetchInto(DataSource.class);
    }
}
