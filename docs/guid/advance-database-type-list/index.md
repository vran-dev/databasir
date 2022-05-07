# 数据库模板

## MYSQL

<!-- tabs:start -->
#### **mysql-8**

```json
{
  "author": {
  },
  "template": {
    "databaseType": "mysql-8.0.28",
    "jdbcDriverFileUrl": "https://maven.aliyun.com/repository/central/mysql/mysql-connector-java/8.0.28/mysql-connector-java-8.0.28.jar",
    "icon": "",
    "description": "mysql-8.0.28",
    "jdbcDriverClassName": "com.mysql.cj.jdbc.Driver",
    "jdbcProtocol": "jdbc:mysql",
    "urlPattern": "{{jdbc.protocol}}://{{db.url}}/{{db.name}}"
  }
}
```
#### **mysql-5**

```json
{
  "author": {
  },
  "template": {
    "databaseType": "mysql-5.1.49",
    "jdbcDriverFileUrl": "https://maven.aliyun.com/repository/central/mysql/mysql-connector-java/5.1.49/mysql-connector-java-5.1.49.jar",
    "icon": "",
    "description": "mysql-5.1.49",
    "jdbcDriverClassName": "com.mysql.jdbc.Driver",
    "jdbcProtocol": "jdbc:mysql",
    "urlPattern": "{{jdbc.protocol}}://{{db.url}}/{{db.name}}"
  }
}
```

<!-- tabs:end -->

## PostgreSQL

```json
{
  "author": {
  },
  "template": {
    "databaseType": "postgresql-42.3.4",
    "jdbcDriverFileUrl": "https://maven.aliyun.com/repository/central/org/postgresql/postgresql/42.3.4/postgresql-42.3.4.jar",
    "icon": "",
    "description": "postgresql jdbc version 42.3.4",
    "jdbcDriverClassName": "org.postgresql.Driver",
    "jdbcProtocol": "jdbc:postgresql",
    "urlPattern": "{{jdbc.protocol}}://{{db.url}}/{{db.name}}"
  }
}

```

## MariaDB

```json
{
  "author": {
  },
  "template": {
    "databaseType": "mariadb-3.0.3",
    "jdbcDriverFileUrl": "https://maven.aliyun.com/repository/central/org/mariadb/jdbc/mariadb-java-client/3.0.3/mariadb-java-client-3.0.3.jar",
    "icon": "",
    "description": "mariadb-3.0.3",
    "jdbcDriverClassName": "org.mariadb.jdbc.Driver",
    "jdbcProtocol": "jdbc:mariadb",
    "urlPattern": "{{jdbc.protocol}}://{{db.url}}/{{db.name}}"
  }
}
```

## Oracle

<!-- tabs:start -->
#### **thin**

```json
{
  "author": {
  },
  "template": {
    "databaseType": "oracle-thin-12.2.0.1",
    "jdbcDriverFileUrl": "https://maven.aliyun.com/repository/central/com/oracle/database/jdbc/ojdbc8/12.2.0.1/ojdbc8-12.2.0.1.jar",
    "icon": "",
    "description": "oracle-thin-12.2.0.1",
    "jdbcDriverClassName": "oracle.jdbc.OracleDriver",
    "jdbcProtocol": "jdbc:oracle:thin",
    "urlPattern": "{{jdbc.protocol}}:@{{db.url}}:{{db.name}}"
  }
}
```

<!-- tabs:end -->


## SqlServer

```json
{
  "author": {
  },
  "template": {
    "databaseType": "sqlServer-10.2.0.jre8",
    "jdbcDriverFileUrl": "https://maven.aliyun.com/repository/central/com/microsoft/sqlserver/mssql-jdbc/10.2.0.jre8/mssql-jdbc-10.2.0.jre8.jar",
    "icon": "",
    "description": "sqlServer-10.2.0.jre8",
    "jdbcDriverClassName": "com.microsoft.sqlserver.jdbc.SQLServerDriver",
    "jdbcProtocol": "jdbc:sqlserver",
    "urlPattern": "{{jdbc.protocol}}://{{db.url}};databaseName={{db.name}}"
  }
}
```

## 达梦
```json
// TODO 
```