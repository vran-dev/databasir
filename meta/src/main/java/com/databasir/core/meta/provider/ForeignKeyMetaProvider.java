package com.databasir.core.meta.provider;

import com.databasir.core.meta.data.ForeignKeyMeta;
import com.databasir.core.meta.provider.condition.TableCondition;

import java.sql.Connection;
import java.util.List;

public interface ForeignKeyMetaProvider {

    List<ForeignKeyMeta> selectForeignKeys(Connection connection, TableCondition condition);

}
