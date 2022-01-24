package com.databasir.dao.impl;

import com.databasir.dao.exception.DataNotExistsException;
import com.databasir.dao.tables.pojos.SysKeyPojo;
import com.databasir.dao.tables.records.SysKeyRecord;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.databasir.dao.Tables.SYS_KEY;

@Repository
public class SysKeyDao extends BaseDao<SysKeyRecord, SysKeyPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public SysKeyDao() {
        super(SYS_KEY, SysKeyPojo.class);
    }

    public Optional<SysKeyPojo> selectOptionTopOne() {
        return dslContext.select(SYS_KEY.fields()).from(SYS_KEY)
                .limit(1)
                .fetchOptionalInto(SysKeyPojo.class);
    }

    public SysKeyPojo selectTopOne() {
        return selectOptionTopOne()
                .orElseThrow(() -> new DataNotExistsException("no syskey data find"));
    }
}