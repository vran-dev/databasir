package com.databasir.core.domain.document.generator;

import org.springframework.stereotype.Component;

import java.io.OutputStream;

@Component
public class ExcelDocumentFileGenerator implements DocumentFileGenerator {

    @Override
    public boolean support(DocumentFileType type) {
        return type == DocumentFileType.EXCEL;
    }

    @Override
    public void generate(DocumentFileGenerateContext context, OutputStream outputStream) {

    }

    private void buildTableWithSheet() {
    }
}
