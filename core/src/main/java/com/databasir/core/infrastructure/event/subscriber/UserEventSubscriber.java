package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.core.domain.user.data.UserSource;
import com.databasir.core.domain.user.event.UserCreated;
import com.databasir.core.domain.user.event.UserPasswordRenewed;
import com.databasir.core.infrastructure.mail.MailSender;
import com.databasir.dao.impl.SysMailDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.tables.pojos.UserPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * TODO use html template instead of simple message
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventSubscriber {

    private final MailSender mailSender;

    private final SysMailDao sysMailDao;

    private final UserDao userDao;

    @EventListener(classes = UserPasswordRenewed.class)
    public void onPasswordRenewed(UserPasswordRenewed event) {
        UserPojo operator = userDao.selectById(event.getRenewByUserId());
        sysMailDao.selectOptionTopOne()
                .ifPresent(mailPojo -> {
                    String renewBy = operator.getNickname();
                    String subject = "Databasir 密码重置提醒";
                    String message = String.format("Hi %s,\r\n 您的密码已被 %s 重置，新密码为 %s",
                            event.getNickname(),
                            renewBy,
                            event.getNewPassword());
                    mailSender.send(mailPojo, event.getEmail(), subject, message);
                });
    }

    @EventListener(classes = UserCreated.class)
    public void onUserCreated(UserCreated event) {
        String subject = "Databasir 账户创建成功";
        String message;
        if (UserSource.isManual(event.getSource())) {
            message = String.format("Hi %s\r\n您的 Databasir 账户已创建成功，用户名：%s，密码：%s",
                    event.getNickname(), event.getUsername(), event.getRawPassword());
        } else {
            message = String.format("Hi %s\r\n您的 Databasir 账户已创建成功，用户名：%s",
                    event.getNickname(), event.getUsername());
        }
        sysMailDao.selectOptionTopOne()
                .ifPresent(mailPojo -> {
                    mailSender.send(mailPojo, event.getEmail(), subject, message);
                });
    }

}
