package com.databasir.core.domain.document.data;

import com.databasir.dao.enums.DocumentTemplatePropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTemplatePropertiesResponse {

    private List<DocumentTemplatePropertyResponse> columnFieldNameProperties = Collections.emptyList();

    private List<DocumentTemplatePropertyResponse> indexFieldNameProperties = Collections.emptyList();

    private List<DocumentTemplatePropertyResponse> triggerFieldNameProperties = Collections.emptyList();

    private List<DocumentTemplatePropertyResponse> foreignKeyFieldNameProperties = Collections.emptyList();

    @Data
    public static class DocumentTemplatePropertyResponse {

        private String key;

        private String value;

        private String defaultValue;

        private DocumentTemplatePropertyType type;

        private LocalDateTime createAt;

    }
}
