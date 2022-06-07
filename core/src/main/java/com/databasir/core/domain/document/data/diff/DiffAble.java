package com.databasir.core.domain.document.data.diff;

import com.databasir.core.diff.data.DiffType;

public interface DiffAble<T> {

    void setDiffType(DiffType diffType);

    void setOriginal(T t);
}

