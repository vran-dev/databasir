package com.databasir.core.meta.provider;

import com.databasir.core.meta.data.TableMeta;
import com.databasir.core.meta.provider.condition.Condition;

import java.sql.Connection;
import java.util.List;

public interface TableMetaProvider {

    List<TableMeta> selectTables(Connection connection, Condition condition);

}
