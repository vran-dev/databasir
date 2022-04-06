package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.TableResponse;
import com.databasir.dao.tables.pojos.TableColumnDocumentPojo;
import com.databasir.dao.tables.pojos.TableDocumentPojo;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TableResponseConverter {

    default List<TableResponse> from(List<TableDocumentPojo> tables,
                                     Map<Integer, List<TableColumnDocumentPojo>> columnMapByTableId) {
        return tables.stream()
                .map(table -> from(table, columnMapByTableId.get(table.getId())))
                .collect(Collectors.toList());
    }

    TableResponse from(TableDocumentPojo table, List<TableColumnDocumentPojo> columns);

    TableResponse.ColumnResponse from(TableColumnDocumentPojo column);
}
