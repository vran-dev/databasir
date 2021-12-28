package com.databasir.core.doc.model;

import lombok.Builder;
import lombok.Data;

/**
 * now: only support mysql, postgresql.
 */
@Data
@Builder
public class TriggerDoc {

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
