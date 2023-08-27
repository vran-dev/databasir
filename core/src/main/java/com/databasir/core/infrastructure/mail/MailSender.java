package com.databasir.core.infrastructure.mail;

import com.databasir.common.SystemException;
import com.databasir.dao.tables.pojos.SysMail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

@Component
public class MailSender {

    public void sendHtml(SysMail mail,
                         String to,
                         String subject,
                         String content) {
        this.batchSendHtml(mail, Collections.singleton(to), subject, content);
    }

    public void batchSendHtml(SysMail mail,
                              Collection<String> to,
                              String subject,
                              String content) {
        JavaMailSender sender = initJavaMailSender(mail);
        MimeMessage mimeMessage = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            if (StringUtils.isNotBlank(mail.getMailFrom())) {
                helper.setFrom(mail.getMailFrom());
            } else {
                helper.setFrom(mail.getUsername());
            }
            helper.setTo(to.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(content, true);
            sender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new SystemException("send mail error", e);
        }
    }

    private JavaMailSender initJavaMailSender(SysMail properties) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(properties.getSmtpHost());
        if (properties.getSmtpPort() != null) {
            sender.setPort(properties.getSmtpPort());
        }
        sender.setUsername(properties.getUsername());
        sender.setPassword(properties.getPassword());
        if (properties.getUseSsl()) {
            sender.setProtocol("smtps");
        } else {
            sender.setProtocol("smtp");
        }
        sender.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return sender;
    }
}
