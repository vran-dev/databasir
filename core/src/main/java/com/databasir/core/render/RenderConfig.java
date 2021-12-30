package com.databasir.core.render;

import com.databasir.core.meta.pojo.ColumnMeta;
import com.databasir.core.meta.pojo.IndexMeta;
import com.databasir.core.meta.pojo.TriggerMeta;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.function.Function;

@Data
public class RenderConfig {

    private Boolean renderTables = true;

    private Boolean renderColumns = true;

    private Boolean renderIndexes = true;

    private Boolean renderTriggers = true;

    private LinkedHashMap<String, Function<ColumnMeta, String>> columnTitleAndValueMapping = columnTitleAndValueMapping();

    private LinkedHashMap<String, Function<IndexMeta, String>> indexTitleAndValueMapping = indexTitleAndValueMapping();

    private LinkedHashMap<String, Function<TriggerMeta, String>> triggerTitleAndValueMapping = triggerTitleAndValueMapping();

    protected LinkedHashMap<String, Function<ColumnMeta, String>> columnTitleAndValueMapping() {
        LinkedHashMap<String, Function<ColumnMeta, String>> mapping = new LinkedHashMap<>();
        mapping.put("Name", ColumnMeta::getName);
        mapping.put("Type", column -> {
            String type;
            if (column.getDecimalDigits() == null || column.getDecimalDigits().equals(0)) {
                type = column.getType()
                        + "(" + column.getSize().toString() + ")";
            } else {
                type = column.getType()
                        + "(" + column.getSize().toString() + ", " + column.getDecimalDigits().toString() + ")";
            }
            return type;
        });
        mapping.put("Not Null", column -> column.getIsNullable() ? "" : "YES");
        mapping.put("Auto Increment", column -> column.getIsAutoIncrement() ? "YES" : "");
        mapping.put("Default", column -> {
            if (column.getDefaultValue() == null) {
                return "";
            }
            if (column.getDefaultValue().trim().equals("")) {
                return "'" + column.getDefaultValue() + "'";
            }
            return column.getDefaultValue();
        });
        mapping.put("Comment", ColumnMeta::getComment);
        return mapping;
    }

    protected LinkedHashMap<String, Function<IndexMeta, String>> indexTitleAndValueMapping() {
        LinkedHashMap<String, Function<IndexMeta, String>> mapping = new LinkedHashMap<>();
        mapping.put("Name", IndexMeta::getIndexName);
        mapping.put("IsPrimary", index -> index.getIsPrimaryKey() ? "YES" : "");
        mapping.put("IsUnique", index -> index.getIsUniqueKey() ? "YES" : "");
        mapping.put("Columns", index -> String.join(", ", index.getColumnNames()));
        return mapping;
    }

    protected LinkedHashMap<String, Function<TriggerMeta, String>> triggerTitleAndValueMapping() {
        LinkedHashMap<String, Function<TriggerMeta, String>> mapping = new LinkedHashMap<>();
        mapping.put("Name", TriggerMeta::getName);
        mapping.put("Timing", trigger -> trigger.getTiming() + " " + trigger.getManipulation());
        mapping.put("Statement", trigger -> trigger.getStatement().replace("\n", " ")
                .replace("\r", " "));
        mapping.put("Create At", TriggerMeta::getCreateAt);
        return mapping;
    }
}
