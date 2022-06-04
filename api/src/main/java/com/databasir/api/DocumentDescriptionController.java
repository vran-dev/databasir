package com.databasir.api;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.common.JsonData;
import com.databasir.core.domain.description.data.DocumentDescriptionSaveRequest;
import com.databasir.core.domain.description.service.DocumentDescriptionService;
import com.databasir.core.domain.log.annotation.AuditLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
@Tag(name = "DocumentDescriptionController", description = "文档描述 API")
public class DocumentDescriptionController {

    private final DocumentDescriptionService documentDescriptionService;

    @PostMapping(Routes.DocumentDescription.SAVE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    @AuditLog(module = AuditLog.Modules.PROJECT,
            name = "更新描述",
            involvedProjectId = "#projectId",
            retrieveInvolvedGroupId = true)
    @Operation(summary = "更新描述")
    public JsonData<Void> save(@PathVariable Integer groupId,
                               @PathVariable Integer projectId,
                               @RequestBody @Valid DocumentDescriptionSaveRequest request) {
        DatabasirUserDetails principal = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId = principal.getUser().getId();
        documentDescriptionService.save(groupId, projectId, userId, request);
        return JsonData.ok();
    }
}
