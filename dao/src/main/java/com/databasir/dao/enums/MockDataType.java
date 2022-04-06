package com.databasir.dao.enums;

import java.util.List;

public enum MockDataType {

    // java faker supported type
    PHONE,
    EMAIL,
    FULL_NAME,
    FULL_ADDRESS,
    AVATAR_URL,
    UUID,

    // databasir custom type
    SCRIPT,
    CONSTANT,
    REF,
    AUTO,
    ;

    public static List<MockDataType> fakerTypes() {
        return List.of(PHONE, EMAIL, FULL_NAME, FULL_ADDRESS, AVATAR_URL, UUID);
    }
}
