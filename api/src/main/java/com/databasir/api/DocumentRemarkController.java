package com.databasir.api;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.common.JsonData;
import com.databasir.core.domain.remark.data.RemarkCreateRequest;
import com.databasir.core.domain.remark.data.RemarkListCondition;
import com.databasir.core.domain.remark.data.RemarkResponse;
import com.databasir.core.domain.remark.service.DocumentRemarkService;
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
public class DocumentRemarkController {

    private final DocumentRemarkService documentRemarkService;

    @GetMapping(Routes.DocumentRemark.LIST)
    public JsonData<Page<RemarkResponse>> listByProjectId(@PathVariable Integer groupId,
                                                          @PathVariable Integer projectId,
                                                          @PageableDefault(sort = "id",
                                                                  direction = Sort.Direction.DESC)
                                                                  Pageable request,
                                                          RemarkListCondition condition) {
        var data = documentRemarkService.list(groupId, projectId, request, condition);
        return JsonData.ok(data);
    }

    @DeleteMapping(Routes.DocumentRemark.DELETE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId)")
    public JsonData<Void> delete(@PathVariable Integer groupId,
                                 @PathVariable Integer projectId,
                                 @PathVariable Integer remarkId) {
        documentRemarkService.deleteById(groupId, projectId, remarkId);
        return JsonData.ok();
    }

    @PostMapping(Routes.DocumentRemark.CREATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    public JsonData<Void> create(@PathVariable Integer groupId,
                                 @PathVariable Integer projectId,
                                 @RequestBody @Valid RemarkCreateRequest request) {
        DatabasirUserDetails principal = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId = principal.getUserPojo().getId();
        documentRemarkService.create(groupId, projectId, userId, request);
        return JsonData.ok();
    }
}
