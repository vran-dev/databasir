package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.diff.data.RootDiff;
import com.databasir.core.domain.document.converter.DocumentFullTextConverter;
import com.databasir.core.domain.document.event.DocumentUpdated;
import com.databasir.core.infrastructure.mail.MailSender;
import com.databasir.core.infrastructure.mail.MailTemplateProcessor;
import com.databasir.dao.impl.*;
import com.databasir.dao.tables.pojos.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.tools.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

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

    private final GroupDao groupDao;

    private final DatabaseDocumentDao databaseDocumentDao;

    private final TableDocumentDao tableDocumentDao;

    private final TableColumnDocumentDao tableColumnDocumentDao;

    private final DocumentDescriptionDao documentDescriptionDao;

    private final DocumentFullTextDao documentFullTextDao;

    private final DocumentFullTextConverter documentFullTextConverter;

    private final MailSender mailSender;

    private final UserDao userDao;

    private final SysMailDao sysMailDao;

    private final MailTemplateProcessor mailTemplateProcessor;

    private final TransactionTemplate transactionTemplate;

    @EventListener(classes = DocumentUpdated.class)
    @Async("mailThreadPoolTaskExecutor")
    public void sendMailOnUpdated(DocumentUpdated event) {
        sysMailDao.selectOptionTopOne().ifPresent(mail -> {
            Project project = projectDao.selectById(event.getProjectId());
            List<String> to = userDao.selectEnabledGroupMembers(project.getGroupId())
                    .stream()
                    .map(User::getEmail)
                    .filter(userEmail -> userEmail.contains("@"))
                    .collect(Collectors.toList());
            if (!to.isEmpty()) {
                String subject = project.getName() + " 文档有新的内容变更";
                List<Map<String, String>> diffs = event.getDiff()
                        .map(this::diffs)
                        .orElseGet(Collections::emptyList);
                Map<String, Object> context = new HashMap<>();
                context.put("diffs", diffs);
                context.put("projectName", project.getName());
                String message = mailTemplateProcessor.process("ftl/mail/DocumentUpdated.ftl", context);
                mailSender.batchSendHtml(mail, to, subject, message);
            }
        });
    }

    @EventListener(classes = DocumentUpdated.class)
    public void updateFullTextOnUpdated(DocumentUpdated event) {
        updateFullTextIndex(event);
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

    private void updateFullTextIndex(DocumentUpdated event) {
        Integer projectId = event.getProjectId();
        Project project = projectDao.selectById(projectId);
        Group group = groupDao.selectById(project.getGroupId());
        // save full text
        var descriptionMapByJoinName = documentDescriptionDao.selectByProjectId(projectId)
                .stream()
                .collect(Collectors.toMap(
                        d -> {
                            if (d.getColumnName() == null) {
                                return d.getTableName();
                            }
                            return String.join(".",
                                    d.getTableName(),
                                    StringUtils.defaultIfBlank(d.getColumnName(), ""));
                        },
                        DocumentDescription::getContent,
                        (a, b) -> a));
        DatabaseDocument database = databaseDocumentDao.selectById(event.getDatabaseDocumentId());
        List<TableDocument> tables = tableDocumentDao.selectByDatabaseDocumentId(event.getDatabaseDocumentId());

        // begin transaction
        transactionTemplate.executeWithoutResult(status -> {
            // delete all project.table full text
            int deletedRows = documentFullTextDao.deleteTableFullText(event.getProjectId());
            // recreate
            for (TableDocument table : tables) {
                List<TableColumnDocument> columns = tableColumnDocumentDao.selectByTableDocumentId(table.getId());
                List<DocumentFullText> fullTextPojoList = columns.stream()
                        .map(column -> {
                            String tableName = table.getName();
                            String tableDescription = descriptionMapByJoinName.get(tableName);
                            String columnDescription = descriptionMapByJoinName.get(tableName + "." + column.getName());
                            return documentFullTextConverter.toPojo(group, project, database, table, column,
                                    tableDescription, columnDescription);
                        })
                        .collect(Collectors.toList());
                documentFullTextDao.batchInsert(fullTextPojoList);
            }
            log.info("refresh project({}) tables fultext success, deleted {} items", projectId, deletedRows);
        });
    }
}
