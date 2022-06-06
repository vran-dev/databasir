package com.databasir.core.domain.document.diff;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleObject {

    private Integer id;

    private Long height;

    private String name;

}