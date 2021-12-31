# ${meta.databaseName}

| seq | name | comment |
| ---- | --------------- | ------ |
<#list meta.tables as table>
    | ${table_index+1} | [${table.name}](#${table.name}) | ${table.comment!'N/A'} |
</#list>

<#if config.renderTables>
    <#list meta.tables as table>
        ## ${table.name}

        <#if config.renderColumns>
            ### Columns

            | seq | name | type | nullable | auto increment| default | comment |
            | ---- | ------ | ------ | ------ | -------- | ------ | ------ |
            <#list table.columns as column>
                | ${column_index+1} | ${column.name} | ${column.type} | ${column.isNullable?then('YES','NO')} | ${column.isAutoIncrement?then('YES', 'NO')} | ${column.isNullable?then(column.defaultValue!'NULL', column.defaultValue!'')} | ${column.comment!''} |
            </#list>
        </#if>

   <#if config.renderIndexes>
### Indexes

| seq | name | unique | primary key | columns |
| ---- | ---- | -------- | -------- | ------ |
       <#list table.indexes as index>
           | ${index_index+1} | ${index.name} | ${index.isUniqueKey?then('YES', 'NO')} | ${index.isPrimaryKey?then('YES','NO')} | ${index.columnNames?join(', ')} |
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