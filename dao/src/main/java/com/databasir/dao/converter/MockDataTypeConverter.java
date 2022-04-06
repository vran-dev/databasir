package com.databasir.dao.converter;

import com.databasir.dao.enums.MockDataType;
import org.jooq.impl.EnumConverter;

public class MockDataTypeConverter extends EnumConverter<String, MockDataType> {

    public MockDataTypeConverter() {
        super(String.class, MockDataType.class);
    }

}
