package com.databasir.dao.value;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DatabaseDocumentVersionPojo {

    private Integer id;

    private Long version;

    private LocalDateTime createAt;
}
