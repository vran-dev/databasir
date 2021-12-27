package com.databasir.core.doc.factory;

import com.databasir.core.doc.model.IndexDoc;

import java.util.List;

public interface TableIndexDocFactory extends Sortable<TableIndexDocFactory> {

    List<IndexDoc> create(TableDocCreateContext context);

}
