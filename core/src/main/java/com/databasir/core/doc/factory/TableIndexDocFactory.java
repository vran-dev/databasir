package com.databasir.core.doc.factory;

import com.databasir.core.doc.model.IndexDoc;

import java.sql.DatabaseMetaData;
import java.util.List;

public interface TableIndexDocFactory extends Sortable<TableIndexDocFactory> {

    List<IndexDoc> create(String tableName, DatabaseMetaData metaData, DatabaseDocConfig configuration);

}
