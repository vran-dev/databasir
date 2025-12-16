package com.databasir.core.domain.user.converter;

import com.databasir.core.domain.user.data.UserCreateRequest;
import com.databasir.dao.tables.pojos.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public User of(UserCreateRequest request, String hashedPassword) {
        if ( request == null && hashedPassword == null ) {
            return null;
        }

        User user = new User();

        if ( request != null ) {
            user.setEmail( request.getEmail() );
            user.setUsername( request.getUsername() );
            user.setNickname( request.getNickname() );
            user.setAvatar( request.getAvatar() );
            user.setEnabled( request.getEnabled() );
        }
        if ( hashedPassword != null ) {
            user.setPassword( hashedPassword );
        }

        return user;
    }
}
