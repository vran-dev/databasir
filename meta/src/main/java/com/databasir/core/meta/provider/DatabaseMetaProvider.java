package com.databasir.core.meta.provider;

import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.meta.provider.condition.Condition;

import java.sql.Connection;
import java.util.Optional;

public interface DatabaseMetaProvider {

    Optional<DatabaseMeta> select(Connection connection, Condition condition);

}
