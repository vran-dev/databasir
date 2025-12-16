package com.databasir.core.domain.user.converter;

import com.databasir.core.domain.user.data.UserDetailResponse;
import com.databasir.core.domain.user.data.UserDetailResponse.UserRoleDetailResponse;
import com.databasir.core.domain.user.data.UserPageResponse;
import com.databasir.dao.tables.pojos.User;
import com.databasir.dao.tables.pojos.UserRole;
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
public class UserResponseConverterImpl implements UserResponseConverter {

    @Override
    public UserDetailResponse detailResponse(User pojo, List<UserRoleDetailResponse> roles) {
        if ( pojo == null && roles == null ) {
            return null;
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();

        if ( pojo != null ) {
            userDetailResponse.setAvatar( pojo.getAvatar() );
            userDetailResponse.setCreateAt( pojo.getCreateAt() );
            userDetailResponse.setEmail( pojo.getEmail() );
            userDetailResponse.setEnabled( pojo.getEnabled() );
            userDetailResponse.setId( pojo.getId() );
            userDetailResponse.setNickname( pojo.getNickname() );
            userDetailResponse.setUsername( pojo.getUsername() );
        }
        if ( roles != null ) {
            List<UserRoleDetailResponse> list = roles;
            if ( list != null ) {
                userDetailResponse.setRoles( new ArrayList<UserRoleDetailResponse>( list ) );
            }
        }

        return userDetailResponse;
    }

    @Override
    public UserRoleDetailResponse userRoleDetailResponse(UserRole pojo, String groupName) {
        if ( pojo == null && groupName == null ) {
            return null;
        }

        UserRoleDetailResponse userRoleDetailResponse = new UserRoleDetailResponse();

        if ( pojo != null ) {
            userRoleDetailResponse.setCreateAt( pojo.getCreateAt() );
            userRoleDetailResponse.setGroupId( pojo.getGroupId() );
            userRoleDetailResponse.setRole( pojo.getRole() );
        }
        if ( groupName != null ) {
            userRoleDetailResponse.setGroupName( groupName );
        }

        return userRoleDetailResponse;
    }

    @Override
    public UserPageResponse pageResponse(User pojo, Boolean isSysOwner, List<Integer> inGroupIds) {
        if ( pojo == null && isSysOwner == null && inGroupIds == null ) {
            return null;
        }

        UserPageResponse userPageResponse = new UserPageResponse();

        if ( pojo != null ) {
            userPageResponse.setCreateAt( pojo.getCreateAt() );
            userPageResponse.setEmail( pojo.getEmail() );
            userPageResponse.setEnabled( pojo.getEnabled() );
            userPageResponse.setId( pojo.getId() );
            userPageResponse.setNickname( pojo.getNickname() );
            userPageResponse.setUsername( pojo.getUsername() );
        }
        if ( isSysOwner != null ) {
            userPageResponse.setIsSysOwner( isSysOwner );
        }
        if ( inGroupIds != null ) {
            List<Integer> list = inGroupIds;
            if ( list != null ) {
                userPageResponse.setInGroupIds( new ArrayList<Integer>( list ) );
            }
        }

        return userPageResponse;
    }
}
