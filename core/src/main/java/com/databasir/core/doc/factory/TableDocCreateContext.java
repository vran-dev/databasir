package com.databasir.core.doc.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TableDocCreateContext {

    private String database;

    private String tableName;

    private Connection connection;

    private DatabaseMetaData databaseMetaData;

}
