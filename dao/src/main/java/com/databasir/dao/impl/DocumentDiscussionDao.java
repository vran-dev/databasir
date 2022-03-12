package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DocumentRemarkPojo;
import com.databasir.dao.value.DocumentDiscussionCountPojo;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.databasir.dao.Tables.DOCUMENT_REMARK;

@Repository
public class DocumentDiscussionDao extends BaseDao<DocumentRemarkPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DocumentDiscussionDao() {
        super(DOCUMENT_REMARK, DocumentRemarkPojo.class);
    }

    public Optional<DocumentRemarkPojo> selectByProjectIdAndId(Integer projectId, Integer id) {
        return this.getDslContext()
                .selectFrom(DOCUMENT_REMARK).where(DOCUMENT_REMARK.PROJECT_ID.eq(projectId)
                        .and(DOCUMENT_REMARK.ID.eq(id)))
                .fetchOptionalInto(DocumentRemarkPojo.class);
    }

    public List<DocumentDiscussionCountPojo> selectTableDiscussionCount(Integer projectId) {
        return this.selectDiscussionCount(DOCUMENT_REMARK.PROJECT_ID.eq(projectId)
                .and(DOCUMENT_REMARK.COLUMN_NAME.isNull()));
    }

    public List<DocumentDiscussionCountPojo> selectAllDiscussionCount(Integer projectId) {
        return this.selectDiscussionCount(DOCUMENT_REMARK.PROJECT_ID.eq(projectId));
    }

    public List<DocumentDiscussionCountPojo> selectDiscussionCount(Condition condition) {
        return this.getDslContext()
                .select(DSL.count(), DOCUMENT_REMARK.TABLE_NAME, DOCUMENT_REMARK.COLUMN_NAME)
                .from(DOCUMENT_REMARK)
                .where(condition)
                .groupBy(DOCUMENT_REMARK.TABLE_NAME, DOCUMENT_REMARK.COLUMN_NAME)
                .fetchInto(DocumentDiscussionCountPojo.class);
    }
}
