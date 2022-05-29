package com.databasir.core.domain.search.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResponse {

    private Page<Item> groupPageData;

    private Page<Item> projectPageData;

    private Page<Item> tablePageData;

    private Page<Item> columnPageData;

    @Data
    public static class Item {

        private Integer groupId;

        private Integer projectId;

        private Integer databaseDocumentId;

        private Integer databaseDocumentVersion;

        private Integer tableDocumentId;

        private Integer tableColumnDocumentId;

        private String groupName;

        private String groupDescription;

        private String projectName;

        private String projectDescription;

        private String databaseName;

        private String schemaName;

        private String databaseProductName;

        private String databaseType;

        private String tableName;

        private String tableComment;

        private String tableDescription;

        private String colName;

        private String colComment;

        private String colDescription;

    }

}
