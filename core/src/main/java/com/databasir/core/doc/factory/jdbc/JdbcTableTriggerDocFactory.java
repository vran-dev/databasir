package com.databasir.core.doc.factory.jdbc;

import com.databasir.core.doc.factory.TableDocCreateContext;
import com.databasir.core.doc.factory.TableTriggerDocFactory;
import com.databasir.core.doc.model.TriggerDoc;

import java.util.Collections;
import java.util.List;

public class JdbcTableTriggerDocFactory implements TableTriggerDocFactory {

    @Override
    public List<TriggerDoc> create(TableDocCreateContext context) {
        // note: jdbc not support get triggers
        return Collections.emptyList();
    }

}
