package com.databasir.core.doc.factory;

import com.databasir.core.doc.model.TableDoc;

import java.sql.DatabaseMetaData;
import java.util.List;

public interface TableDocFactory extends Sortable<TableDocFactory> {

    List<TableDoc> create(DatabaseMetaData metaData, DatabaseDocConfiguration configuration);

}
