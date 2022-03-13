package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DocumentDescriptionPojo;
import com.databasir.dao.tables.records.DocumentDescriptionRecord;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.databasir.dao.Tables.DOCUMENT_DESCRIPTION;

@Repository
public class DocumentDescriptionDao extends BaseDao<DocumentDescriptionPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DocumentDescriptionDao() {
        super(DOCUMENT_DESCRIPTION, DocumentDescriptionPojo.class);
    }

    public boolean exists(Integer projectId, String tableName, String columnName) {
        Condition condition = DOCUMENT_DESCRIPTION.PROJECT_ID.eq(projectId)
                .and(DOCUMENT_DESCRIPTION.TABLE_NAME.eq(tableName));
        if (columnName == null) {
            condition = condition.and(DOCUMENT_DESCRIPTION.COLUMN_NAME.isNull());
        } else {
            condition = condition.and(DOCUMENT_DESCRIPTION.COLUMN_NAME.eq(columnName));
        }
        return this.exists(condition);
    }

    public void update(DocumentDescriptionPojo pojo) {
        Condition condition = DOCUMENT_DESCRIPTION.TABLE_NAME.eq(pojo.getTableName());
        if (pojo.getColumnName() == null) {
            condition = condition.and(DOCUMENT_DESCRIPTION.COLUMN_NAME.isNull());
        } else {
            condition = condition.and(DOCUMENT_DESCRIPTION.COLUMN_NAME.eq(pojo.getColumnName()));
        }
        DocumentDescriptionRecord record = getDslContext().newRecord(DOCUMENT_DESCRIPTION, pojo);
        getDslContext().executeUpdate(record, condition);
    }

    public List<DocumentDescriptionPojo> selectByProjectId(Integer projectId) {
        return selectByCondition(DOCUMENT_DESCRIPTION.PROJECT_ID.eq(projectId));
    }

    public List<DocumentDescriptionPojo> selectTableDescriptionByProjectId(Integer projectId) {
        return selectByCondition(DOCUMENT_DESCRIPTION.PROJECT_ID.eq(projectId)
                .and(DOCUMENT_DESCRIPTION.COLUMN_NAME.isNull()));
    }

    public List<DocumentDescriptionPojo> selectByCondition(Condition condition) {
        return this.getDslContext()
                .selectFrom(DOCUMENT_DESCRIPTION).where(condition)
                .fetchInto(DocumentDescriptionPojo.class);
    }
}
