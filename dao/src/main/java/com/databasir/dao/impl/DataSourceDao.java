package com.databasir.dao.impl;

import com.databasir.dao.exception.DataNotExistsException;
import com.databasir.dao.tables.pojos.DataSourcePojo;
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
public class DataSourceDao extends BaseDao<DataSourcePojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DataSourceDao() {
        super(DATA_SOURCE, DataSourcePojo.class);
    }

    public Optional<DataSourcePojo> selectOptionalByProjectId(Integer projectId) {
        return getDslContext()
                .select(DATA_SOURCE.fields()).from(DATA_SOURCE).where(DATA_SOURCE.PROJECT_ID.eq(projectId))
                .fetchOptionalInto(DataSourcePojo.class);
    }

    public DataSourcePojo selectByProjectId(Integer projectId) {
        return getDslContext()
                .select(DATA_SOURCE.fields()).from(DATA_SOURCE).where(DATA_SOURCE.PROJECT_ID.eq(projectId))
                .fetchOptionalInto(DataSourcePojo.class)
                .orElseThrow(() -> new DataNotExistsException("data not exists in " + table().getName() + " with schemaSourceId = " + projectId));
    }

    public int updateByProjectId(DataSourcePojo dataSource) {
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

    public List<DataSourcePojo> selectInProjectIds(List<Integer> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .select(DATA_SOURCE.fields()).from(DATA_SOURCE).where(DATA_SOURCE.PROJECT_ID.in(projectIds))
                .fetchInto(DataSourcePojo.class);
    }
}
