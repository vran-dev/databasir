package com.databasir.core.diff;

import com.databasir.core.diff.data.RootDiff;
import com.databasir.core.diff.processor.DatabaseDiffProcessor;
import com.databasir.core.meta.data.DatabaseMeta;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Diffs {

    private static final DatabaseDiffProcessor databaseDiffProcessor = new DatabaseDiffProcessor();

    public static RootDiff diff(DatabaseMeta original, DatabaseMeta current) {
        return databaseDiffProcessor.process(original, current);
    }

}
