package com.databasir.core.domain.group.converter;

import com.databasir.core.domain.group.data.GroupMemberPageResponse;
import com.databasir.core.domain.group.data.GroupPageResponse;
import com.databasir.core.domain.group.data.GroupResponse;
import com.databasir.core.domain.group.data.GroupResponse.GroupOwnerResponse;
import com.databasir.dao.tables.pojos.Group;
import com.databasir.dao.tables.pojos.User;
import com.databasir.dao.value.GroupMemberDetailPojo;
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
public class GroupResponseConverterImpl implements GroupResponseConverter {

    @Override
    public GroupPageResponse toResponse(Group group, List<String> groupOwnerNames, Integer projectCount) {
        if ( group == null && groupOwnerNames == null && projectCount == null ) {
            return null;
        }

        GroupPageResponse groupPageResponse = new GroupPageResponse();

        if ( group != null ) {
            groupPageResponse.setDescription( group.getDescription() );
            groupPageResponse.setId( group.getId() );
            groupPageResponse.setName( group.getName() );
        }
        if ( groupOwnerNames != null ) {
            List<String> list = groupOwnerNames;
            if ( list != null ) {
                groupPageResponse.setGroupOwnerNames( new ArrayList<String>( list ) );
            }
        }
        if ( projectCount != null ) {
            groupPageResponse.setProjectCount( projectCount );
        }

        return groupPageResponse;
    }

    @Override
    public GroupResponse toResponse(Group group, List<User> groupOwners) {
        if ( group == null && groupOwners == null ) {
            return null;
        }

        GroupResponse groupResponse = new GroupResponse();

        if ( group != null ) {
            groupResponse.setCreateAt( group.getCreateAt() );
            groupResponse.setDescription( group.getDescription() );
            groupResponse.setId( group.getId() );
            groupResponse.setName( group.getName() );
            groupResponse.setUpdateAt( group.getUpdateAt() );
        }
        if ( groupOwners != null ) {
            groupResponse.setGroupOwners( userListToGroupOwnerResponseList( groupOwners ) );
        }

        return groupResponse;
    }

    @Override
    public GroupMemberPageResponse toResponse(GroupMemberDetailPojo data) {
        if ( data == null ) {
            return null;
        }

        GroupMemberPageResponse groupMemberPageResponse = new GroupMemberPageResponse();

        groupMemberPageResponse.setCreateAt( data.getCreateAt() );
        groupMemberPageResponse.setEmail( data.getEmail() );
        groupMemberPageResponse.setNickname( data.getNickname() );
        groupMemberPageResponse.setRole( data.getRole() );
        groupMemberPageResponse.setUserId( data.getUserId() );
        groupMemberPageResponse.setUsername( data.getUsername() );

        return groupMemberPageResponse;
    }

    protected GroupOwnerResponse userToGroupOwnerResponse(User user) {
        if ( user == null ) {
            return null;
        }

        GroupOwnerResponse groupOwnerResponse = new GroupOwnerResponse();

        groupOwnerResponse.setEmail( user.getEmail() );
        groupOwnerResponse.setId( user.getId() );
        groupOwnerResponse.setNickname( user.getNickname() );

        return groupOwnerResponse;
    }

    protected List<GroupOwnerResponse> userListToGroupOwnerResponseList(List<User> list) {
        if ( list == null ) {
            return null;
        }

        List<GroupOwnerResponse> list1 = new ArrayList<GroupOwnerResponse>( list.size() );
        for ( User user : list ) {
            list1.add( userToGroupOwnerResponse( user ) );
        }

        return list1;
    }
}
