package com.databasir.dao.impl;

import com.databasir.dao.Indexes;
import com.databasir.dao.tables.pojos.DocumentFullTextPojo;
import com.databasir.dao.value.FullTextProjectInfoUpdatePojo;
import lombok.Getter;
import org.jooq.DSLContext;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.databasir.dao.Tables.DOCUMENT_FULL_TEXT;

@Repository
public class DocumentFullTextDao extends BaseDao<DocumentFullTextPojo> {

    public static final String[] EMPTY = new String[0];

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DocumentFullTextDao() {
        super(DOCUMENT_FULL_TEXT, DocumentFullTextPojo.class);
    }

    public Page<DocumentFullTextPojo> selectColumnPage(Pageable pageable, String keyword) {
        String[] matchCols = Indexes.DOCUMENT_FULL_TEXT_FIDX_COLUMN.getFields()
                .stream()
                .map(f -> f.getName())
                .toArray(String[]::new);
        String colSegment = String.join(",", matchCols);
        // sample: match(col_name, col_comment) against('+databasir' in boolean mode)
        String fullTextMatchSqlSegment = new StringBuilder(64)
                .append("MATCH(").append(colSegment).append(") ")
                .append("AGAINST('+").append(keyword).append("' IN BOOLEAN MODE)")
                .toString();
        // count
        Integer total = dslContext.selectDistinct(DSL.count(DOCUMENT_FULL_TEXT.TABLE_COLUMN_DOCUMENT_ID))
                .from(DOCUMENT_FULL_TEXT)
                .where(DOCUMENT_FULL_TEXT.TABLE_COLUMN_DOCUMENT_ID.isNotNull().and(fullTextMatchSqlSegment))
                .fetchOne(0, int.class);
        // content
        List<DocumentFullTextPojo> content = dslContext.select(DOCUMENT_FULL_TEXT.fields())
                .from(DOCUMENT_FULL_TEXT)
                .where(DOCUMENT_FULL_TEXT.TABLE_COLUMN_DOCUMENT_ID.isNotNull().and(fullTextMatchSqlSegment))
                .limit(pageable.getOffset(), pageable.getPageSize())
                .fetchInto(DocumentFullTextPojo.class);
        return new PageImpl<>(content, pageable, total.longValue());
    }

    public Page<DocumentFullTextPojo> selectTablePage(Pageable pageable, String keyword) {
        String[] matchCols = Indexes.DOCUMENT_FULL_TEXT_FIDX_TABLE.getFields()
                .stream()
                .map(f -> f.getName())
                .toArray(String[]::new);
        TableField[] groupColumns = new TableField[]{
                DOCUMENT_FULL_TEXT.GROUP_ID,
                DOCUMENT_FULL_TEXT.GROUP_NAME,
                DOCUMENT_FULL_TEXT.GROUP_DESCRIPTION,
                DOCUMENT_FULL_TEXT.PROJECT_ID,
                DOCUMENT_FULL_TEXT.PROJECT_NAME,
                DOCUMENT_FULL_TEXT.PROJECT_DESCRIPTION,
                DOCUMENT_FULL_TEXT.DATABASE_NAME,
                DOCUMENT_FULL_TEXT.SCHEMA_NAME,
                DOCUMENT_FULL_TEXT.DATABASE_PRODUCT_NAME,
                DOCUMENT_FULL_TEXT.TABLE_DOCUMENT_ID,
                DOCUMENT_FULL_TEXT.TABLE_NAME,
                DOCUMENT_FULL_TEXT.TABLE_COMMENT,
                DOCUMENT_FULL_TEXT.TABLE_DESCRIPTION,
        };
        String colSegment = String.join(",", matchCols);

        // sample: match(col_name, col_comment) against('+databasir' in boolean mode)
        String fullTextMatchSqlSegment = new StringBuilder(64)
                .append("MATCH(").append(colSegment).append(") ")
                .append("AGAINST('+").append(keyword).append("' IN BOOLEAN MODE)")
                .toString();
        // count
        Integer total = dslContext.selectDistinct(DSL.count(DOCUMENT_FULL_TEXT.TABLE_DOCUMENT_ID))
                .from(DOCUMENT_FULL_TEXT)
                .where(DOCUMENT_FULL_TEXT.PROJECT_ID.isNotNull()
                        .and(DOCUMENT_FULL_TEXT.TABLE_DOCUMENT_ID.isNull())
                        .and(fullTextMatchSqlSegment))
                .fetchOne(0, int.class);
        // content
        List<DocumentFullTextPojo> content = dslContext.select(groupColumns)
                .from(DOCUMENT_FULL_TEXT)
                .where(DOCUMENT_FULL_TEXT.PROJECT_ID.isNotNull()
                        .and(DOCUMENT_FULL_TEXT.TABLE_DOCUMENT_ID.isNotNull())
                        .and(fullTextMatchSqlSegment))
                .groupBy(groupColumns)
                .limit(pageable.getOffset(), pageable.getPageSize())
                .fetchInto(DocumentFullTextPojo.class);
        return new PageImpl<>(content, pageable, total.longValue());
    }

