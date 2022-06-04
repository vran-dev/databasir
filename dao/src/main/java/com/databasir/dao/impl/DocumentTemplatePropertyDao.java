package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DocumentTemplateProperty;
import com.databasir.dao.tables.records.DocumentTemplatePropertyRecord;
import lombok.Getter;
import org.jooq.DSLContext;
import org.jooq.InsertReturningStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.databasir.dao.Tables.DOCUMENT_TEMPLATE_PROPERTY;

@Repository
public class DocumentTemplatePropertyDao extends BaseDao<DocumentTemplateProperty> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DocumentTemplatePropertyDao() {
        super(DOCUMENT_TEMPLATE_PROPERTY, DocumentTemplateProperty.class);
    }

    public void batchInsertOnDuplicateUpdateDefaultValue(Collection<DocumentTemplateProperty> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        List<InsertReturningStep<DocumentTemplatePropertyRecord>> query = data.stream()
                .map(pojo -> getDslContext()
                        .insertInto(DOCUMENT_TEMPLATE_PROPERTY)
                        .set(getDslContext().newRecord(DOCUMENT_TEMPLATE_PROPERTY, pojo))
                        .onDuplicateKeyUpdate()
                        .set(DOCUMENT_TEMPLATE_PROPERTY.DEFAULT_VALUE, pojo.getDefaultValue()))
                .collect(Collectors.toList());
        getDslContext().batch(query).execute();
    }

    public void batchInsertOnDuplicateKeyUpdateValue(Collection<DocumentTemplateProperty> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        List<InsertReturningStep<DocumentTemplatePropertyRecord>> query = data.stream()
                .map(pojo -> getDslContext()
                        .insertInto(DOCUMENT_TEMPLATE_PROPERTY)
                        .set(getDslContext().newRecord(DOCUMENT_TEMPLATE_PROPERTY, pojo))
                        .onDuplicateKeyUpdate()
                        .set(DOCUMENT_TEMPLATE_PROPERTY.VALUE, pojo.getValue()))
                .collect(Collectors.toList());
        getDslContext().batch(query).execute();
    }
}
