package com.databasir.core.doc.factory;

import com.databasir.core.doc.model.TriggerDoc;

import java.util.List;

public interface TableTriggerDocFactory extends Sortable<TableTriggerDocFactory> {

    List<TriggerDoc> create(TableDocCreateContext context);
}
