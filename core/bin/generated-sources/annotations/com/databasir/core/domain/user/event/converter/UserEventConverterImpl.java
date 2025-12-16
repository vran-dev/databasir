package com.databasir.core.domain.user.event.converter;

import com.databasir.core.domain.user.event.UserCreated;
import com.databasir.core.domain.user.event.UserCreated.UserCreatedBuilder;
import com.databasir.core.domain.user.event.UserPasswordRenewed;
import com.databasir.core.domain.user.event.UserPasswordRenewed.UserPasswordRenewedBuilder;
import com.databasir.dao.tables.pojos.User;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class UserEventConverterImpl implements UserEventConverter {

    @Override
    public UserPasswordRenewed userPasswordRenewed(User pojo, Integer renewByUserId, LocalDateTime renewTime, String newPassword) {
        if ( pojo == null && renewByUserId == null && renewTime == null && newPassword == null ) {
            return null;
        }

        UserPasswordRenewedBuilder userPasswordRenewed = UserPasswordRenewed.builder();

        if ( pojo != null ) {
            userPasswordRenewed.email( pojo.getEmail() );
            userPasswordRenewed.nickname( pojo.getNickname() );
        }
        if ( renewByUserId != null ) {
            userPasswordRenewed.renewByUserId( renewByUserId );
        }
        if ( renewTime != null ) {
            userPasswordRenewed.renewTime( renewTime );
        }
        if ( newPassword != null ) {
            userPasswordRenewed.newPassword( newPassword );
        }

        return userPasswordRenewed.build();
    }

    @Override
    public UserCreated userCreated(User pojo, String source, String rawPassword, Integer userId) {
        if ( pojo == null && source == null && rawPassword == null && userId == null ) {
            return null;
        }

        UserCreatedBuilder userCreated = UserCreated.builder();

        if ( pojo != null ) {
            userCreated.email( pojo.getEmail() );
            userCreated.nickname( pojo.getNickname() );
            userCreated.username( pojo.getUsername() );
        }
        if ( source != null ) {
            userCreated.source( source );
        }
        if ( rawPassword != null ) {
            userCreated.rawPassword( rawPassword );
        }
        if ( userId != null ) {
            userCreated.userId( userId );
        }

        return userCreated.build();
    }
}
