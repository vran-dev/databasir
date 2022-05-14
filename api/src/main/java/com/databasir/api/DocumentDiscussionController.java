package com.databasir.api;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.common.JsonData;
import com.databasir.core.domain.discussion.data.DiscussionCreateRequest;
import com.databasir.core.domain.discussion.data.DiscussionListCondition;
import com.databasir.core.domain.discussion.data.DiscussionResponse;
import com.databasir.core.domain.discussion.service.DocumentDiscussionService;
import com.databasir.core.domain.log.annotation.AuditLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
@Tag(name = "DocumentDiscussionController", description = "文档讨论 API")
public class DocumentDiscussionController {

    private final DocumentDiscussionService documentDiscussionService;

    @GetMapping(Routes.DocumentDiscussion.LIST)
    @Operation(summary = "获取文档评论列表")
    public JsonData<Page<DiscussionResponse>> listByProjectId(@PathVariable Integer groupId,
                                                              @PathVariable Integer projectId,
                                                              @PageableDefault(sort = "id",
                                                                      direction = Sort.Direction.DESC)
                                                              Pageable request,
                                                              DiscussionListCondition condition) {
        var data = documentDiscussionService.list(groupId, projectId, request, condition);
        return JsonData.ok(data);
    }

    @DeleteMapping(Routes.DocumentDiscussion.DELETE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId)")
    @AuditLog(module = AuditLog.Modules.PROJECT,
            name = "删除评论",
            involvedProjectId = "#projectId",
            retrieveInvolvedGroupId = true)
    @Operation(summary = "删除评论")
    public JsonData<Void> delete(@PathVariable Integer groupId,
                                 @PathVariable Integer projectId,
                                 @PathVariable Integer discussionId) {
        documentDiscussionService.deleteById(groupId, projectId, discussionId);
        return JsonData.ok();
    }

    @PostMapping(Routes.DocumentDiscussion.CREATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    @AuditLog(module = AuditLog.Modules.PROJECT,
            name = "新增评论",
            involvedProjectId = "#projectId",
            retrieveInvolvedGroupId = true)
    @Operation(summary = "新增评论")
    public JsonData<Void> create(@PathVariable Integer groupId,
                                 @PathVariable Integer projectId,
                                 @RequestBody @Valid DiscussionCreateRequest request) {
        DatabasirUserDetails principal = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId = principal.getUserPojo().getId();
        documentDiscussionService.create(groupId, projectId, userId, request);
        return JsonData.ok();
    }
}
