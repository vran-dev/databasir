package com.databasir.core.doc.render;

import java.util.Objects;

public interface ColumnValueConverter {

    default String convertDataType(String originType) {
        return originType;
    }

    default String convertIsNotNull(Boolean isNotNull) {
        return Objects.equals(isNotNull, true) ? "YES" : "";
    }

    default String convertIsAutoIncrement(Boolean isAutoIncrement) {
        return Objects.equals(isAutoIncrement, true) ? "YES" : "";
    }

}
