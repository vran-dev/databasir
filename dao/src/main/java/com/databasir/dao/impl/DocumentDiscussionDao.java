package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DocumentDiscussionPojo;
import com.databasir.dao.value.DocumentDiscussionCountPojo;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.databasir.dao.Tables.DOCUMENT_DISCUSSION;

@Repository
public class DocumentDiscussionDao extends BaseDao<DocumentDiscussionPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DocumentDiscussionDao() {
        super(DOCUMENT_DISCUSSION, DocumentDiscussionPojo.class);
    }

    public Optional<DocumentDiscussionPojo> selectByProjectIdAndId(Integer projectId, Integer id) {
        return this.getDslContext()
                .selectFrom(DOCUMENT_DISCUSSION).where(DOCUMENT_DISCUSSION.PROJECT_ID.eq(projectId)
                        .and(DOCUMENT_DISCUSSION.ID.eq(id)))
                .fetchOptionalInto(DocumentDiscussionPojo.class);
    }

    public List<DocumentDiscussionCountPojo> selectTableDiscussionCount(Integer projectId) {
        return this.selectDiscussionCount(DOCUMENT_DISCUSSION.PROJECT_ID.eq(projectId)
                .and(DOCUMENT_DISCUSSION.COLUMN_NAME.isNull()));
    }

    public List<DocumentDiscussionCountPojo> selectAllDiscussionCount(Integer projectId) {
        return this.selectDiscussionCount(DOCUMENT_DISCUSSION.PROJECT_ID.eq(projectId));
    }

    public List<DocumentDiscussionCountPojo> selectDiscussionCount(Condition condition) {
        return this.getDslContext()
                .select(DSL.count(), DOCUMENT_DISCUSSION.TABLE_NAME, DOCUMENT_DISCUSSION.COLUMN_NAME)
                .from(DOCUMENT_DISCUSSION)
                .where(condition)
                .groupBy(DOCUMENT_DISCUSSION.TABLE_NAME, DOCUMENT_DISCUSSION.COLUMN_NAME)
                .fetchInto(DocumentDiscussionCountPojo.class);
    }
}
