package com.databasir.core.domain.document.data;

import com.databasir.dao.enums.DocumentTemplatePropertyType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Data
public class DocumentTemplatePropertiesUpdateRequest {

    @NotNull
    private DocumentTemplatePropertyType type;

    @NotEmpty
    private List<PropertyRequest> properties = Collections.emptyList();

    @Data
    public static class PropertyRequest {

        private String key;

        private String value;

    }
}
