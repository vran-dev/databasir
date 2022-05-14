package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.mock.MockDataService;
import com.databasir.core.domain.mock.data.ColumnMockRuleSaveRequest;
import com.databasir.core.domain.mock.data.MockDataGenerateCondition;
import com.databasir.core.domain.mock.data.MockDataRuleListCondition;
import com.databasir.core.domain.mock.data.MockDataRuleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "MockDataController", description = "MOCK SQL API")
public class MockDataController {

    private final MockDataService mockDataService;

    @GetMapping(Routes.MockData.GET_SQL_MOCK_DATA)
    @Operation(summary = "获取 Mock Insert SQL")
    public JsonData<String> getMockSql(@PathVariable("groupId") Integer groupId,
                                       @PathVariable("projectId") Integer projectId,
                                       @Valid MockDataGenerateCondition condition) {
        String sql = mockDataService.generateMockInsertSql(projectId, condition);
        return JsonData.ok(sql);
    }

    @GetMapping(Routes.MockData.GET_MOCK_RULE)
    @Operation(summary = "获取 Mock Rule")
    public JsonData<List<MockDataRuleResponse>> getMockRules(@PathVariable("groupId") Integer groupId,
                                                             @PathVariable("projectId") Integer projectId,
                                                             @Valid MockDataRuleListCondition condition) {
        List<MockDataRuleResponse> rules = mockDataService.listRules(projectId, condition);
        return JsonData.ok(rules);
    }

    @PostMapping(Routes.MockData.SAVE_MOCK_RULE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    @Operation(summary = "保存 Mock Rule")
    public JsonData<Void> saveMockRules(@PathVariable Integer groupId,
                                        @PathVariable Integer projectId,
                                        @PathVariable Integer tableId,
                                        @RequestBody @Valid List<ColumnMockRuleSaveRequest> rules) {
        mockDataService.saveMockRules(projectId, tableId, rules);
        return JsonData.ok();
    }
}
