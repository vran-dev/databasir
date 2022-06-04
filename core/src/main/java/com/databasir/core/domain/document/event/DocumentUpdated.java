package com.databasir.core.domain.document.event;

import com.databasir.core.diff.data.RootDiff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUpdated {

    private RootDiff diff;

    private Long newVersion;

    private Long oldVersion;

    private Integer projectId;

    private Integer databaseDocumentId;

    public Optional<Long> getOldVersion() {
        return Optional.ofNullable(oldVersion);
    }

    public Optional<RootDiff> getDiff() {
        return Optional.ofNullable(diff);
    }
}
