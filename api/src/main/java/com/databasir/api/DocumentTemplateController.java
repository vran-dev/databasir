package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.document.data.DocumentTemplatePropertiesResponse;
import com.databasir.core.domain.document.data.DocumentTemplatePropertiesUpdateRequest;
import com.databasir.core.domain.document.service.DocumentTemplateService;
import com.databasir.core.domain.log.annotation.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@Validated
@RestController
public class DocumentTemplateController {

    private final DocumentTemplateService documentTemplateService;

    @GetMapping(Routes.DocumentTemplateProperty.API)
    public JsonData<DocumentTemplatePropertiesResponse> getAllProperties() {
        return JsonData.ok(documentTemplateService.getAllProperties());
    }

    @PatchMapping(Routes.DocumentTemplateProperty.API)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @Operation(module = Operation.Modules.SETTING, name = "更新模板")
    public JsonData<Void> updateByType(@RequestBody @Valid DocumentTemplatePropertiesUpdateRequest request) {
        documentTemplateService.updateByType(request);
        return JsonData.ok();
    }

}
