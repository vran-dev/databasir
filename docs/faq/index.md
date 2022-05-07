## Mysql

- 如何填写 database 和 schema ？

在 Mysql 中两者概念其实是一样的，都是数据库名称，填一样的就可以了

- 文档同步完成以后没有注释信息

这是 MYSQL 官方 JDBC 驱动的限制，需要在项目编辑页面的连接属性中添加 `useInformationSchema=true`

## Oracle
- 文档同步完成以后没有注释信息

这是 Oracle 官方 JDBC 驱动的限制，需要在项目编辑页面的连接属性中添加 `remarksReporting = true`


## Postgresql
- Postgresql 的 schema 该怎么填写？

如果项目没有做特殊的设置，那么 postgresql 的默认 schema 名称是 `public`

## SQL Server
- Sql Server 的 database 该怎么填写？

如果项目没有做特殊的设置，那么 Sql Server 的默认 database 名称是 `master`
