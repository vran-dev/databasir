package com.databasir.core.meta.repository;

import com.databasir.core.meta.pojo.ColumnMeta;
import com.databasir.core.meta.repository.condition.TableCondition;

import java.sql.Connection;
import java.util.List;

public interface ColumnMetaRepository {

    List<ColumnMeta> selectColumns(Connection connection, TableCondition condition);

}
