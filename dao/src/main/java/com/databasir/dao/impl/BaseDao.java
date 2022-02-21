package com.databasir.dao.impl;

import com.databasir.dao.exception.DataNotExistsException;
import org.jooq.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseDao<R> {

    private final Table<?> table;

    private final Class<R> pojoType;

    public BaseDao(Table<?> table, Class<R> pojoType) {
        this.table = table;
        this.pojoType = pojoType;
    }

    public abstract DSLContext getDslContext();

    public <T extends Serializable> boolean existsById(T id) {
        return getDslContext().fetchExists(table, identity().eq(id));
    }

    public <T> T insertAndReturnId(R pojo) {
        Record record = getDslContext().newRecord(table, pojo);
        UpdatableRecord<?> updatableRecord = (UpdatableRecord<?>) record;
        updatableRecord.store();
        Object value = updatableRecord.getValue(table.getIdentity().getField());
        return (T) identityType().cast(value);
    }

    public int batchInsert(Collection<R> pojoList) {
        List<TableRecord<?>> records = pojoList.stream()
                .map(pojo -> {
                    Record record = getDslContext().newRecord(table, pojo);
                    return (TableRecord<?>) record;
                })
                .collect(Collectors.toList());
        return Arrays.stream(getDslContext().batchInsert(records).execute()).sum();
    }

    public <T extends Serializable> int deleteById(T id) {
        return getDslContext()
                .deleteFrom(table).where(identity().eq(id))
                .execute();
    }

    public int updateById(R pojo) {
        Record record = getDslContext().newRecord(table, pojo);
        record.changed(table.getIdentity().getField(), false);
        return getDslContext().executeUpdate((UpdatableRecord<?>) record);
    }

    public <T extends Serializable> Optional<R> selectOptionalById(T id) {
        return getDslContext()
                .select(table.fields()).from(table).where(identity().eq(id))
                .fetchOptionalInto(pojoType);
    }

    public <T extends Serializable> R selectById(T id) {
        return selectOptionalById(id)
                .orElseThrow(() ->
                        new DataNotExistsException("data not exists in " + table.getName() + " with id = " + id));
    }

    public List<R> selectInIds(List<? extends Serializable> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .select(table().fields()).from(table())
                .where(identity().in(ids))
                .fetchInto(pojoType);
    }

    public Page<R> selectByPage(Pageable request, Condition condition) {
        Integer count = getDslContext()
                .selectCount().from(table).where(condition)
                .fetchOne(0, int.class);
        int total = count == null ? 0 : count;
        List<R> data = getDslContext()
                .selectFrom(table).where(condition)
                .orderBy(getSortFields(request.getSort()))
                .offset(request.getOffset()).limit(request.getPageSize())
                .fetchInto(pojoType);
        return new PageImpl<>(data, request, total);
    }

    protected Collection<SortField<?>> getSortFields(Sort sortSpecification) {
        Collection<SortField<?>> querySortFields = new ArrayList<>();
        if (sortSpecification == null) {
            return querySortFields;
        }
        Iterator<Sort.Order> specifiedFields = sortSpecification.iterator();
        while (specifiedFields.hasNext()) {
            Sort.Order specifiedField = specifiedFields.next();
            String sortFieldName = specifiedField.getProperty();
            Field<?> field = table().field(sortFieldName);
            TableField tableField = (TableField) field;
            if (specifiedField.getDirection() == Sort.Direction.ASC) {
                querySortFields.add(tableField.asc());
            } else {
                querySortFields.add(tableField.desc());
            }
        }
        return querySortFields;
    }

    protected <T extends Serializable> Field<T> identity() {
        Identity<?, ?> identity = table.getIdentity();
        if (identity == null) {
            throw new IllegalStateException("can not find identity column in " + table.getName());
        }
        return identity.getField().cast(identityType());
    }

    protected <T extends Serializable> Class<T> identityType() {
        return (Class<T>) Integer.class;
    }

    protected Table<?> table() {
        return this.table;
    }
}
