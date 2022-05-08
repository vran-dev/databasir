package com.databasir.core.meta.repository;

import com.databasir.core.meta.data.TriggerMeta;
import com.databasir.core.meta.repository.condition.TableCondition;

import java.sql.Connection;
import java.util.List;

public interface TriggerMetaRepository {

    List<TriggerMeta> selectTriggers(Connection connection, TableCondition condition);

}
