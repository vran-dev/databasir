package com.databasir.core.domain.user.data;

import java.util.Objects;

public interface UserSource {

    String MANUAL = "manual";

    static boolean isManual(String source) {
        return Objects.equals(source, MANUAL);
    }
}
