package com.databasir.api;


import com.databasir.common.JsonData;
import com.databasir.core.domain.system.data.SystemEmailResponse;
import com.databasir.core.domain.system.data.SystemEmailUpdateRequest;
import com.databasir.core.domain.system.service.SystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasAnyAuthority('SYS_OWNER')")
public class SettingController {

    private final SystemService systemService;

    @GetMapping(Routes.Setting.GET_SYS_EMAIL)
    public JsonData<SystemEmailResponse> getSystemEmailSetting() {
        return systemService.getEmailSetting()
                .map(JsonData::ok)
                .orElseGet(JsonData::ok);
    }

    @PostMapping(Routes.Setting.UPDATE_SYS_EMAIL)
    public JsonData<Void> updateSystemEmailSetting(@RequestBody @Valid SystemEmailUpdateRequest request) {
        systemService.updateEmailSetting(request);
        return JsonData.ok();
    }
}
