# ${meta.databaseName}
|                 |      |
| --------------- | ---- |
| Database name   |      |
| Product name    |      |
| Product version |      |

## Overview
|  | name | type | comment |
| ---- | --------------- | ------ | ------ |
<#list meta.tables as table>
| ${table_index+1} | [${table.name}](#${table.name}) |  ${table.type} |  ${table.comment!'N/A'} |
</#list>

<#if config.renderTables>
    <#list meta.tables as table>
## ${table.name}
        <#if config.renderColumns>
### Columns

|  | name | type | primary Key | nullable | auto increment| default | comment |
| --- | ---- | ---- | ----------- | -------- | ------------- | ------- | ------- |
            <#list table.columns as column>
| ${column_index+1} | ${column.name} | ${column.type} | ${column.isPrimaryKey?then('YES','NO')} | ${column.nullable } | ${column.autoIncrement} | ${column.defaultValue!'NULL'} | ${column.comment!''} |
            </#list>
        </#if>

<#if config.renderIndexes>
### Indexes

|  | name | unique | columns |
| --- | ---- | ------ | ------- |
       <#list table.indexes as index>
| ${index_index+1} | ${index.name} | ${index.isUniqueKey?then('YES', 'NO')} | ${index.columnNames?join(', ')} |
       </#list>
    </#if>

   <#if config.renderTriggers>
### Triggers

|  | name | timing | statement | created |
| --- | ---- | ------ | --------- | ------- |
    <#list table.triggers as trigger>
| ${trigger_index} | ${trigger.name} | ${trigger.timing + " " + trigger.manipulation } | ${trigger.statement?replace("\n", "<br>")?replace("\r", " ")} | ${trigger.createAt} |
    </#list>
   </#if>

    </#list>
</#if>