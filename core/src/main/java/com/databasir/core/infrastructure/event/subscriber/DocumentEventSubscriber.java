package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.diff.data.RootDiff;
import com.databasir.core.domain.document.event.DocumentUpdated;
import com.databasir.core.infrastructure.mail.MailSender;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.SysMailDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.impl.UserRoleDao;
import com.databasir.dao.tables.pojos.ProjectPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class DocumentEventSubscriber {

    private final ProjectDao projectDao;

    private final MailSender mailSender;

    private final UserRoleDao userRoleDao;

    private final UserDao userDao;

    private final SysMailDao sysMailDao;

    @EventListener(classes = DocumentUpdated.class)
    public void onDocumentUpdated(DocumentUpdated created) {
        ProjectPojo project = projectDao.selectById(created.getProjectId());
        List<String> to = userDao.selectEnabledGroupMembers(project.getGroupId())
                .stream()
                .map(UserPojo::getEmail)
                .filter(mail -> mail.contains("@"))
                .collect(Collectors.toList());
        sysMailDao.selectOptionTopOne().ifPresent(mail -> {
            String subject = project.getName() + " 文档有新的版本";
            String message = created.getDiff()
                    .map(diff -> build(diff))
                    .orElseGet(() -> "首次文档同步常规");
            mailSender.batchSend(mail, to, subject, message);
        });
    }

    private String build(RootDiff diff) {
        if (diff.getDiffType() == DiffType.NONE) {
            return "";
        } else {
            return diff.getFields()
                    .stream()
                    .filter(field -> field.getFieldName().equals("tables"))
                    .flatMap(f -> f.getFields().stream())
                    .map(table -> {
                        String tableName = table.getFieldName();
                        String change = toDescription(table.getDiffType());
                        return tableName + " " + change;
                    })
                    .collect(Collectors.joining("\n"));
        }
    }

    private String toDescription(DiffType diffType) {
        switch (diffType) {
            case NONE:
                return "无变化";
            case ADDED:
                return "新增";
            case REMOVED:
                return "删除";
            case MODIFIED:
                return "修改";
            default:
                return diffType.name();
        }
    }
}
