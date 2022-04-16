package com.databasir.api;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.api.validator.CronExpressionValidator;
import com.databasir.common.JsonData;
import com.databasir.core.domain.log.annotation.Operation;
import com.databasir.core.domain.project.data.*;
import com.databasir.core.domain.project.data.task.ProjectSimpleTaskResponse;
import com.databasir.core.domain.project.data.task.ProjectTaskListCondition;
import com.databasir.core.domain.project.service.ProjectService;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private final ProjectService projectService;

    private final CronExpressionValidator cronExpressionValidator;

    @PostMapping(Routes.GroupProject.CREATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#request.groupId, "
            + "'GROUP_MEMBER?groupId='+#request.groupId)")
    @Operation(module = Operation.Modules.PROJECT,
            name = "创建项目",
            involvedGroupId = "#request.groupId")
    public JsonData<Void> create(@RequestBody @Valid ProjectCreateRequest request) {
        cronExpressionValidator.isValidCron(request);
        projectService.create(request);
        return JsonData.ok();
    }

    @PatchMapping(Routes.GroupProject.UPDATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    @Operation(module = Operation.Modules.PROJECT,
            name = "更新项目",
            involvedGroupId = "#groupId",
            involvedProjectId = "#request.id")
    public JsonData<Void> update(@RequestBody @Valid ProjectUpdateRequest request,
                                 @PathVariable Integer groupId) {
        cronExpressionValidator.isValidCron(request);
        projectService.update(groupId, request);
        return JsonData.ok();
    }

    @DeleteMapping(Routes.GroupProject.DELETE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    @Operation(module = Operation.Modules.PROJECT,
            name = "删除项目",
            involvedGroupId = "#groupId",
            involvedProjectId = "#projectId")
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
        DatabasirUserDetails user = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId = user.getUserPojo().getId();
        return JsonData.ok(projectService.list(userId, page, condition));
    }

    @PostMapping(Routes.GroupProject.TEST_CONNECTION)
    public JsonData<Void> testConnection(@RequestBody @Valid ProjectTestConnectionRequest request) {
        projectService.testConnection(request);
        return JsonData.ok();
    }

    @PostMapping(Routes.GroupProject.LIST_MANUAL_TASKS)
    public JsonData<List<ProjectSimpleTaskResponse>> listManualTasks(@PathVariable Integer projectId,
                                                               @RequestBody ProjectTaskListCondition condition) {
        return JsonData.ok(projectService.listManualTasks(projectId, condition));
    }
}
