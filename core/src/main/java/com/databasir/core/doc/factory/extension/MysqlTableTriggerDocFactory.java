package com.databasir.core.doc.factory.extension;

import com.databasir.core.doc.factory.DatabaseDocConfiguration;
import com.databasir.core.doc.factory.TableTriggerDocFactory;
import com.databasir.core.doc.model.TriggerDoc;

import java.sql.DatabaseMetaData;
import java.util.Collections;
import java.util.List;

public class MysqlTableTriggerDocFactory implements TableTriggerDocFactory {

    @Override
    public List<TriggerDoc> create(String tableName,
                                   DatabaseMetaData metaData,
                                   DatabaseDocConfiguration configuration) {
        return Collections.emptyList();
    }

}
