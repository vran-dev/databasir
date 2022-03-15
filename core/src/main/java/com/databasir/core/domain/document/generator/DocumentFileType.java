package com.databasir.core.domain.document.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DocumentFileType {

    MARKDOWN("md"), EXCEL("xlsx");

    private String fileExtension;
}
