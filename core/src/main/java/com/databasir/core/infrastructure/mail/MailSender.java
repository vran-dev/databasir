package com.databasir.core.infrastructure.mail;

import com.databasir.dao.tables.pojos.SysMailPojo;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

@Component
public class MailSender {

    public void batchSend(SysMailPojo mail, Collection<String> to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail.getUsername());
        message.setTo(to.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(content);
        JavaMailSender sender = initJavaMailSender(mail);
        sender.send(message);
    }

    public void send(SysMailPojo mail, String to, String subject, String content) {
        this.batchSend(mail, Collections.singleton(to), subject, content);
    }

    private JavaMailSender initJavaMailSender(SysMailPojo properties) {
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
