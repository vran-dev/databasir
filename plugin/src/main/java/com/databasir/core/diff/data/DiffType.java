package com.databasir.core.diff.data;

public enum DiffType {
    NONE, ADDED, REMOVED, MODIFIED;

    public static boolean isModified(DiffType type) {
        return type != null && type != NONE;
    }
}