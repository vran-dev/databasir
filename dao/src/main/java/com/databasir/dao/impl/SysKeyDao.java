package com.databasir.dao.impl;

import com.databasir.dao.exception.DataNotExistsException;
import com.databasir.dao.tables.pojos.SysKey;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.databasir.dao.Tables.SYS_KEY;

@Repository
public class SysKeyDao extends BaseDao<SysKey> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public SysKeyDao() {
        super(SYS_KEY, SysKey.class);
    }

    public Optional<SysKey> selectOptionTopOne() {
        return dslContext.select(SYS_KEY.fields()).from(SYS_KEY)
                .limit(1)
                .fetchOptionalInto(SysKey.class);
    }

    public SysKey selectTopOne() {
        return selectOptionTopOne()
                .orElseThrow(() -> new DataNotExistsException("no syskey data find"));
    }
}