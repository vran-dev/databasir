package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.core.BaseTest;
import com.databasir.core.domain.user.data.UserSource;
import com.databasir.core.domain.user.event.UserCreated;
import com.databasir.core.domain.user.event.UserPasswordRenewed;
import com.databasir.core.infrastructure.mail.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class UserEventSubscriberTest extends BaseTest {

    @Autowired
    private UserEventSubscriber userEventSubscriber;

    @MockBean
    private MailSender mailSender;

    @BeforeEach
    void mock() {
        doNothing().when(mailSender).sendHtml(any(), any(), any(), any());
    }

    @Test
    @Sql({
            "classpath:sql/event/subscriber/UserEventSubscriberTest.sql",
            "classpath:sql/init.sql"
    })
    void onPasswordRenewed() {
        var event = UserPasswordRenewed.builder()
                .email("ut@databasir.com")
                .newPassword("123456")
                .nickname("demo")
                .renewTime(LocalDateTime.now())
                .renewByUserId(1)
                .build();
        userEventSubscriber.onPasswordRenewed(event);
        verify(mailSender, times(1)).sendHtml(any(), any(), any(), any());
    }

    @Test
    @Sql({
            "classpath:sql/event/subscriber/UserEventSubscriberTest.sql",
            "classpath:sql/init.sql"
    })
    void onUserCreated() {
        var event = UserCreated.builder()
                .username("ut")
                .email("ut@databasir.com")
                .rawPassword("123456")
                .userId(1)
                .nickname("demo")
                .source(UserSource.MANUAL)
                .build();
        userEventSubscriber.onUserCreated(event);
        verify(mailSender, times(1)).sendHtml(any(), any(), any(), any());
    }
}