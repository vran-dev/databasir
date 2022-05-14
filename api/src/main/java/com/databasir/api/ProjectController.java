package com.databasir.api;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.api.validator.CronExpressionValidator;
import com.databasir.common.JsonData;
import com.databasir.core.domain.log.annotation.AuditLog;
import com.databasir.core.domain.project.data.*;
import com.databasir.core.domain.project.data.task.ProjectSimpleTaskResponse;
import com.databasir.core.domain.project.data.task.ProjectTaskListCondition;
import com.databasir.core.domain.project.service.ProjectService;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "ProjectController", description = "项目 API")
public class ProjectController {

    private final ProjectService projectService;

    private final CronExpressionValidator cronExpressionValidator;

    @PostMapping(Routes.GroupProject.CREATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#request.groupId, "
            + "'GROUP_MEMBER?groupId='+#request.groupId)")
    @AuditLog(module = AuditLog.Modules.PROJECT,
            name = "创建项目",
            involvedGroupId = "#request.groupId")
    @Operation(summary = "创建项目")
    public JsonData<Void> create(@RequestBody @Valid ProjectCreateRequest request) {
        cronExpressionValidator.isValidCron(request);
        projectService.create(request);
        return JsonData.ok();
    }

    @PatchMapping(Routes.GroupProject.UPDATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    @AuditLog(module = AuditLog.Modules.PROJECT,
            name = "更新项目",
            involvedGroupId = "#groupId",
            involvedProjectId = "#request.id")
    @Operation(summary = "更新项目")
    public JsonData<Void> update(@RequestBody @Valid ProjectUpdateRequest request,
                                 @PathVariable Integer groupId) {
        cronExpressionValidator.isValidCron(request);
        projectService.update(groupId, request);
        return JsonData.ok();
    }

    @DeleteMapping(Routes.GroupProject.DELETE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    @AuditLog(module = AuditLog.Modules.PROJECT,
            name = "删除项目",
            involvedGroupId = "#groupId",
            involvedProjectId = "#projectId")
    @Operation(summary = "删除项目")
    public JsonData<Void> delete(@PathVariable Integer groupId,
                                 @PathVariable Integer projectId) {
        projectService.delete(projectId);
        return JsonData.ok();
    }

    @GetMapping(Routes.GroupProject.GET_ONE)
    @Operation(summary = "获取项目详情")
    public JsonData<ProjectDetailResponse> getOne(@PathVariable Integer projectId) {
        return JsonData.ok(projectService.getOne(projectId));
    }

    @GetMapping(Routes.GroupProject.LIST)
    @Operation(summary = "获取项目列表")
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
    @Operation(summary = "测试连接")
    public JsonData<Void> testConnection(@RequestBody @Valid ProjectTestConnectionRequest request) {
        projectService.testConnection(request);
        return JsonData.ok();
    }

    @PostMapping(Routes.GroupProject.LIST_MANUAL_TASKS)
    @Operation(summary = "获取同步任务列表")
    public JsonData<List<ProjectSimpleTaskResponse>> listManualTasks(@PathVariable Integer projectId,
                                                                     @RequestBody ProjectTaskListCondition condition) {
        return JsonData.ok(projectService.listManualTasks(projectId, condition));
    }

    @PatchMapping(Routes.GroupProject.CANCEL_MANUAL_TASK)
    @Operation(summary = "取消同步任务")
    public JsonData<Void> cancelTask(@PathVariable Integer projectId,
                                     @PathVariable Integer taskId) {
        projectService.cancelTask(projectId, taskId);
        return JsonData.ok();
    }
}
