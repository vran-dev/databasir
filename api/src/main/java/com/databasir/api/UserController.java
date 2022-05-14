package com.databasir.api;

import com.databasir.api.common.LoginUserContext;
import com.databasir.api.validator.UserOperationValidator;
import com.databasir.common.JsonData;
import com.databasir.common.exception.Forbidden;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.log.annotation.AuditLog;
import com.databasir.core.domain.user.data.*;
import com.databasir.core.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "UserController", description = "用户 API")
public class UserController {

    private final UserService userService;

    private final UserOperationValidator userOperationValidator;

    @GetMapping(Routes.User.LIST)
    @Operation(summary = "分页查询用户")
    public JsonData<Page<UserPageResponse>> list(@PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                 Pageable pageable,
                                                 UserPageCondition condition) {
        return JsonData.ok(userService.list(pageable, condition));
    }

    @PostMapping(Routes.User.DISABLE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @AuditLog(module = AuditLog.Modules.USER, name = "禁用用户", involvedUserId = "#userId")
    @Operation(summary = "禁用用户")
    public JsonData<Void> disableUser(@PathVariable Integer userId) {
        if (userOperationValidator.isMyself(userId)) {
            throw DomainErrors.CANNOT_UPDATE_SELF_ENABLED_STATUS.exception();
        }
        userService.switchEnableStatus(userId, false);
        return JsonData.ok();
    }

    @PostMapping(Routes.User.ENABLE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @AuditLog(module = AuditLog.Modules.USER, name = "启用用户", involvedUserId = "#userId")
    @Operation(summary = "启用用户")
    public JsonData<Void> enableUser(@PathVariable Integer userId) {
        if (userOperationValidator.isMyself(userId)) {
            throw DomainErrors.CANNOT_UPDATE_SELF_ENABLED_STATUS.exception();
        }
        userService.switchEnableStatus(userId, true);
        return JsonData.ok();
    }

    @PostMapping(Routes.User.CREATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @AuditLog(module = AuditLog.Modules.USER, name = "创建用户")
    @Operation(summary = "创建用户")
    public JsonData<Void> create(@RequestBody @Valid UserCreateRequest request) {
        userService.create(request, UserSource.MANUAL);
        return JsonData.ok();
    }

    @GetMapping(Routes.User.GET_ONE)
    @Operation(summary = "根据 ID 查询用户")
    public JsonData<UserDetailResponse> getOne(@PathVariable Integer userId) {
        return JsonData.ok(userService.get(userId));
    }

    @DeleteMapping(Routes.User.DELETE_ONE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @Operation(summary = "根据 ID 删除用户")
    public JsonData<Void> deleteOne(@PathVariable Integer userId) {
        if (userOperationValidator.isMyself(userId)) {
            throw DomainErrors.CANNOT_DELETE_SELF.exception();
        }
        userService.deleteOne(userId);
        return JsonData.ok();
    }

    @PostMapping(Routes.User.RENEW_PASSWORD)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @AuditLog(module = AuditLog.Modules.USER, name = "重置用户密码", involvedUserId = "#userId")
    @Operation(summary = "重置用户密码")
    public JsonData<Void> renewPassword(@PathVariable Integer userId) {
        Integer operatorUserId = LoginUserContext.getLoginUserId();
        userService.renewPassword(operatorUserId, userId);
        return JsonData.ok();
    }

    @PostMapping(Routes.User.ADD_OR_REMOVE_SYS_OWNER)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @AuditLog(module = AuditLog.Modules.USER, name = "添加系统管理员", involvedUserId = "#userId")
    @Operation(summary = "添加系统管理员")
    public JsonData<Void> addSysOwner(@PathVariable Integer userId) {
        userOperationValidator.forbiddenIfUpdateSelfRole(userId);
        userService.addSysOwnerTo(userId);
        return JsonData.ok();
    }

    @DeleteMapping(Routes.User.ADD_OR_REMOVE_SYS_OWNER)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @AuditLog(module = AuditLog.Modules.USER, name = "移除系统管理员", involvedUserId = "#userId")
    @Operation(summary = "移除系统管理员")
    public JsonData<Void> removeSysOwner(@PathVariable Integer userId) {
        userOperationValidator.forbiddenIfUpdateSelfRole(userId);
        userService.removeSysOwnerFrom(userId);
        return JsonData.ok();
    }

    @PostMapping(Routes.User.UPDATE_PASSWORD)
    @AuditLog(module = AuditLog.Modules.USER, name = "更新密码", involvedUserId = "#userId")
    @Operation(summary = "更新密码")
    public JsonData<Void> updatePassword(@PathVariable Integer userId,
                                         @RequestBody @Valid UserPasswordUpdateRequest request) {
        if (userOperationValidator.isMyself(userId)) {
            userService.updatePassword(userId, request);
            return JsonData.ok();
        } else {
            throw new Forbidden();
        }
    }

    @PostMapping(Routes.User.UPDATE_NICKNAME)
    @AuditLog(module = AuditLog.Modules.USER, name = "更新昵称", involvedUserId = "#userId")
    @Operation(summary = "更新昵称")
    public JsonData<Void> updateNickname(@PathVariable Integer userId,
                                         @RequestBody @Valid UserNicknameUpdateRequest request) {
        if (userOperationValidator.isMyself(userId)) {
            userService.updateNickname(userId, request);
            return JsonData.ok();
        } else {
            throw new Forbidden();
        }
    }
}
