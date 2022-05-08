package com.databasir.core.meta.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * now: only support mysql, postgresql.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TriggerMeta {

    private String name;

    /**
     * example: BEFORE, AFTER
     */
    private String timing;

    /**
     * example: INSERT, UPDATE
     */
    private String manipulation;

    private String statement;

    private String createAt;
}
