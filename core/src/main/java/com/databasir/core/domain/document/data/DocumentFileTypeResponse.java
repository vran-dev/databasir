package com.databasir.core.domain.document.data;

import com.databasir.core.domain.document.generator.DocumentFileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentFileTypeResponse {

    private String name;

    private String fileExtension;

    private DocumentFileType type;

}
