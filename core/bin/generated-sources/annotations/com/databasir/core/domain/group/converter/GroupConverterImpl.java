package com.databasir.core.domain.group.converter;

import com.databasir.core.domain.group.data.GroupCreateRequest;
import com.databasir.core.domain.group.data.GroupUpdateRequest;
import com.databasir.dao.tables.pojos.Group;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class GroupConverterImpl implements GroupConverter {

    @Override
    public Group of(GroupCreateRequest groupCreateRequest) {
        if ( groupCreateRequest == null ) {
            return null;
        }

        Group group = new Group();

        group.setName( nullToEmpty( groupCreateRequest.getName() ) );
        group.setDescription( nullToEmpty( groupCreateRequest.getDescription() ) );

        return group;
    }

    @Override
    public Group of(GroupUpdateRequest groupUpdateRequest) {
        if ( groupUpdateRequest == null ) {
            return null;
        }

        Group group = new Group();

        group.setId( groupUpdateRequest.getId() );
        group.setName( nullToEmpty( groupUpdateRequest.getName() ) );
        group.setDescription( nullToEmpty( groupUpdateRequest.getDescription() ) );

        return group;
    }
}
