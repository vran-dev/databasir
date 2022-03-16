package com.databasir.core.meta.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForeignKeyMeta {

    private Integer keySql;

    /**
     * may null
     */
    private String pkName;

    private String pkTableName;

    private String pkColumnName;

    /**
     * may null
     */
    private String fkName;

    private String fkTableName;

    private String fkColumnName;

    /**
     * NO_ACTION \ CASCADE \ SET_NULL \ SET_DEFAULT
     */
    private String updateRule;

    /**
     * NO_ACTION \ CASCADE \ SET_NULL \ SET_DEFAULT
     */
    private String deleteRule;
}