    public Page<DocumentFullTextPojo> selectProjectPage(Pageable pageable, String keyword) {
        String[] matchCols = Indexes.DOCUMENT_FULL_TEXT_FIDX_PROJECT.getFields()
                .stream()
                .map(f -> f.getName())
                .toArray(String[]::new);
        String colSegment = String.join(",", matchCols);
        // sample: match(col_name, col_comment) against('+databasir' in boolean mode)
        String fullTextMatchSqlSegment = new StringBuilder(64)
                .append("MATCH(").append(colSegment).append(") ")
                .append("AGAINST('+").append(keyword).append("' IN BOOLEAN MODE)")
                .toString();
        // count
        Integer total = dslContext.selectDistinct(DSL.count(DOCUMENT_FULL_TEXT.PROJECT_ID))
                .from(DOCUMENT_FULL_TEXT)
                .where(DOCUMENT_FULL_TEXT.PROJECT_ID.isNotNull()
                        .and(DOCUMENT_FULL_TEXT.TABLE_DOCUMENT_ID.isNull())
                        .and(fullTextMatchSqlSegment))
                .fetchOne(0, int.class);
        // content
        List<DocumentFullTextPojo> content = dslContext.select(DOCUMENT_FULL_TEXT.fields())
                .from(DOCUMENT_FULL_TEXT)
                .where(DOCUMENT_FULL_TEXT.PROJECT_ID.isNotNull()
                        .and(DOCUMENT_FULL_TEXT.TABLE_DOCUMENT_ID.isNull())
                        .and(fullTextMatchSqlSegment))
                .limit(pageable.getOffset(), pageable.getPageSize())
                .fetchInto(DocumentFullTextPojo.class);
        return new PageImpl<>(content, pageable, total.longValue());
    }

    public Page<DocumentFullTextPojo> selectGroupPage(Pageable pageable, String keyword) {
        String[] matchCols = Indexes.DOCUMENT_FULL_TEXT_FIDX_GROUP.getFields()
                .stream()
                .map(f -> f.getName())
                .toArray(String[]::new);
        String colSegment = String.join(",", matchCols);

        String fullTextMatchSqlSegment = new StringBuilder(64)
                .append("MATCH(").append(colSegment).append(") ")
                .append("AGAINST('+").append(keyword).append("' IN BOOLEAN MODE)")
                .toString();
        // count
        Integer total = dslContext.selectDistinct(DSL.count(DOCUMENT_FULL_TEXT.GROUP_ID))
                .from(DOCUMENT_FULL_TEXT)
                .where(DOCUMENT_FULL_TEXT.GROUP_ID.isNotNull()
                        .and(DOCUMENT_FULL_TEXT.PROJECT_ID.isNull())
                        .and(fullTextMatchSqlSegment))
                .fetchOne(0, int.class);
        // content
        List<DocumentFullTextPojo> content = dslContext.select(DOCUMENT_FULL_TEXT.fields())
                .from(DOCUMENT_FULL_TEXT)
                .where(DOCUMENT_FULL_TEXT.GROUP_ID.isNotNull()
                        .and(DOCUMENT_FULL_TEXT.PROJECT_ID.isNull())
                        .and(fullTextMatchSqlSegment))
                .limit(pageable.getOffset(), pageable.getPageSize())
                .fetchInto(DocumentFullTextPojo.class);
        return new PageImpl<>(content, pageable, total.longValue());
    }

