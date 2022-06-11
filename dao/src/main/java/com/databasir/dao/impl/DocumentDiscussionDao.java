package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DocumentDiscussion;
import com.databasir.dao.value.DocumentDiscussionCountPojo;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.databasir.dao.Tables.DOCUMENT_DISCUSSION;

@Repository
public class DocumentDiscussionDao extends BaseDao<DocumentDiscussion> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DocumentDiscussionDao() {
        super(DOCUMENT_DISCUSSION, DocumentDiscussion.class);
    }

    public List<DocumentDiscussionCountPojo> selectAllDiscussionCount(Integer projectId) {
        return this.selectDiscussionCount(DOCUMENT_DISCUSSION.PROJECT_ID.eq(projectId));
    }

    public List<DocumentDiscussionCountPojo> selectDiscussionCount(Condition condition) {
        return this.getDslContext()
                .select(DSL.count(), DOCUMENT_DISCUSSION.TABLE_NAME)
                .from(DOCUMENT_DISCUSSION)
                .where(condition)
                .groupBy(DOCUMENT_DISCUSSION.TABLE_NAME)
                .fetchInto(DocumentDiscussionCountPojo.class);
    }
}
