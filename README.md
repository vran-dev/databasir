# Databasir

Database document generator

you could use `databasir` to generate database meta model, or render it as markdown / pdf (TODO) / html (TODO)

# How to use

## Database Meta to Java Model

```java
java.sql.Connection connection = ...;
DatabaseDocConfig config = DatabaseDocConfig.builder()
        .databaseName("Demo")
        .connection(connection)
        .build();
DatabaseDoc doc = JdbcDatabaseDocFactory.of().create(config).orElseThrow();
```

## Render as Markdown
```java
java.sql.Connection connection = ...;
DatabaseDocConfig config = DatabaseDocConfig.builder()
        .databaseName("Demo")
        .connection(connection)
        .build();
DatabaseDoc doc = JdbcDatabaseDocFactory.of().create(config).orElseThrow();
try (FileOutputStream out = new FileOutputStream("doc.md")) {
    RenderConfig renderConfig = new RenderConfig();
    Render.markdownRender(renderConfig).rendering(doc, out);
} catch (IOException e) {
    throw new IllegalStateException(e);
}
```

- Example

![](README/table-doc.png)


## Ignore tables or columns

support regex pattern to ignore table or column

```java
java.sql.Connection connection = ...;
DatabaseDocConfig config = DatabaseDocConfig.builder()
        .databaseName("Demo")
        .connection(connection)
        .ignoreTableRegexes(Arrays.asList("mysql_*"))
        .ignoreColumnRegexes(Arrays.asList("id"))
        .build();
DatabaseDoc doc = JdbcDatabaseDocFactory.of().create(config).orElseThrow();
```

## Extension

Default factory

- tableDocFactory ->  `com.databasir.core.doc.factory.jdbc.JdbcTableDocFactory`
- columnDocFactory -> `com.databasir.core.doc.factory.jdbc.JdbcTableColumnDocFactory`
- indexDocFactory -> `com.databasir.core.doc.factory.jdbc.JdbcTableIndexDocFactory`
- triggerDocFactory -> `com.databasir.core.doc.factory.jdbc.JdbcTableTriggerDocFactory`

Custom configuration
```java
java.sql.Connection connection = ...;
DatabaseDocConfig config = DatabaseDocConfig.builder()
        .databaseName("Demo")
        .connection(connection)
        .tableDocFactory(...) // your custom table doc factory
        .tableColumnDocFactory(...) // your custom column doc factory
        .tableIndexDocFactory(...) // your custom index doc factory
        .tableTriggerDocFactory(...) // your custom trigger doc factory
        .build();
DatabaseDoc doc = JdbcDatabaseDocFactory.of().create(config).orElseThrow();
```


