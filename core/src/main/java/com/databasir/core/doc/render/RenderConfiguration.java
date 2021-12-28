package com.databasir.core.doc.render;

import com.databasir.core.doc.model.ColumnDoc;
import com.databasir.core.doc.model.IndexDoc;
import com.databasir.core.doc.model.TriggerDoc;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.function.Function;

@Data
public class RenderConfiguration {

    private Boolean renderTables = true;

    private Boolean renderColumns = true;

    private Boolean renderIndexes = true;

    private Boolean renderTriggers = true;

    private LinkedHashMap<String, Function<ColumnDoc, String>> columnTitleAndValueMapping = columnTitleAndValueMapping();

    private LinkedHashMap<String, Function<IndexDoc, String>> indexTitleAndValueMapping = indexTitleAndValueMapping();

    private LinkedHashMap<String, Function<TriggerDoc, String>> triggerTitleAndValueMapping = triggerTitleAndValueMapping();

    protected LinkedHashMap<String, Function<ColumnDoc, String>> columnTitleAndValueMapping() {
        LinkedHashMap<String, Function<ColumnDoc, String>> mapping = new LinkedHashMap<>();
        mapping.put("Name", ColumnDoc::getName);
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
        mapping.put("Comment", ColumnDoc::getComment);
        return mapping;
    }

    protected LinkedHashMap<String, Function<IndexDoc, String>> indexTitleAndValueMapping() {
        LinkedHashMap<String, Function<IndexDoc, String>> mapping = new LinkedHashMap<>();
        mapping.put("Name", IndexDoc::getIndexName);
        mapping.put("IsPrimary", index -> index.getIsPrimaryKey() ? "YES" : "");
        mapping.put("IsUnique", index -> index.getIsUniqueKey() ? "YES" : "");
        mapping.put("Columns", index -> String.join(", ", index.getColumnNames()));
        return mapping;
    }

    protected LinkedHashMap<String, Function<TriggerDoc, String>> triggerTitleAndValueMapping() {
        LinkedHashMap<String, Function<TriggerDoc, String>> mapping = new LinkedHashMap<>();
        mapping.put("Name", TriggerDoc::getName);
        mapping.put("Timing", trigger -> trigger.getTiming() + " " + trigger.getManipulation());
        mapping.put("Statement", trigger -> trigger.getStatement().replace("\n", " ")
                .replace("\r", " "));
        mapping.put("Create At", TriggerDoc::getCreateAt);
        return mapping;
    }
}
