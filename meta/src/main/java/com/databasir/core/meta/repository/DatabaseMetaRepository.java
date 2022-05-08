package com.databasir.core.meta.repository;

import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.meta.repository.condition.Condition;

import java.sql.Connection;
import java.util.Optional;

public interface DatabaseMetaRepository {

    Optional<DatabaseMeta> select(Connection connection, Condition condition);

}
