package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DataSourcePropertyPojo;
import com.databasir.dao.tables.records.DataSourcePropertyRecord;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.databasir.dao.Tables.DATA_SOURCE_PROPERTY;


@Repository
public class DataSourcePropertyDao extends BaseDao<DataSourcePropertyRecord, DataSourcePropertyPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DataSourcePropertyDao() {
        super(DATA_SOURCE_PROPERTY, DataSourcePropertyPojo.class);
    }

    public int deleteByDataSourceId(Integer dataSourceId) {
        return dslContext
                .deleteFrom(DATA_SOURCE_PROPERTY).where(DATA_SOURCE_PROPERTY.DATA_SOURCE_ID.eq(dataSourceId))
                .execute();
    }

    public List<DataSourcePropertyPojo> selectByDataSourceId(Integer id) {
        return dslContext
                .select(DATA_SOURCE_PROPERTY.fields()).from(DATA_SOURCE_PROPERTY)
                .where(DATA_SOURCE_PROPERTY.DATA_SOURCE_ID.eq(id))
                .fetchInto(DataSourcePropertyPojo.class);
    }
}
