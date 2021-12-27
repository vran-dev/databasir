package com.databasir.core.doc.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class DatabaseDoc {

    private String productName;

    private String productVersion;

    private String driverName;

    private String driverVersion;

    private String databaseName;

    private String remark;

    @Builder.Default
    private List<TableDoc> tables = Collections.emptyList();

}
