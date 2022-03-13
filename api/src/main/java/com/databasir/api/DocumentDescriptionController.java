package com.databasir.api;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.common.JsonData;
import com.databasir.core.domain.description.data.DocumentDescriptionSaveRequest;
import com.databasir.core.domain.description.service.DocumentDescriptionService;
import com.databasir.core.domain.log.annotation.Operation;
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
public class DocumentDescriptionController {

    private final DocumentDescriptionService documentDescriptionService;

    @PostMapping(Routes.DocumentDescription.SAVE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    @Operation(module = Operation.Modules.PROJECT,
            name = "更新描述",
            involvedProjectId = "#projectId")
    public JsonData<Void> save(@PathVariable Integer groupId,
                               @PathVariable Integer projectId,
                               @RequestBody @Valid DocumentDescriptionSaveRequest request) {
        DatabasirUserDetails principal = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId = principal.getUserPojo().getId();
        documentDescriptionService.save(groupId, projectId, userId, request);
        return JsonData.ok();
    }
}
