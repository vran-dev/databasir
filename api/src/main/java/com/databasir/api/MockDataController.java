package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.mock.MockDataService;
import com.databasir.core.domain.mock.data.ColumnMockRuleSaveRequest;
import com.databasir.core.domain.mock.data.MockDataGenerateCondition;
import com.databasir.core.domain.mock.data.MockDataRuleListCondition;
import com.databasir.core.domain.mock.data.MockDataRuleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class MockDataController {

    private final MockDataService mockDataService;

    @GetMapping(Routes.MockData.GET_SQL_MOCK_DATA)
    public JsonData<String> getMockSql(@PathVariable("groupId") Integer groupId,
                                       @PathVariable("projectId") Integer projectId,
                                       @Valid MockDataGenerateCondition condition) {
        String sql = mockDataService.generateMockInsertSql(projectId, condition);
        return JsonData.ok(sql);
    }

    @GetMapping(Routes.MockData.GET_MOCK_RULE)
    public JsonData<List<MockDataRuleResponse>> getMockRules(@PathVariable("groupId") Integer groupId,
                                                             @PathVariable("projectId") Integer projectId,
                                                             @Valid MockDataRuleListCondition condition) {
        List<MockDataRuleResponse> rules = mockDataService.listRules(projectId, condition);
        return JsonData.ok(rules);
    }

    @PostMapping(Routes.MockData.SAVE_MOCK_RULE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    public JsonData<Void> saveMockRules(@PathVariable Integer groupId,
                                        @PathVariable Integer projectId,
                                        @PathVariable Integer tableId,
                                        @RequestBody @Valid List<ColumnMockRuleSaveRequest> rules) {
        mockDataService.saveMockRules(projectId, tableId, rules);
        return JsonData.ok();
    }
}
