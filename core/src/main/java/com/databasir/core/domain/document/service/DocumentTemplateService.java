package com.databasir.core.domain.document.service;

import com.databasir.core.domain.document.converter.DocumentTemplatePropertiesUpdateRequestConverter;
import com.databasir.core.domain.document.converter.DocumentTemplatePropertyResponseConverter;
import com.databasir.core.domain.document.data.DocumentTemplatePropertiesResponse;
import com.databasir.core.domain.document.data.DocumentTemplatePropertiesUpdateRequest;
import com.databasir.dao.impl.DocumentTemplatePropertyDao;
import com.databasir.dao.tables.pojos.DocumentTemplatePropertyPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.databasir.dao.enums.DocumentTemplatePropertyType.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentTemplateService {

    private final DocumentTemplatePropertyDao documentTemplatePropertyDao;

    private final DocumentTemplatePropertyResponseConverter documentTemplatePropertyResponseConverter;

    private final DocumentTemplatePropertiesUpdateRequestConverter documentTemplatePropertiesUpdateRequestConverter;

    public DocumentTemplatePropertiesResponse getAllProperties() {
        List<DocumentTemplatePropertyPojo> properties = documentTemplatePropertyDao.selectAll();
        var propertiesGroupByType = documentTemplatePropertyResponseConverter.of(properties)
                .stream()
                .collect(Collectors.groupingBy(d -> d.getType()));
        return DocumentTemplatePropertiesResponse.builder()
                .columnFieldNameProperties(propertiesGroupByType.get(COLUMN_FIELD_NAME))
                .foreignKeyFieldNameProperties(propertiesGroupByType.get(FOREIGN_KEY_FIELD_NAME))
                .indexFieldNameProperties(propertiesGroupByType.get(INDEX_FIELD_NAME))
                .triggerFieldNameProperties(propertiesGroupByType.get(TRIGGER_FIELD_NAME))
                .build();
    }

    public void updateByType(DocumentTemplatePropertiesUpdateRequest request) {
        List<DocumentTemplatePropertyPojo> pojoList = documentTemplatePropertiesUpdateRequestConverter.toPojo(request);
        documentTemplatePropertyDao.batchInsertOnDuplicateKeyUpdate(pojoList);
    }
}
