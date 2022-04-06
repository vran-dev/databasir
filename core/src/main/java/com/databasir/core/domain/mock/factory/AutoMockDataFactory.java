package com.databasir.core.domain.mock.factory;

import com.databasir.dao.enums.MockDataType;
import com.databasir.dao.impl.TableColumnDocumentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Component
@Order
@RequiredArgsConstructor
public class AutoMockDataFactory implements MockDataFactory {

    private final TableColumnDocumentDao tableColumnDocumentDao;

    public static final Map<Integer, String> DATA_TYPE_VALUE_MAP = new HashMap<>();

    static {
        DATA_TYPE_VALUE_MAP.put(9999, "''");
        DATA_TYPE_VALUE_MAP.put(Types.BIT, "1");
        DATA_TYPE_VALUE_MAP.put(Types.TINYINT, "1");
        DATA_TYPE_VALUE_MAP.put(Types.SMALLINT, "1");
        DATA_TYPE_VALUE_MAP.put(Types.INTEGER, "1");
        DATA_TYPE_VALUE_MAP.put(Types.BIGINT, "1");
        DATA_TYPE_VALUE_MAP.put(Types.FLOAT, "1.1");
        DATA_TYPE_VALUE_MAP.put(Types.REAL, "''");
        DATA_TYPE_VALUE_MAP.put(Types.DOUBLE, "1.2");
        DATA_TYPE_VALUE_MAP.put(Types.NUMERIC, "1");
        DATA_TYPE_VALUE_MAP.put(Types.DECIMAL, "1.1");
        DATA_TYPE_VALUE_MAP.put(Types.CHAR, "''");
        DATA_TYPE_VALUE_MAP.put(Types.VARCHAR, "''");
        DATA_TYPE_VALUE_MAP.put(Types.LONGVARCHAR, "''");
        DATA_TYPE_VALUE_MAP.put(Types.DATE, "'1970-12-31'");
        DATA_TYPE_VALUE_MAP.put(Types.TIME, "'00:00:00'");
        DATA_TYPE_VALUE_MAP.put(Types.TIMESTAMP, "'2001-01-01 00:00:00'");
        DATA_TYPE_VALUE_MAP.put(Types.BINARY, "''");
        DATA_TYPE_VALUE_MAP.put(Types.VARBINARY, "''");
        DATA_TYPE_VALUE_MAP.put(Types.LONGVARBINARY, "''");
        DATA_TYPE_VALUE_MAP.put(Types.NULL, "null");
        DATA_TYPE_VALUE_MAP.put(Types.OTHER, "''");
        DATA_TYPE_VALUE_MAP.put(Types.JAVA_OBJECT, "''");
        DATA_TYPE_VALUE_MAP.put(Types.DISTINCT, "''");
        DATA_TYPE_VALUE_MAP.put(Types.STRUCT, "''");
        DATA_TYPE_VALUE_MAP.put(Types.ARRAY, "'{}'");
        DATA_TYPE_VALUE_MAP.put(Types.BLOB, "''");
        DATA_TYPE_VALUE_MAP.put(Types.CLOB, "''");
        DATA_TYPE_VALUE_MAP.put(Types.REF, "''");
        DATA_TYPE_VALUE_MAP.put(Types.DATALINK, "''");
        DATA_TYPE_VALUE_MAP.put(Types.BOOLEAN, "true");
        DATA_TYPE_VALUE_MAP.put(Types.ROWID, "''");
        DATA_TYPE_VALUE_MAP.put(Types.NCHAR, "''");
        DATA_TYPE_VALUE_MAP.put(Types.NVARCHAR, "''");
        DATA_TYPE_VALUE_MAP.put(Types.LONGNVARCHAR, "''");
        DATA_TYPE_VALUE_MAP.put(Types.NCLOB, "''");
        DATA_TYPE_VALUE_MAP.put(Types.SQLXML, "''");
        DATA_TYPE_VALUE_MAP.put(Types.REF_CURSOR, "''");
        DATA_TYPE_VALUE_MAP.put(Types.TIME_WITH_TIMEZONE, "''");
        DATA_TYPE_VALUE_MAP.put(Types.TIMESTAMP_WITH_TIMEZONE, "''");
    }

    @Override
    public boolean accept(MockColumnRule rule) {
        return rule == null || rule.getMockDataType() == MockDataType.AUTO;
    }

    @Override
    public String create(MockColumnRule rule) {
        return DATA_TYPE_VALUE_MAP.getOrDefault(rule.getDataType(), "''");
    }
}
