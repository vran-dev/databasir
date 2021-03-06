package com.databasir.api;

import com.databasir.api.validator.DatabaseTypeValidator;
import com.databasir.common.JsonData;
import com.databasir.core.domain.database.data.*;
import com.databasir.core.domain.database.service.DatabaseTypeService;
import com.databasir.core.domain.log.annotation.AuditLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@Validated
@RestController
@Tag(name = "DatabaseTypeController", description = "数据库类型 API")
public class DatabaseTypeController {

    private final DatabaseTypeService databaseTypeService;

    private final DatabaseTypeValidator databaseTypeValidator;

    @GetMapping(Routes.DatabaseType.LIST_SIMPLE)
    @Operation(summary = "获取所有数据库类型")
    public JsonData<List<DatabaseTypeSimpleResponse>> listSimpleDatabaseTypes() {
        return JsonData.ok(databaseTypeService.listSimpleDatabaseTypes());
    }

    @GetMapping(Routes.DatabaseType.LIST_PAGE)
    @Operation(summary = "分页获取数据库类型")
    public JsonData<Page<DatabaseTypePageResponse>> listPage(@PageableDefault(sort = "id", direction = DESC)
                                                             Pageable page,
                                                             DatabaseTypePageCondition condition) {
        Page<DatabaseTypePageResponse> data = databaseTypeService.findByPage(page, condition);
        return JsonData.ok(data);
    }

    @PostMapping(Routes.DatabaseType.CREATE)
    @AuditLog(module = AuditLog.Modules.DATABASE_TYPE, name = "创建数据库类型")
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @Operation(summary = "创建数据库类型")
    public JsonData<Integer> create(@RequestBody @Valid DatabaseTypeCreateRequest request) {
        databaseTypeValidator.isValidUrlPattern(request.getUrlPattern());
        Integer id = databaseTypeService.create(request);
        return JsonData.ok(id);
    }

    @PatchMapping(Routes.DatabaseType.UPDATE)
    @AuditLog(module = AuditLog.Modules.DATABASE_TYPE, name = "更新数据库类型")
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @Operation(summary = "更新数据库类型")
    public JsonData<Void> update(@RequestBody @Valid DatabaseTypeUpdateRequest request) {
        databaseTypeValidator.isValidUrlPattern(request.getUrlPattern());
        databaseTypeService.update(request);
        return JsonData.ok();
    }

    @DeleteMapping(Routes.DatabaseType.DELETE_ONE)
    @AuditLog(module = AuditLog.Modules.DATABASE_TYPE, name = "删除数据库类型")
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @Operation(summary = "删除数据库类型")
    public JsonData<Void> delete(@PathVariable Integer id) {
        databaseTypeService.deleteById(id);
        return JsonData.ok();
    }

    @GetMapping(Routes.DatabaseType.GET_ONE)
    @Operation(summary = "获取数据库类型")
    public JsonData<DatabaseTypeDetailResponse> getOne(@PathVariable Integer id) {
        Optional<DatabaseTypeDetailResponse> data = databaseTypeService.selectOne(id);
        return JsonData.ok(data);
    }

    @PostMapping(Routes.DatabaseType.RESOLVE_DRIVER_CLASS_NAME)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @Operation(summary = "解析数据库驱动类名")
    public JsonData<String> resolveDriverClassName(@RequestBody @Valid DriverClassNameResolveRequest request) {
        String driverClassName = databaseTypeService.resolveDriverClassName(request);
        return JsonData.ok(driverClassName);
    }

    @PostMapping(Routes.DatabaseType.UPLOAD_DRIVER)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @Operation(summary = "上传数据库驱动")
    public JsonData<String> uploadDriver(@RequestPart MultipartFile file) {
        String driverPath = databaseTypeService.uploadDriver(file);
        return JsonData.ok(driverPath);
    }

}
