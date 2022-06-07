package com.databasir.core.domain.document.diff;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTypeFieldEqualFunctionTest {

    @Test
    public void testApplyAllNull() {
        BaseTypeFieldEqualFunction equalFunction = new BaseTypeFieldEqualFunction(List.of());

        // Setup
        final Object that = null;
        final Object other = null;

        // Run the test
        final Boolean result = equalFunction.apply(that, other);

        // Verify the results
        assertTrue(result);
    }

    @Test
    public void testApplySimpleObject() {
        SimpleObject that = SimpleObject.builder()
                .id(1)
                .height(180L)
                .name("test")
                .build();

        SimpleObject other = SimpleObject.builder()
                .id(1)
                .height(180L)
                .name("test")
                .build();

        BaseTypeFieldEqualFunction equalFunction = new BaseTypeFieldEqualFunction(List.of());
        final Boolean result = equalFunction.apply(that, other);
        assertTrue(result);
    }

    @Test
    public void testApplyComplexObject() {
        SimpleObject thatItem = SimpleObject.builder()
                .id(1)
                .height(180L)
                .name("test")
                .build();
        ComplexObject thatObj = new ComplexObject();
        thatObj.setId(1);
        thatObj.setName("eq");
        thatObj.setItems(List.of(thatItem));

        SimpleObject otherItem = SimpleObject.builder()
                .id(1)
                .height(180L)
                .name("test")
                .build();
        ComplexObject otherObj = new ComplexObject();
        otherObj.setId(1);
        otherObj.setName("eq");
        otherObj.setItems(List.of(otherItem));

        BaseTypeFieldEqualFunction equalFunction = new BaseTypeFieldEqualFunction(List.of());
        final Boolean result = equalFunction.apply(thatObj, otherObj);
        assertTrue(result);
    }

    @Test
    public void testApplyComplexObject2() {
        SimpleObject thatItem = SimpleObject.builder()
                .id(1)
                .height(180L)
                .name("test")
                .build();
        ComplexObject thatObj = new ComplexObject();
        thatObj.setId(1);
        thatObj.setName("eq");
        thatObj.setItems(List.of(thatItem));

        ComplexObject otherObj = new ComplexObject();
        otherObj.setId(1);
        otherObj.setName("eq");
        otherObj.setItems(List.of());

        BaseTypeFieldEqualFunction equalFunction = new BaseTypeFieldEqualFunction(List.of());
        final Boolean result = equalFunction.apply(thatObj, otherObj);
        assertFalse(result);
    }
}
