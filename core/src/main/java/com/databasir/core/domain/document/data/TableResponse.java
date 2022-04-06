package com.databasir.core.domain.document.data;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class TableResponse {

    private Integer id;

    private String name;

    private List<ColumnResponse> columns = Collections.emptyList();

    @Data
    public static class ColumnResponse {

        private Integer id;

        private String name;

        private String type;

    }
}
