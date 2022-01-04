package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentVersionResponse;
import com.databasir.core.domain.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(Routes.Document.SYNC_ONE)
    public JsonData<Void> sync(@PathVariable Integer projectId) {
        documentService.syncByProjectId(projectId);
        return JsonData.ok();
    }

    @GetMapping(Routes.Document.GET_ONE)
    public JsonData<DatabaseDocumentResponse> getByProjectId(@PathVariable Integer projectId,
                                                             @RequestParam(required = false) Long version) {
        return documentService.getOneByProjectId(projectId, version)
                .map(JsonData::ok)
                .orElseGet(JsonData::ok);
    }

    @GetMapping(Routes.Document.LIST_VERSIONS)
    public JsonData<Page<DatabaseDocumentVersionResponse>> getVersionsByProjectId(@PathVariable Integer projectId,
                                                                                  @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                                                          Pageable page) {
        return JsonData.ok(documentService.getVersionsBySchemaSourceId(projectId, page));
    }

}