    public int deleteTableFullText(Integer projectId) {
        return this.delete(DOCUMENT_FULL_TEXT.PROJECT_ID.eq(projectId)
                .and(DOCUMENT_FULL_TEXT.TABLE_DOCUMENT_ID.isNotNull()));
    }

    public int deleteByGroupId(Integer groupId) {
        return this.delete(DOCUMENT_FULL_TEXT.GROUP_ID.eq(groupId));
    }

    public int deleteByProjectId(Integer projectId) {
        return this.delete(DOCUMENT_FULL_TEXT.PROJECT_ID.eq(projectId));
    }

    public int updateGroupInfoByGroupId(String groupName, String groupDescription, Integer groupId) {
        String description = Objects.requireNonNullElse(groupDescription, "");
        return this.getDslContext()
                .update(DOCUMENT_FULL_TEXT)
                .set(DOCUMENT_FULL_TEXT.GROUP_NAME, groupName)
                .set(DOCUMENT_FULL_TEXT.GROUP_DESCRIPTION, description)
                .where(DOCUMENT_FULL_TEXT.GROUP_ID.eq(groupId).and(DOCUMENT_FULL_TEXT.PROJECT_ID.isNull()))
                .execute();
    }

    public int updateProjectInfoByProjectId(FullTextProjectInfoUpdatePojo updatePojo) {
        return this.getDslContext()
                .update(DOCUMENT_FULL_TEXT)
                .set(DOCUMENT_FULL_TEXT.PROJECT_NAME, updatePojo.getProjectName())
                .set(DOCUMENT_FULL_TEXT.PROJECT_DESCRIPTION, updatePojo.getProjectDescription())
                .set(DOCUMENT_FULL_TEXT.DATABASE_NAME, updatePojo.getDatabaseName())
                .set(DOCUMENT_FULL_TEXT.SCHEMA_NAME, updatePojo.getSchemaName())
                .set(DOCUMENT_FULL_TEXT.DATABASE_TYPE, updatePojo.getDatabaseType())
                .where(DOCUMENT_FULL_TEXT.PROJECT_ID.eq(updatePojo.getProjectId())
                        .and(DOCUMENT_FULL_TEXT.TABLE_DOCUMENT_ID.isNull()))
                .execute();
    }

    public int updateTableDescription(Integer groupId, Integer projectId, String tableName, String description) {
        return this.getDslContext()
                .update(DOCUMENT_FULL_TEXT)
                .set(DOCUMENT_FULL_TEXT.TABLE_DESCRIPTION, description)
                .where(DOCUMENT_FULL_TEXT.GROUP_ID.eq(groupId)
                        .and(DOCUMENT_FULL_TEXT.PROJECT_ID.eq(projectId))
                        .and(DOCUMENT_FULL_TEXT.TABLE_NAME.eq(tableName)))
                .execute();
    }

    public int updateColumnDescription(Integer groupId,
                                       Integer projectId,
                                       String tableName,
                                       String columnName,
                                       String description) {
        return this.getDslContext()
                .update(DOCUMENT_FULL_TEXT)
                .set(DOCUMENT_FULL_TEXT.COL_DESCRIPTION, description)
                .where(DOCUMENT_FULL_TEXT.GROUP_ID.eq(groupId)
                        .and(DOCUMENT_FULL_TEXT.PROJECT_ID.eq(projectId))
                        .and(DOCUMENT_FULL_TEXT.TABLE_NAME.eq(tableName))
                        .and(DOCUMENT_FULL_TEXT.COL_NAME.eq(columnName)))
                .execute();
    }
}
