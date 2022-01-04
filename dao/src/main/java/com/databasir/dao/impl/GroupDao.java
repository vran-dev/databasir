package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.GroupPojo;
import com.databasir.dao.tables.records.GroupRecord;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.databasir.dao.Tables.GROUP;

@Repository
public class GroupDao extends BaseDao<GroupRecord, GroupPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public GroupDao() {
        super(GROUP, GroupPojo.class);
    }

    @Override
    public int deleteById(Integer id) {
        return dslContext
                .update(table()).set(GROUP.DELETED, true).where(GROUP.ID.eq(id))
                .execute();
    }

    @Override
    public Page<GroupPojo> selectByPage(Pageable request, Condition condition) {
        return super.selectByPage(request, condition.and(GROUP.DELETED.eq(false)));
    }

    @Override
    public Optional<GroupPojo> selectOptionalById(Integer id) {
        return getDslContext()
                .select(GROUP.fields()).from(GROUP).where(GROUP.ID.eq(id).and(GROUP.DELETED.eq(false)))
                .fetchOptionalInto(GroupPojo.class);
    }

    @Override
    public List<GroupPojo> selectInIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .select(GROUP.fields()).from(GROUP)
                .where(GROUP.ID.in(ids)).and(GROUP.DELETED.eq(false))
                .fetchInto(GroupPojo.class);
    }
}