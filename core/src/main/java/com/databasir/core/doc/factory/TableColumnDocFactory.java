package com.databasir.core.doc.factory;

import com.databasir.core.doc.model.ColumnDoc;

import java.sql.DatabaseMetaData;
import java.util.List;

public interface TableColumnDocFactory extends Sortable<TableColumnDocFactory> {

    List<ColumnDoc> create(String tableName, DatabaseMetaData metaData, DatabaseDocConfiguration configuration);

}
