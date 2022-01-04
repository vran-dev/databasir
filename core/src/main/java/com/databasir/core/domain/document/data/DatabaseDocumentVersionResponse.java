package com.databasir.core.domain.document.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatabaseDocumentVersionResponse {

    private Long version;

    private LocalDateTime createAt;

}
