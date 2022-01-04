package com.databasir.core.domain.document.converter;

public interface BaseConverter {

    @NullToEmpty
    default String nullToEmpty(String s) {
        return s == null ? "" : s;
    }
}
