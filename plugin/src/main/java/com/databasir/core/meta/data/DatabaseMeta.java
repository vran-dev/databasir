package com.databasir.core.meta.data;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class DatabaseMeta {

    /**
     * product_name
     */
    private String productName;

    /**
     * product_version
     */
    private String productVersion;

    /**
     * driver_name
     */
    private String driverName;

    /**
     * driver_version
     */
    private String driverVersion;

    /**
     * database_name
     */
    private String databaseName;

    /**
     * schema_name
     */
    private String schemaName;

    @Builder.Default
    private List<TableMeta> tables = Collections.emptyList();

}
