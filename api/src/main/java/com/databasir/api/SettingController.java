package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.log.annotation.AuditLog;
import com.databasir.core.domain.system.data.SystemEmailResponse;
import com.databasir.core.domain.system.data.SystemEmailUpdateRequest;
import com.databasir.core.domain.system.service.SystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasAnyAuthority('SYS_OWNER')")
@Tag(name = "SettingController", description = "系统设置 API")
public class SettingController {

    private final SystemService systemService;

    @GetMapping(Routes.Setting.GET_SYS_EMAIL)
    @Operation(summary = "获取系统邮箱配置")
    public JsonData<SystemEmailResponse> getSystemEmailSetting() {
        return systemService.getEmailSetting()
                .map(JsonData::ok)
                .orElseGet(JsonData::ok);
    }

    @DeleteMapping(Routes.Setting.DELETE_SYS_EMAIL)
    @AuditLog(module = AuditLog.Modules.SETTING, name = "重置系统邮箱")
    @Operation(summary = "重置系统邮箱")
    public JsonData<Void> deleteSysEmail() {
        systemService.deleteSystemEmail();
        return JsonData.ok();
    }

    @PostMapping(Routes.Setting.UPDATE_SYS_EMAIL)
    @AuditLog(module = AuditLog.Modules.SETTING, name = "更新邮件配置")
    @Operation(summary = "更新邮件配置")
    public JsonData<Void> updateSystemEmailSetting(@RequestBody @Valid SystemEmailUpdateRequest request) {
        systemService.updateEmailSetting(request);
        return JsonData.ok();
    }
}
