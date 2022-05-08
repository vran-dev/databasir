package com.databasir.core.meta.provider;

import com.databasir.core.meta.data.ColumnMeta;
import com.databasir.core.meta.provider.condition.TableCondition;

import java.sql.Connection;
import java.util.List;

public interface ColumnMetaProvider {

    List<ColumnMeta> selectColumns(Connection connection, TableCondition condition);

}
