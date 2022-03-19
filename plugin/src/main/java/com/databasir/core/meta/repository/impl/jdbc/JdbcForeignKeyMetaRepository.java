package com.databasir.core.meta.repository.impl.jdbc;

import com.databasir.core.meta.data.ForeignKeyMeta;
import com.databasir.core.meta.repository.ForeignKeyMetaRepository;
import com.databasir.core.meta.repository.condition.TableCondition;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcForeignKeyMetaRepository implements ForeignKeyMetaRepository {

    @Override
    public List<ForeignKeyMeta> selectForeignKeys(Connection connection, TableCondition condition) {
        String databaseName = condition.getDatabaseName();
        String schemaName = condition.getSchemaName();
        String tableName = condition.getTableName();
        List<ForeignKeyMeta> foreignKeys = new ArrayList<>();
        ResultSet keyResult = null;
        try {
            keyResult = connection.getMetaData().getImportedKeys(databaseName, schemaName, tableName);
        } catch (SQLException e) {
            log.warn("warn: ignore foreign keys in " + databaseName + "." + tableName + ", " + e.getMessage());
            return foreignKeys;
        }

        try {
            keyResult = connection.getMetaData()
                    .getImportedKeys(databaseName, schemaName, tableName);
            while (keyResult.next()) {
                String fkTableName = keyResult.getString("FKTABLE_NAME");
                String fkColumnName = keyResult.getString("FKCOLUMN_NAME");
                String fkName = keyResult.getString("FK_NAME");

                String pkTableName = keyResult.getString("PKTABLE_NAME");
                String pkColumnName = keyResult.getString("PKCOLUMN_NAME");
                String pkName = keyResult.getString("PK_NAME");
                int updateRule = keyResult.getInt("UPDATE_RULE");
                int keySeq = keyResult.getInt("KEY_SEQ");
                int deleteRule = keyResult.getInt("DELETE_RULE");
                ForeignKeyMeta meta = ForeignKeyMeta.builder()
                        .keySeq(keySeq)
                        .fkTableName(fkTableName)
                        .fkColumnName(fkColumnName)
                        .fkName(fkName)
                        .pkTableName(pkTableName)
                        .pkColumnName(pkColumnName)
                        .pkName(pkName)
                        .updateRule(updateRuleConvert(updateRule))
                        .deleteRule(deleteRuleConvert(deleteRule))
                        .build();
                foreignKeys.add(meta);
            }
        } catch (SQLException e) {
            log.warn("warn: ignore foreign keys in " + databaseName + "." + tableName + ", " + e.getMessage());
        }
        return foreignKeys;
    }

    private String updateRuleConvert(int updateRule) {
        return doMapping(updateRule, "update");
    }

    private String deleteRuleConvert(int deleteRule) {
        return doMapping(deleteRule, "delete");
    }

    private String doMapping(int rule, String type) {
        if (rule == DatabaseMetaData.importedKeyCascade) {
            return "CASCADE";
        }
        if (rule == DatabaseMetaData.importedKeyRestrict) {
            return "CASCADE";
        }
        if (rule == DatabaseMetaData.importedKeyNoAction) {
            return "RESTRICT";
        }
        if (rule == DatabaseMetaData.importedKeySetNull) {
            return "SET_NULL";
        }
        if (rule == DatabaseMetaData.importedKeySetDefault) {
            return "SET_DEFAULT";
        }
        log.warn("can not map foreign key " + type + " rule = " + rule);
        return "";
    }
}
