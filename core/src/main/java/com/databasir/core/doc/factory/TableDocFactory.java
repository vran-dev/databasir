package com.databasir.core.doc.factory;

import com.databasir.core.doc.model.TableDoc;

import java.util.List;

public interface TableDocFactory extends Sortable<TableDocFactory> {

    List<TableDoc> create(TableDocCreateContext context);

}
