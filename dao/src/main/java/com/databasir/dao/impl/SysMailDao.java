package com.databasir.dao.impl;

import com.databasir.dao.exception.DataNotExistsException;
import com.databasir.dao.tables.pojos.SysMailPojo;
import com.databasir.dao.tables.records.SysMailRecord;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.databasir.dao.Tables.SYS_MAIL;

@Repository
public class SysMailDao extends BaseDao<SysMailRecord, SysMailPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public SysMailDao() {
        super(SYS_MAIL, SysMailPojo.class);
    }

    public Optional<SysMailPojo> selectOptionTopOne() {
        return dslContext.select(SYS_MAIL.fields()).from(SYS_MAIL)
                .limit(1)
                .fetchOptionalInto(SysMailPojo.class);
    }

    public SysMailPojo selectTopOne() {
        return selectOptionTopOne()
                .orElseThrow(() -> new DataNotExistsException("no sysmail data find"));
    }

    @Override
    public int updateById(SysMailPojo pojo) {
        SysMailRecord record = getDslContext().newRecord(SYS_MAIL, pojo);
        record.changed(SYS_MAIL.ID, false);
        if (pojo.getPassword() == null) {
            record.changed(SYS_MAIL.PASSWORD, false);
        }
        return getDslContext().executeUpdate(record);
    }
}