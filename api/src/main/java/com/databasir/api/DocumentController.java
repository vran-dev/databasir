package com.databasir.api;

import com.databasir.api.common.LoginUserContext;
import com.databasir.common.JsonData;
import com.databasir.core.domain.document.data.*;
import com.databasir.core.domain.document.generator.DocumentFileType;
import com.databasir.core.domain.document.service.DocumentService;
import com.databasir.core.domain.log.annotation.AuditLog;
import com.databasir.core.domain.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@Validated
@RestController
@Tag(name = "DocumentController", description = "数据库文档 API")
public class DocumentController {

    private final DocumentService documentService;

    private final ProjectService projectService;

    @PostMapping(Routes.Document.SYNC_ONE)
    @AuditLog(module = AuditLog.Modules.PROJECT, name = "创建同步任务", involvedProjectId = "#projectId",
            retrieveInvolvedGroupId = true)
    @Operation(summary = "创建同步任务")
    public JsonData<Integer> createSyncTask(@PathVariable Integer projectId) {
        Integer userId = LoginUserContext.getLoginUserId();
        Optional<Integer> taskIdOpt = projectService.createSyncTask(projectId, userId, false);
        return JsonData.ok(taskIdOpt);
    }

    @GetMapping(Routes.Document.GET_ONE)
    @Operation(summary = "获取文档")
    public JsonData<DatabaseDocumentResponse> getByProjectId(@PathVariable Integer projectId,
                                                             @RequestParam(required = false) Long version) {
        return documentService.getOneByProjectId(projectId, version)
                .map(JsonData::ok)
                .orElseGet(JsonData::ok);
    }

    @GetMapping(Routes.Document.LIST_VERSIONS)
    @Operation(summary = "获取文档版本列表")
    public JsonData<Page<DatabaseDocumentVersionResponse>> getVersionsByProjectId(@PathVariable Integer projectId,
                                                                                  @PageableDefault(sort = "id",
                                                                                          direction = DESC)
                                                                                  Pageable page) {
        return JsonData.ok(documentService.getVersionsByProjectId(projectId, page));
    }

    @GetMapping(Routes.Document.EXPORT)
    @Operation(summary = "导出文档")
    @AuditLog(module = AuditLog.Modules.PROJECT, name = "导出文档", involvedProjectId = "#projectId",
            retrieveInvolvedGroupId = true)
    public ResponseEntity<StreamingResponseBody> getDocumentFiles(@PathVariable Integer projectId,
                                                                  @RequestParam(required = false)
                                                                  Long version,
                                                                  @RequestParam DocumentFileType fileType) {
        HttpHeaders headers = new HttpHeaders();
        String projectName = projectService.getOne(projectId).getName();
        String fileName = projectName + "." + fileType.getFileExtension();
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(fileName)
                .build());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .headers(headers)
                .body(out -> documentService.export(projectId, version, fileType, out));
    }

    @GetMapping(Routes.Document.EXPORT_TYPES)
    public JsonData<List<DocumentFileTypeResponse>> getDocumentFileTypes() {
        List<DocumentFileTypeResponse> types = Arrays.stream(DocumentFileType.values())
                .map(type -> new DocumentFileTypeResponse(type.getName(), type.getFileExtension(), type))
                .collect(Collectors.toList());
        return JsonData.ok(types);
    }

    @GetMapping(Routes.Document.GET_SIMPLE_ONE)
    @Operation(summary = "获取文档（无详情信息）")
    public JsonData<DatabaseDocumentSimpleResponse> getSimpleByProjectId(@PathVariable Integer projectId,
                                                                         @RequestParam(required = false)
                                                                         Long version,
                                                                         @RequestParam(required = false)
                                                                         Long originalVersion) {
        return JsonData.ok(documentService.getSimpleOneByProjectId(projectId, version, originalVersion));
    }

    @PostMapping(Routes.Document.GET_TABLE_DETAIL)
    @Operation(summary = "获取表详情")
    public JsonData<List<TableDocumentResponse>> getTableDocument(@PathVariable Integer projectId,
                                                                  @PathVariable Integer documentId,
                                                                  @RequestBody @Valid TableDocumentRequest request) {
        return JsonData.ok(documentService.getTableDetails(projectId, documentId, request));
    }

    @GetMapping(Routes.Document.LIST_TABLES)
    @Operation(summary = "获取表列表")
    public JsonData<List<TableResponse>> listTables(@PathVariable Integer projectId,
                                                    @RequestParam(required = false) Long version) {
        return JsonData.ok(documentService.getTableAndColumns(projectId, version));
    }
}
