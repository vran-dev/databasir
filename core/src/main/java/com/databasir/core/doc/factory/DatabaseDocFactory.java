package com.databasir.core.doc.factory;

import com.databasir.core.doc.model.DatabaseDoc;

import java.util.Optional;

public interface DatabaseDocFactory extends Sortable<DatabaseDocFactory> {

    Optional<DatabaseDoc> create(DatabaseDocConfig configuration);

}
