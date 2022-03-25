package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.core.BaseTest;
import com.databasir.core.diff.data.DiffType;
import com.databasir.core.diff.data.FieldDiff;
import com.databasir.core.diff.data.RootDiff;
import com.databasir.core.domain.document.event.DocumentUpdated;
import com.databasir.core.infrastructure.mail.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DocumentEventSubscriberTest extends BaseTest {

    @Autowired
    private DocumentEventSubscriber documentEventSubscriber;

    @MockBean
    private MailSender mailSender;

    @BeforeEach
    void mock() {
        doNothing().when(mailSender).batchSendHtml(any(), any(), any(), any());
    }

    @Test
    @Sql(scripts = {
            "classpath:sql/event/subscriber/DocumentEventSubscriberTest.sql",
            "classpath:sql/init.sql"
    })
    void onDocumentUpdated() {
        var event = new DocumentUpdated();
        RootDiff diff = new RootDiff();
        FieldDiff tablesFields = FieldDiff.builder()
                .fieldName("tables")
                .fields(List.of(
                                tableDiff("user", DiffType.ADDED),
                                tableDiff("role", DiffType.MODIFIED),
                                tableDiff("permission", DiffType.REMOVED)
                        )
                )
                .build();
        diff.setFields(List.of(tablesFields));
        diff.setDiffType(DiffType.MODIFIED);
        event.setDiff(diff);
        event.setNewVersion(2L);
        event.setOldVersion(1L);
        event.setProjectId(1);
        documentEventSubscriber.onDocumentUpdated(event);
        verify(mailSender, times(1)).batchSendHtml(any(), any(), any(), any());
    }

    private FieldDiff tableDiff(String tableName, DiffType type) {
        return FieldDiff.builder()
                .fieldName(tableName)
                .diffType(type)
                .build();
    }
}