package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.log.data.OperationLogPageCondition;
import com.databasir.core.domain.log.data.OperationLogPageResponse;
import com.databasir.core.domain.log.service.OperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "AuditLogController", description = "操作日志 API")
public class AuditLogController {

    private final OperationLogService operationLogService;

    @GetMapping(Routes.OperationLog.LIST)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @Operation(summary = "查询操作日志")
    public JsonData<Page<OperationLogPageResponse>> list(@PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                         Pageable page,
                                                         OperationLogPageCondition condition) {
        Page<OperationLogPageResponse> pageData = operationLogService.list(page, condition);
        return JsonData.ok(pageData);
    }
}
