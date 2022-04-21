package com.databasir.api;

import com.databasir.api.validator.DatabaseTypeValidator;
import com.databasir.common.JsonData;
import com.databasir.core.domain.database.data.*;
import com.databasir.core.domain.database.service.DatabaseTypeService;
import com.databasir.core.domain.log.annotation.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@Validated
@RestController
public class DatabaseTypeController {

    private final DatabaseTypeService databaseTypeService;

    private final DatabaseTypeValidator databaseTypeValidator;

    @GetMapping(Routes.DatabaseType.LIST_SIMPLE)
    public JsonData<List<DatabaseTypeSimpleResponse>> listSimpleDatabaseTypes() {
        return JsonData.ok(databaseTypeService.listSimpleDatabaseTypes());
    }

    @GetMapping(Routes.DatabaseType.LIST_PAGE)
    public JsonData<Page<DatabaseTypePageResponse>> listPage(@PageableDefault(sort = "id", direction = DESC)
                                                             Pageable page,
                                                             DatabaseTypePageCondition condition) {
        Page<DatabaseTypePageResponse> data = databaseTypeService.findByPage(page, condition);
        return JsonData.ok(data);
    }

    @PostMapping(Routes.DatabaseType.CREATE)
    @Operation(module = Operation.Modules.DATABASE_TYPE, name = "创建数据库类型")
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    public JsonData<Integer> create(@RequestBody @Valid DatabaseTypeCreateRequest request) {
        databaseTypeValidator.isValidUrlPattern(request.getUrlPattern());
        Integer id = databaseTypeService.create(request);
        return JsonData.ok(id);
    }

    @PatchMapping(Routes.DatabaseType.UPDATE)
    @Operation(module = Operation.Modules.DATABASE_TYPE, name = "更新数据库类型")
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    public JsonData<Void> update(@RequestBody @Valid DatabaseTypeUpdateRequest request) {
        databaseTypeValidator.isValidUrlPattern(request.getUrlPattern());
        databaseTypeService.update(request);
        return JsonData.ok();
    }

    @DeleteMapping(Routes.DatabaseType.DELETE_ONE)
    @Operation(module = Operation.Modules.DATABASE_TYPE, name = "删除数据库类型")
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    public JsonData<Void> delete(@PathVariable Integer id) {
        databaseTypeService.deleteById(id);
        return JsonData.ok();
    }

    @GetMapping(Routes.DatabaseType.GET_ONE)
    public JsonData<DatabaseTypeDetailResponse> getOne(@PathVariable Integer id) {
        Optional<DatabaseTypeDetailResponse> data = databaseTypeService.selectOne(id);
        return JsonData.ok(data);
    }

    @PostMapping(Routes.DatabaseType.RESOLVE_DRIVER_CLASS_NAME)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    public JsonData<String> resolveDriverClassName(@RequestBody @Valid DriverClassNameResolveRequest request) {
        String driverClassName = databaseTypeService.resolveDriverClassName(request);
        return JsonData.ok(driverClassName);
    }

}
