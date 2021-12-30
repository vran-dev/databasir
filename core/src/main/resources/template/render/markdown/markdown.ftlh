# ${meta.databaseName}

| seq | name | comment |
| ---- | --------------- | ------ |
<#list meta.tables as table>
| ${table_index+1} | [${table.tableName}](#${table.tableName}) | ${table.tableComment!'N/A'} |
</#list>

<#if config.renderTables>
    <#list meta.tables as table>
## ${table.tableName}

    <#if config.renderColumns>
### Columns

| seq | name | type | nullable | auto increment| default | comment |
| ---- | ------ | ------ | ------ | -------- | ------ | ------ |
<#list table.columns as column>
| ${column_index+1} | ${column.name} | ${column.type} | ${column.isNullable?then('YES','')} | ${column.isAutoIncrement?then('YES', '')} | ${column.isNullable?then(column.defaultValue!'NULL', column.defaultValue!'')} | ${column.comment!''} |
</#list>
    </#if>

   <#if config.renderIndexes>
### Indexes

| seq | name | unique | primary key | columns |
| ---- | ---- | -------- | -------- | ------ |
<#list table.indexes as index>
| ${index_index+1} | ${index.indexName} | ${index.isUniqueKey?then('YES', '')} | ${index.isPrimaryKey?then('YES','')} | ${index.columnNames?join(', ')} |
</#list>
    </#if>

   <#if config.renderTriggers>
### Triggers

| seq | name | timing | statement | created |
| ---- | ---- | -------- | --------- | -------- |
    <#list table.triggers as trigger>
| ${trigger_index} | ${trigger.name} | ${trigger.timing + " " + trigger.manipulation } | ${trigger.statement?replace("\n", "<br>")?replace("\r", " ")} | ${trigger.createAt} |
    </#list>
   </#if>

    </#list>
</#if>