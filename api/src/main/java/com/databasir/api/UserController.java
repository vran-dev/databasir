package com.databasir.api;

import com.databasir.api.validator.UserOperationValidator;
import com.databasir.common.JsonData;
import com.databasir.common.exception.Forbidden;
import com.databasir.core.domain.user.data.*;
import com.databasir.core.domain.user.service.UserService;
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
public class UserController {

    private final UserService userService;

    private final UserOperationValidator userOperationValidator;

    @GetMapping(Routes.User.LIST)
    public JsonData<Page<UserPageResponse>> list(@PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                         Pageable pageable,
                                                 UserPageCondition condition) {
        return JsonData.ok(userService.list(pageable, condition));
    }

    @PostMapping(Routes.User.DISABLE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    public JsonData<Void> disableUser(@PathVariable Integer userId) {
        userService.switchEnableStatus(userId, false);
        return JsonData.ok();
    }

    @PostMapping(Routes.User.ENABLE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    public JsonData<Void> enableUser(@PathVariable Integer userId) {
        userService.switchEnableStatus(userId, true);
        return JsonData.ok();
    }

    @PostMapping(Routes.User.CREATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    public JsonData<Void> create(@RequestBody @Valid UserCreateRequest request) {
        userService.create(request);
        return JsonData.ok();
    }

    @GetMapping(Routes.User.GET_ONE)
    public JsonData<UserDetailResponse> getOne(@PathVariable Integer userId) {
        return JsonData.ok(userService.get(userId));
    }

    @PostMapping(Routes.User.RENEW_PASSWORD)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    public JsonData<Void> renewPassword(@PathVariable Integer userId) {
        userService.renewPassword(userId);
        return JsonData.ok();
    }

    @PostMapping(Routes.User.ADD_OR_REMOVE_SYS_OWNER)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    public JsonData<Void> addSysOwner(@PathVariable Integer userId) {
        userOperationValidator.forbiddenIfUpdateSelfRole(userId);
        userService.addSysOwnerTo(userId);
        return JsonData.ok();
    }

    @DeleteMapping(Routes.User.ADD_OR_REMOVE_SYS_OWNER)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    public JsonData<Void> removeSysOwner(@PathVariable Integer userId) {
        userOperationValidator.forbiddenIfUpdateSelfRole(userId);
        userService.removeSysOwnerFrom(userId);
        return JsonData.ok();
    }

    @PostMapping(Routes.User.UPDATE_PASSWORD)
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
