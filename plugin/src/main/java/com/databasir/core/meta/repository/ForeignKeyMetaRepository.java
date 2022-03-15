package com.databasir.core.meta.repository;

import com.databasir.core.meta.data.ForeignKeyMeta;
import com.databasir.core.meta.repository.condition.TableCondition;

import java.sql.Connection;
import java.util.List;

public interface ForeignKeyMetaRepository {

    List<ForeignKeyMeta> selectForeignKeys(Connection connection, TableCondition condition);

}
