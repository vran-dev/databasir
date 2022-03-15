package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentSimpleResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentVersionResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.core.domain.document.generator.DocumentFileType;
import com.databasir.core.domain.document.service.DocumentService;
import com.databasir.core.domain.log.annotation.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@Validated
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(Routes.Document.SYNC_ONE)
    @Operation(module = Operation.Modules.PROJECT, name = "文档同步", involvedProjectId = "#projectId")
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
                                                                                  @PageableDefault(sort = "id",
                                                                                          direction = DESC)
                                                                                          Pageable page) {
        return JsonData.ok(documentService.getVersionsByProjectId(projectId, page));
    }

    @GetMapping(Routes.Document.EXPORT)
    public ResponseEntity<StreamingResponseBody> getDocumentFiles(@PathVariable Integer projectId,
                                                                  @RequestParam(required = false)
                                                                          Long version,
                                                                  @RequestParam DocumentFileType fileType) {
        HttpHeaders headers = new HttpHeaders();
        String fileName = "project[" + projectId + "]." + fileType.getFileExtension();
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("demo.md", StandardCharsets.UTF_8)
                .build());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .headers(headers)
                .body(out -> documentService.export(projectId, version, fileType, out));
    }

    @GetMapping(Routes.Document.GET_SIMPLE_ONE)
    public JsonData<DatabaseDocumentSimpleResponse> getSimpleByProjectId(@PathVariable Integer projectId,
                                                                         @RequestParam(required = false)
                                                                                 Long version) {
        return JsonData.ok(documentService.getSimpleOneByProjectId(projectId, version));
    }

    @PostMapping(Routes.Document.GET_TABLE_DETAIL)
    public JsonData<List<TableDocumentResponse>> getTableDocument(
            @PathVariable Integer projectId,
            @PathVariable Integer documentId,
            @RequestBody List<Integer> tableIds) {
        return JsonData.ok(documentService.getTableDetails(projectId, documentId, tableIds));
    }

}
