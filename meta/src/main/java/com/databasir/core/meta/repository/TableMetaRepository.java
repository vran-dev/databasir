package com.databasir.core.meta.repository;

import com.databasir.core.meta.data.TableMeta;
import com.databasir.core.meta.repository.condition.Condition;

import java.sql.Connection;
import java.util.List;

public interface TableMetaRepository {

    List<TableMeta> selectTables(Connection connection, Condition condition);

}
