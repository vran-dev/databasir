package com.databasir.core.diff.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RootDiff {

    private DiffType diffType;

    private List<FieldDiff> fields = new ArrayList<>();

}
