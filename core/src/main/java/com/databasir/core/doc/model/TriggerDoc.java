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
     * example 1: BEFORE UPDATE
     * example 2: AFTER INSERT
     */
    private String timing;

    private String statement;

    private String createAt;
}
