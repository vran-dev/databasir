package com.databasir.core.domain.document.diff;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ComplexObject {

    private Integer id;

    private String name;

    private List<SimpleObject> items = new ArrayList<>();

}
