package com.databasir.core.domain.document.generator;

import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.io.OutputStream;

public interface DocumentFileGenerator {

    boolean support(DocumentFileType type);

    void generate(DocumentFileGenerateContext context, OutputStream outputStream);

    @Getter
    @Builder
    class DocumentFileGenerateContext {

        @NonNull
        private DocumentFileType documentFileType;

        @NonNull
        private DatabaseDocumentResponse databaseDocument;

    }
}
