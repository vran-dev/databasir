package com.databasir.api;

import com.databasir.api.validator.UserOperationValidator;
import com.databasir.common.JsonData;
import com.databasir.core.domain.group.data.*;
import com.databasir.core.domain.group.service.GroupService;
import com.databasir.core.domain.log.annotation.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static com.databasir.core.infrastructure.constant.RoleConstants.GROUP_MEMBER;
import static com.databasir.core.infrastructure.constant.RoleConstants.GROUP_OWNER;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@Validated
public class GroupController {

    private final GroupService groupService;

    private final UserOperationValidator userOperationValidator;

    @PostMapping(Routes.Group.CREATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @Operation(module = Operation.Modules.GROUP, name = "创建分组")
    public JsonData<Void> create(@RequestBody @Valid GroupCreateRequest request) {
        groupService.create(request);
        return JsonData.ok();
    }

    @PatchMapping(Routes.Group.UPDATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER'.concat('?groupId='.concat(#request.id)))")
    @Operation(module = Operation.Modules.GROUP,
            name = "更新分组",
            involvedGroupId = "#request.id")
    public JsonData<Void> update(@RequestBody @Valid GroupUpdateRequest request) {
        groupService.update(request);
        return JsonData.ok();
    }

    @GetMapping(Routes.Group.LIST)
    public JsonData<Page<GroupPageResponse>> list(@PageableDefault(sort = "id", direction = DESC)
                                                          Pageable page,
                                                  GroupPageCondition condition) {
        return JsonData.ok(groupService.list(page, condition));
    }

    @DeleteMapping(Routes.Group.DELETE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER'.concat('?groupId='.concat(#groupId)))")
    @Operation(module = Operation.Modules.GROUP,
            name = "删除分组",
            involvedGroupId = "#groupId")
    public JsonData<Void> deleteById(@PathVariable Integer groupId) {
        groupService.delete(groupId);
        return JsonData.ok();
    }

    @GetMapping(Routes.Group.GET_ONE)
    public JsonData<GroupResponse> getOne(@PathVariable Integer groupId) {
        return JsonData.ok(groupService.get(groupId));
    }

    @GetMapping(Routes.Group.MEMBERS)
    public JsonData<Page<GroupMemberPageResponse>> listGroupMembers(@PathVariable Integer groupId,
                                                                    @PageableDefault(sort = "user_role.create_at",
                                                                            direction = DESC)
                                                                            Pageable pageable,
                                                                    GroupMemberPageCondition condition) {
        return JsonData.ok(groupService.listGroupMembers(groupId, pageable, condition));
    }

    @PostMapping(Routes.Group.ADD_MEMBER)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER'.concat('?groupId='.concat(#groupId)))")
    @Operation(module = Operation.Modules.GROUP,
            name = "添加组员",
            involvedGroupId = "#groupId",
            involvedUserId = "#request.userId")
    public JsonData<Void> addGroupMember(@PathVariable Integer groupId,
                                         @RequestBody @Valid GroupMemberCreateRequest request) {
        userOperationValidator.forbiddenIfUpdateSelfRole(request.getUserId());
        List<String> groupRoles = Arrays.asList(GROUP_OWNER, GROUP_MEMBER);
        if (!groupRoles.contains(request.getRole())) {
            throw new IllegalArgumentException("role should be GROUP_OWNER or GROUP_MEMBER");
        }
        groupService.addMember(groupId, request);
        return JsonData.ok();
    }

    @DeleteMapping(Routes.Group.DELETE_MEMBER)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER'.concat('?groupId='.concat(#groupId)))")
    @Operation(module = Operation.Modules.GROUP,
            name = "移除组员",
            involvedGroupId = "#groupId",
            involvedUserId = "#userId")
    public JsonData<Void> removeGroupMember(@PathVariable Integer groupId,
                                            @PathVariable Integer userId) {
        userOperationValidator.forbiddenIfUpdateSelfRole(userId);
        groupService.removeMember(groupId, userId);
        return JsonData.ok();
    }

    @PatchMapping(Routes.Group.UPDATE_MEMBER)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER'.concat('?groupId='.concat(#groupId)))")
    @Operation(module = Operation.Modules.GROUP,
            name = "更新组员角色",
            involvedGroupId = "#groupId",
            involvedUserId = "#userId")
    public JsonData<Void> updateGroupMemberRole(@PathVariable Integer groupId,
                                                @PathVariable Integer userId,
                                                @RequestBody GroupMemberRoleUpdateRequest request) {
        userOperationValidator.forbiddenIfUpdateSelfRole(userId);
        List<String> groupRoles = Arrays.asList(GROUP_OWNER, GROUP_MEMBER);
        if (!groupRoles.contains(request.getRole())) {
            throw new IllegalArgumentException("role should be GROUP_OWNER or GROUP_MEMBER");
        }
        groupService.changeMemberRole(groupId, userId, request.getRole());
        return JsonData.ok();
    }

}
