package com.databasir.core.domain.document.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseDocumentResponse {

    private Integer id;

    private String databaseName;

    private String schemaName;

    private String productName;

    private String productVersion;

    private Integer documentVersion;

    @Builder.Default
    private List<TableDocumentResponse> tables = new ArrayList<>();

    private LocalDateTime createAt;

}
