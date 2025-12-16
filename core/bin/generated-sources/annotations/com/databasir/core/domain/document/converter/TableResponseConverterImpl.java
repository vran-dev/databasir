package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.TableResponse;
import com.databasir.core.domain.document.data.TableResponse.ColumnResponse;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import com.databasir.dao.tables.pojos.TableDocument;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class TableResponseConverterImpl implements TableResponseConverter {

    @Override
    public TableResponse from(TableDocument table, List<TableColumnDocument> columns) {
        if ( table == null && columns == null ) {
            return null;
        }

        TableResponse tableResponse = new TableResponse();

        if ( table != null ) {
            tableResponse.setId( table.getId() );
            tableResponse.setName( table.getName() );
        }
        if ( columns != null ) {
            tableResponse.setColumns( tableColumnDocumentListToColumnResponseList( columns ) );
        }

        return tableResponse;
    }

    @Override
    public ColumnResponse from(TableColumnDocument column) {
        if ( column == null ) {
            return null;
        }

        ColumnResponse columnResponse = new ColumnResponse();

        columnResponse.setId( column.getId() );
        columnResponse.setName( column.getName() );
        columnResponse.setType( column.getType() );

        return columnResponse;
    }

    protected List<ColumnResponse> tableColumnDocumentListToColumnResponseList(List<TableColumnDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<ColumnResponse> list1 = new ArrayList<ColumnResponse>( list.size() );
        for ( TableColumnDocument tableColumnDocument : list ) {
            list1.add( from( tableColumnDocument ) );
        }

        return list1;
    }
}
