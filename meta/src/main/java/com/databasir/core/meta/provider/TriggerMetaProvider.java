package com.databasir.core.meta.provider;

import com.databasir.core.meta.data.TriggerMeta;
import com.databasir.core.meta.provider.condition.TableCondition;

import java.sql.Connection;
import java.util.List;

public interface TriggerMetaProvider {

    List<TriggerMeta> selectTriggers(Connection connection, TableCondition condition);

}
