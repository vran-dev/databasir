package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.TableResponse;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import com.databasir.dao.tables.pojos.TableDocument;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TableResponseConverter {

    default List<TableResponse> from(List<TableDocument> tables,
                                     Map<Integer, List<TableColumnDocument>> columnMapByTableId) {
        return tables.stream()
                .map(table -> from(table, columnMapByTableId.get(table.getId())))
                .collect(Collectors.toList());
    }

    TableResponse from(TableDocument table, List<TableColumnDocument> columns);

    TableResponse.ColumnResponse from(TableColumnDocument column);
}
