package com.databasir.core.infrastructure.mail;

import com.databasir.common.SystemException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailTemplateProcessor {

    private final Configuration mailTemplateConfiguration;

    public String process(String templatePath, Map<String, Object> context) {
        try {
            Template template = mailTemplateConfiguration.getTemplate(templatePath);
            StringWriter writer = new StringWriter();
            template.process(context, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new SystemException("build template content error", e);
        }
    }
}
