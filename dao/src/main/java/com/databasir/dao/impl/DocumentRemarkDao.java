package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DocumentRemarkPojo;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.databasir.dao.Tables.DOCUMENT_REMARK;

@Repository
public class DocumentRemarkDao extends BaseDao<DocumentRemarkPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DocumentRemarkDao() {
        super(DOCUMENT_REMARK, DocumentRemarkPojo.class);
    }

    public Optional<DocumentRemarkPojo> selectByProjectIdAndId(Integer projectId, Integer id) {
        return this.getDslContext()
                .selectFrom(DOCUMENT_REMARK).where(DOCUMENT_REMARK.PROJECT_ID.eq(projectId)
                        .and(DOCUMENT_REMARK.ID.eq(id)))
                .fetchOptionalInto(DocumentRemarkPojo.class);
    }
}
