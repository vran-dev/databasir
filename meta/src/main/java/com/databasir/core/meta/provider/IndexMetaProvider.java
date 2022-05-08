package com.databasir.core.meta.provider;

import com.databasir.core.meta.data.IndexMeta;
import com.databasir.core.meta.provider.condition.TableCondition;

import java.sql.Connection;
import java.util.List;

public interface IndexMetaProvider {

    List<IndexMeta> selectIndexes(Connection connection, TableCondition condition);
}
