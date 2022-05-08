package com.databasir.core.diff.data;

public interface Diff {

    DiffType getDiffType();

    Object getOriginal();

    Object getCurrent();

}
