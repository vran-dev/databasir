package com.databasir.dao.impl;


import com.databasir.dao.exception.DataNotExistsException;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class BaseDao<T extends Record, R> {

    private final Table<T> table;

    private final Class<R> pojoType;

    public abstract DSLContext getDslContext();

    public boolean existsById(Integer id) {
        return getDslContext().fetchExists(table, identity().eq(id));
    }

    public Integer insertAndReturnId(R pojo) {
        T record = getDslContext().newRecord(table, pojo);
        UpdatableRecord<?> updatableRecord = (UpdatableRecord<?>) record;
        updatableRecord.store();
        Object value = updatableRecord.getValue(table.getIdentity().getField());
        return (Integer) value;
    }

    public int batchInsert(Collection<R> pojoList) {
        List<TableRecord<?>> records = pojoList.stream()
                .map(pojo -> {
                    T record = getDslContext().newRecord(table, pojo);
                    return (TableRecord<?>) record;
                })
                .collect(Collectors.toList());
        return Arrays.stream(getDslContext().batchInsert(records).execute()).sum();
    }

    public int deleteById(Integer id) {
        return getDslContext()
                .deleteFrom(table).where(identity().eq(id))
                .execute();
    }

    public int updateById(R pojo) {
        T record = getDslContext().newRecord(table, pojo);
        record.changed(table.getIdentity().getField(), false);
        return getDslContext().executeUpdate((UpdatableRecord<?>) record);
    }

    public Optional<R> selectOptionalById(Integer id) {
        return getDslContext()
                .select(table.fields()).from(table).where(identity().eq(id))
                .fetchOptionalInto(pojoType);
    }

    public R selectById(Integer id) {
        return selectOptionalById(id)
                .orElseThrow(() -> new DataNotExistsException("data not exists in " + table.getName() + " with id = " + id));
    }

    public List<R> selectInIds(List<Integer> ids) {
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

    protected Field<Integer> identity() {
        Identity<T, ?> identity = table.getIdentity();
        if (identity == null) {
            throw new IllegalStateException("can not find identity column in " + table.getName());
        }
        return identity.getField().cast(Integer.class);
    }

    protected Table<T> table() {
        return this.table;
    }
}
