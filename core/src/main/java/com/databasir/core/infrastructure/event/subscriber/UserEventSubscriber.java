package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.core.domain.user.data.UserSource;
import com.databasir.core.domain.user.event.UserCreated;
import com.databasir.core.domain.user.event.UserPasswordRenewed;
import com.databasir.core.infrastructure.mail.MailSender;
import com.databasir.core.infrastructure.mail.MailTemplateProcessor;
import com.databasir.dao.impl.SysMailDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.tables.pojos.UserPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

    private final MailTemplateProcessor mailTemplateProcessor;

    @EventListener(classes = UserPasswordRenewed.class)
    public void onPasswordRenewed(UserPasswordRenewed event) {
        UserPojo operator = userDao.selectById(event.getRenewByUserId());
        sysMailDao.selectOptionTopOne()
                .ifPresent(mailPojo -> {
                    String renewBy = operator.getNickname();
                    Map<String, Object> context = new HashMap<>();
                    context.put("renewBy", renewBy);
                    context.put("nickname", event.getNickname());
                    context.put("newPassword", event.getNewPassword());
                    String message = template("ftl/mail/PasswordRenew.ftl", context);
                    String subject = "Databasir 密码重置通知";
                    mailSender.sendHtml(mailPojo, event.getEmail(), subject, message);
                });
    }

    @EventListener(classes = UserCreated.class)
    public void onUserCreated(UserCreated event) {
        if (UserSource.isManual(event.getSource())) {
            sysMailDao.selectOptionTopOne()
                    .ifPresent(mailPojo -> {
                        Map<String, Object> context = new HashMap<>();
                        context.put("nickname", event.getNickname());
                        context.put("username", event.getUsername());
                        context.put("password", event.getRawPassword());
                        String message = template("ftl/mail/UserCreated.ftl", context);
                        String subject = "Databasir 账户创建成功";
                        mailSender.sendHtml(mailPojo, event.getEmail(), subject, message);
                    });
        }
    }

    private String template(String templatePath, Map<String, Object> context) {
        return mailTemplateProcessor.process(templatePath, context);
    }
}
