package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.project.data.*;
import com.databasir.core.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(Routes.GroupProject.CREATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#request.groupId, 'GROUP_MEMBER?groupId='+#request.groupId)")
    public JsonData<Void> create(@RequestBody @Valid ProjectCreateRequest request) {
        projectService.create(request);
        return JsonData.ok();
    }

    @PatchMapping(Routes.GroupProject.UPDATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    public JsonData<Void> update(@RequestBody @Valid ProjectUpdateRequest request,
                                 @PathVariable Integer groupId) {
        projectService.update(groupId, request);
        return JsonData.ok();
    }

    @DeleteMapping(Routes.GroupProject.DELETE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    public JsonData<Void> delete(@PathVariable Integer groupId,
                                 @PathVariable Integer projectId) {
        projectService.delete(projectId);
        return JsonData.ok();
    }

    @GetMapping(Routes.GroupProject.GET_ONE)
    public JsonData<ProjectDetailResponse> getOne(@PathVariable Integer projectId) {
        return JsonData.ok(projectService.getOne(projectId));
    }

    @GetMapping(Routes.GroupProject.LIST)
    public JsonData<Page<ProjectSimpleResponse>> list(@PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                              Pageable page,
                                                      ProjectListCondition condition) {
        return JsonData.ok(projectService.list(page, condition));
    }
}
