# Databasir

Database document generator

# Features

- render as markdown
- render as html (TODO)
- render as PDF (TODO)

# Quick Start

```java
// First: get database connection
Class.forName("com.mysql.cj.jdbc.Driver");
Properties info=new Properties();
info.put("user","root");
info.put("password","123456");
// this config is used by mysql
info.put("useInformationSchema","true");
String url="jdbc:mysql://localhost:3306/patient?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
var connection=DriverManager.getConnection(url,info);

// Second: generate doc model
var config=DatabaseDocConfiguration.builder()
    .databaseName("patient")
    .connection(connection)
    .build();
DatabaseDoc doc = JdbcDatabaseDocFactory.of().create(config).orElseThrow();

// Final: Render as markdown
try(FileOutputStream out=new FileOutputStream("doc.md")){
    MarkdownRender.of(new RenderConfiguration()).rendering(doc,out);
}catch(IOException e){
    throw new IllegalStateException(e);
}
```