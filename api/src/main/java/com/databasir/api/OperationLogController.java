package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.log.data.OperationLogPageCondition;
import com.databasir.core.domain.log.data.OperationLogPageResponse;
import com.databasir.core.domain.log.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class OperationLogController {

    private final OperationLogService operationLogService;

    @GetMapping(Routes.OperationLog.LIST)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    public JsonData<Page<OperationLogPageResponse>> list(@PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                                 Pageable page,
                                                         OperationLogPageCondition condition) {
        Page<OperationLogPageResponse> pageData = operationLogService.list(page, condition);
        return JsonData.ok(pageData);
    }
}
