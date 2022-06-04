package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.OperationLog;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

import static com.databasir.dao.Tables.OPERATION_LOG;

@Repository
public class OperationLogDao extends BaseDao<OperationLog> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public OperationLogDao() {
        super(OPERATION_LOG, OperationLog.class);
    }

    @Override
    protected <T extends Serializable> Class<T> identityType() {
        return (Class<T>) Long.class;
    }
}