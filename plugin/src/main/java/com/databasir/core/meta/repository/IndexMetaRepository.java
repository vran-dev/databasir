package com.databasir.core.meta.repository;

import com.databasir.core.meta.data.IndexMeta;
import com.databasir.core.meta.repository.condition.TableCondition;

import java.sql.Connection;
import java.util.List;

public interface IndexMetaRepository {

    List<IndexMeta> selectIndexes(Connection connection, TableCondition condition);
}
