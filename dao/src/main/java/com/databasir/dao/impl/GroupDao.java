package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.GroupPojo;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.databasir.dao.Tables.GROUP;

@Repository
public class GroupDao extends BaseDao<GroupPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public GroupDao() {
        super(GROUP, GroupPojo.class);
    }

    @Override
    public <T extends Serializable> int deleteById(T id) {
        return dslContext
                .update(table()).set(GROUP.DELETED, true).where(GROUP.ID.eq((Integer) id))
                .execute();
    }

    @Override
    public Page<GroupPojo> selectByPage(Pageable request, Condition condition) {
        return super.selectByPage(request, condition.and(GROUP.DELETED.eq(false)));
    }

    @Override
    public <T extends Serializable> Optional<GroupPojo> selectOptionalById(T id) {
        return getDslContext()
                .select(GROUP.fields()).from(GROUP).where(GROUP.ID.eq((Integer) id).and(GROUP.DELETED.eq(false)))
                .fetchOptionalInto(GroupPojo.class);
    }

    @Override
    public List<GroupPojo> selectInIds(List<? extends Serializable> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .select(GROUP.fields()).from(GROUP)
                .where(GROUP.ID.in(ids)).and(GROUP.DELETED.eq(false))
                .fetchInto(GroupPojo.class);
    }

    /**
     * with deleted
     */
    public List<GroupPojo> selectAllInIds(List<? extends Serializable> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .select(GROUP.fields()).from(GROUP)
                .where(GROUP.ID.in(ids))
                .fetchInto(GroupPojo.class);
    }

    public List<GroupPojo> selectByName(String nameContains) {
        return getDslContext()
                .select(GROUP.fields()).from(GROUP)
                .where(GROUP.NAME.contains(nameContains))
                .and(GROUP.DELETED.eq(false))
                .fetchInto(GroupPojo.class);
    }
}