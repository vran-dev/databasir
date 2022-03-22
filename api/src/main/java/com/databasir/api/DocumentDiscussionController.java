package com.databasir.api;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.common.JsonData;
import com.databasir.core.domain.log.annotation.Operation;
import com.databasir.core.domain.discussion.data.DiscussionCreateRequest;
import com.databasir.core.domain.discussion.data.DiscussionListCondition;
import com.databasir.core.domain.discussion.data.DiscussionResponse;
import com.databasir.core.domain.discussion.service.DocumentDiscussionService;
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
public class DocumentDiscussionController {

    private final DocumentDiscussionService documentDiscussionService;

    @GetMapping(Routes.DocumentDiscussion.LIST)
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
    @Operation(module = Operation.Modules.PROJECT,
            name = "删除评论",
            involvedProjectId = "#projectId")
    public JsonData<Void> delete(@PathVariable Integer groupId,
                                 @PathVariable Integer projectId,
                                 @PathVariable Integer discussionId) {
        documentDiscussionService.deleteById(groupId, projectId, discussionId);
        return JsonData.ok();
    }

    @PostMapping(Routes.DocumentDiscussion.CREATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    @Operation(module = Operation.Modules.PROJECT,
            name = "新增评论",
            involvedProjectId = "#projectId")
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
