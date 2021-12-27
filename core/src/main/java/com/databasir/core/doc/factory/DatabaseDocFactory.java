package com.databasir.core.doc.factory;

import com.databasir.core.doc.model.DatabaseDoc;

import java.sql.Connection;
import java.util.Optional;

public interface DatabaseDocFactory extends Sortable<DatabaseDocFactory> {

    Optional<DatabaseDoc> create(Connection connection, String databaseName);

}
