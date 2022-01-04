package com.databasir.core.infrastructure.mail;

import com.databasir.dao.tables.pojos.SysMailPojo;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MailSender {

    public void send(SysMailPojo mail, String to, String subject, String content) {
        JavaMailSender sender = initJavaMailSender(mail);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        sender.send(message);
    }

    private JavaMailSender initJavaMailSender(SysMailPojo properties) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(properties.getSmtpHost());
        if (properties.getSmtpPort() != null) {
            sender.setPort(properties.getSmtpPort());
        }
        sender.setUsername(properties.getUsername());
        sender.setPassword(properties.getPassword());
        sender.setProtocol("smtp");
        sender.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return sender;
    }
}
