package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.diff.data.RootDiff;
import com.databasir.core.domain.document.event.DocumentUpdated;
import com.databasir.core.infrastructure.mail.MailSender;
import com.databasir.core.infrastructure.mail.MailTemplateProcessor;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.SysMailDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.tables.pojos.ProjectPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class DocumentEventSubscriber {

    private final ProjectDao projectDao;

    private final MailSender mailSender;

    private final UserDao userDao;

    private final SysMailDao sysMailDao;

    private final MailTemplateProcessor mailTemplateProcessor;

    @EventListener(classes = DocumentUpdated.class)
    public void onDocumentUpdated(DocumentUpdated created) {
        sysMailDao.selectOptionTopOne().ifPresent(mail -> {
            ProjectPojo project = projectDao.selectById(created.getProjectId());
            List<String> to = userDao.selectEnabledGroupMembers(project.getGroupId())
                    .stream()
                    .filter(UserPojo::getEnabled)
                    .map(UserPojo::getEmail)
                    .filter(userEmail -> userEmail.contains("@"))
                    .collect(Collectors.toList());
            String subject = project.getName() + " 文档有新的内容变更";
            List<Map<String, String>> diffs = created.getDiff()
                    .map(this::diffs)
                    .orElseGet(Collections::emptyList);
            Map<String, Object> context = new HashMap<>();
            context.put("diffs", diffs);
            context.put("projectName", project.getName());
            String message = mailTemplateProcessor.process("ftl/mail/DocumentUpdated.ftl", context);
            mailSender.batchSendHtml(mail, to, subject, message);
        });
    }

    private List<Map<String, String>> diffs(RootDiff diff) {
        if (diff.getDiffType() == DiffType.NONE) {
            return Collections.emptyList();
        } else {
            return diff.getFields()
                    .stream()
                    .filter(field -> field.getFieldName().equals("tables"))
                    .flatMap(f -> f.getFields().stream())
                    .map(table -> {
                        String tableName = table.getFieldName();
                        Map<String, String> map = Map.of(
                                "tableName", tableName,
                                "diffType", table.getDiffType().name()
                        );
                        return map;
                    })
                    .collect(Collectors.toList());
        }
    }

}
