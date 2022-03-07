package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.common.SystemException;
import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentSimpleResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentVersionResponse;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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
                                                                  @RequestParam(required = false) Long version) {
        String data = documentService.toMarkdown(projectId, version).get();
        try {
            Path path = Files.writeString(Paths.get(UUID.randomUUID().toString() + ".md"), data,
                    StandardCharsets.UTF_8);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("demo.md", StandardCharsets.UTF_8)
                    .build());
            byte[] bytes = Files.readAllBytes(path);
            Files.deleteIfExists(path);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(out -> out.write(bytes));
        } catch (IOException e) {
            throw new SystemException("System error");
        }
    }

    @GetMapping(Routes.Document.GET_SIMPLE_ONE)
    public JsonData<DatabaseDocumentSimpleResponse> getSimpleByProjectId(@PathVariable Integer projectId,
                                                                         @RequestParam(required = false) Long version) {
        return JsonData.ok(documentService.getSimpleOneByProjectId(projectId, version));
    }

    @GetMapping(Routes.Document.GET_TABLE_DETAIL)
    public JsonData<DatabaseDocumentResponse.TableDocumentResponse> getTableDocument(@PathVariable Integer projectId,
                                                                                     @PathVariable Integer tableId) {
        return JsonData.ok(documentService.getTableDetails(projectId, tableId));
    }

}
