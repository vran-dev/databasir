package com.databasir.core.doc.factory.jdbc;

import com.databasir.core.doc.factory.TableColumnDocFactory;
import com.databasir.core.doc.factory.TableDocCreateContext;
import com.databasir.core.doc.model.ColumnDoc;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class JdbcTableColumnDocFactory implements TableColumnDocFactory {
    @Override
    public List<ColumnDoc> create(TableDocCreateContext context) {

        try {
            ResultSet indexResults = context.getDatabaseMetaData().getIndexInfo(context.getDatabase(), null, context.getTableName(), false, false);
        } catch (SQLException e) {
        }

        return null;
    }
}
