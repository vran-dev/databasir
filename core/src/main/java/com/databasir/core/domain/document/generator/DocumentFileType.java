package com.databasir.core.domain.document.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DocumentFileType {

    MARKDOWN("md", "Markdown"),

    PLANT_UML_ER_SVG("svg", "UML SVG"),

    PLANT_UML_ER_PNG("png", "UML PNG"),
    ;

    private String fileExtension;

    private String name;

}
