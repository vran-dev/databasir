package com.databasir.core.diff.service;

import com.databasir.core.diff.Diffs;
import com.databasir.core.diff.data.DiffType;
import com.databasir.core.diff.data.FieldDiff;
import com.databasir.core.diff.data.RootDiff;
import com.databasir.core.meta.data.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DiffsTest {

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .setTimeZone(TimeZone.getDefault());
    }

    @Test
    void diffEmptyDatabaseSame() {
        DatabaseMeta original = DatabaseMeta.builder().build();
        DatabaseMeta current = DatabaseMeta.builder().build();
        RootDiff diff = Diffs.diff(original, current);
        assertEquals(DiffType.NONE, diff.getDiffType());
    }

    @Test
    void diffDatabaseSame() {
        DatabaseMeta original = load("ut/diffsTest/diffDatabaseSame/original.json");
        DatabaseMeta current = load("ut/diffsTest/diffDatabaseSame/current.json");

        RootDiff diff = Diffs.diff(original, current);
        assertEquals(DiffType.NONE, diff.getDiffType());
        assertSame(1, diff.getFields().size());
        FieldDiff tableField = diff.getFields().iterator().next();
        assertEquals("tables", tableField.getFieldName());
        assertEquals(DiffType.NONE, tableField.getDiffType());
        assertTrue(tableField.getFields().isEmpty());
    }

    @Test
    void diffDatabaseAdded() {
        DatabaseMeta current = load("ut/diffsTest/diffDatabaseAdded/current.json");
        RootDiff diff = Diffs.diff(null, current);
        assertEquals(DiffType.ADDED, diff.getDiffType());
    }

    @Test
    void diffDatabaseRemoved() {
        DatabaseMeta original = load("ut/diffsTest/diffDatabaseRemoved/original.json");
        RootDiff diff = Diffs.diff(original, null);
        assertEquals(DiffType.REMOVED, diff.getDiffType());
    }

    @Test
    void diffDatabaseModified() {
        DatabaseMeta original = load("ut/diffsTest/diffDatabaseModified/original.json");
        DatabaseMeta current = load("ut/diffsTest/diffDatabaseModified/current.json");

        // database
        RootDiff diff = Diffs.diff(original, current);
        assertEquals(DiffType.MODIFIED, diff.getDiffType());
        assertSame(2, diff.getFields().size());
        Map<String, FieldDiff> diffMap = diff.getFields()
                .stream()
                .collect(Collectors.toMap(d -> d.getFieldName(), Function.identity()));

        // productVersion
        assertTrue(diffMap.containsKey("productVersion"));
        assertIsModified(diffMap.get("productVersion"));
        assertEquals(original.getProductVersion(), diffMap.get("productVersion").getOriginal());
        assertEquals(current.getProductVersion(), diffMap.get("productVersion").getCurrent());

        // tables
        assertTrue(diffMap.containsKey("tables"));
        assertIsNone(diffMap.get("tables"));
        assertTrue(diffMap.get("tables").getFields().isEmpty());
    }

    @Test
    void diffTableAdded() {
        DatabaseMeta original = load("ut/diffsTest/diffTableAdded/original.json");
        DatabaseMeta current = load("ut/diffsTest/diffTableAdded/current.json");

        // database
        RootDiff diff = Diffs.diff(original, current);
        assertEquals(DiffType.MODIFIED, diff.getDiffType());

        // add table: departments, dept_emp
        assertSame(1, diff.getFields().size());
        FieldDiff tableField = diff.getFields().iterator().next();
        assertEquals("tables", tableField.getFieldName());
        assertEquals(DiffType.ADDED, tableField.getDiffType());

        // tables without original & current value
        assertNull(tableField.getOriginal());
        assertNull(tableField.getCurrent());

        // add two tables
        assertSame(2, tableField.getFields().size());
        Map<String, FieldDiff> tableFieldMap = tableField.getFields()
                .stream()
                .collect(Collectors.toMap(FieldDiff::getFieldName, Function.identity()));
        Map<String, TableMeta> currentTablesMap = current.getTables()
                .stream()
                .collect(Collectors.toMap(TableMeta::getName, Function.identity()));

        List.of("departments", "dept_emp").forEach(tableName -> {
            assertTrue(tableFieldMap.containsKey(tableName));
            FieldDiff departments = tableFieldMap.get(tableName);
            assertIsAdded(departments);
            assertNull(departments.getOriginal());
            assertNotNull(departments.getCurrent());
            assertEquals(currentTablesMap.get(tableName), departments.getCurrent());
        });
    }

    @Test
    void diffTableRemoved() {
        DatabaseMeta original = load("ut/diffsTest/diffTableRemoved/original.json");
        DatabaseMeta current = load("ut/diffsTest/diffTableRemoved/current.json");

        // database
        RootDiff diff = Diffs.diff(original, current);
        assertEquals(DiffType.MODIFIED, diff.getDiffType());

        // remove table: departments, dept_emp
        assertSame(1, diff.getFields().size());
        FieldDiff tableField = diff.getFields().iterator().next();
        assertEquals("tables", tableField.getFieldName());
        assertEquals(DiffType.REMOVED, tableField.getDiffType());

        // tables without original & current value
        assertNull(tableField.getOriginal());
        assertNull(tableField.getCurrent());

        // remove two tables
        assertSame(2, tableField.getFields().size());
        Map<String, FieldDiff> tableFieldMap = tableField.getFields()
                .stream()
                .collect(Collectors.toMap(FieldDiff::getFieldName, Function.identity()));
        Map<String, TableMeta> originalTablesMap = original.getTables()
                .stream()
                .collect(Collectors.toMap(TableMeta::getName, Function.identity()));

        List.of("departments", "dept_emp").forEach(tableName -> {
            assertTrue(tableFieldMap.containsKey(tableName));
            FieldDiff field = tableFieldMap.get(tableName);
            assertIsRemoved(field);
            assertNull(field.getCurrent());
            assertNotNull(field.getOriginal());
            assertEquals(originalTablesMap.get(tableName), field.getOriginal());
        });
    }

    @Test
    void diffTableModified() {
        DatabaseMeta original = load("ut/diffsTest/diffTableModified/original.json");
        DatabaseMeta current = load("ut/diffsTest/diffTableModified/current.json");

        // database
        RootDiff diff = Diffs.diff(original, current);
        assertEquals(DiffType.MODIFIED, diff.getDiffType());

        /**
         * modify three table: departments, dept_emp
         * - dept_manager: add comment, change table type
         * - departments: add comment
         * - dept_emp: add comment
         */
        assertSame(1, diff.getFields().size());
        FieldDiff tableField = diff.getFields().iterator().next();
        assertEquals("tables", tableField.getFieldName());
        assertEquals(DiffType.MODIFIED, tableField.getDiffType());

        // tables without original & current value
        assertNull(tableField.getOriginal());
        assertNull(tableField.getCurrent());

        // modify 3 tables
        assertSame(3, tableField.getFields().size());
        Map<String, FieldDiff> tableFieldMap = tableField.getFields()
                .stream()
                .collect(Collectors.toMap(FieldDiff::getFieldName, Function.identity()));
        Map<String, TableMeta> originalTableMap = original.getTables()
                .stream()
                .collect(Collectors.toMap(TableMeta::getName, Function.identity()));
        Map<String, TableMeta> currentTableMap = current.getTables()
                .stream()
                .collect(Collectors.toMap(TableMeta::getName, Function.identity()));

        /**
         * - departments: add comment
         * - dept_emp: add comment
         * - dept_manager: add comment, change table type
         */
        List.of("departments", "dept_emp", "dept_manager").forEach(tableName -> {
            assertTrue(tableFieldMap.containsKey(tableName));
            FieldDiff departments = tableFieldMap.get(tableName);
            assertIsModified(departments);
            assertNull(departments.getCurrent());
            assertNull(departments.getOriginal());
            if ("dept_manager".equals(tableName)) {
                // columns \ indexes \ triggers \ forgienKes \ comment
                assertSame(6, departments.getFields().size());
                FieldDiff departmentCommentField = departments.getFields().stream()
                        .filter(f -> f.getFieldName().equals("type"))
                        .findAny()
                        .orElseThrow();
                assertEquals(originalTableMap.get(tableName).getType(), departmentCommentField.getOriginal());
                assertEquals(currentTableMap.get(tableName).getType(), departmentCommentField.getCurrent());
            } else {
                // columns \ indexes \ triggers \ forgienKes \ comment
                assertSame(5, departments.getFields().size());
            }

            FieldDiff departmentCommentField = departments.getFields().stream()
                    .filter(f -> f.getFieldName().equals("comment"))
                    .findAny()
                    .orElseThrow();
            assertEquals(originalTableMap.get(tableName).getComment(), departmentCommentField.getOriginal());
            assertEquals(currentTableMap.get(tableName).getComment(), departmentCommentField.getCurrent());
        });
    }

    /**
     * add columns \ indexes \ triggers \ foreignKeys
     * <p>
     * department:
     * - add column [deleted], [dept_code]
     * - add index [dept_deleted]
     * - add triggers [before_insert]
     * - add foreignKeys [dept_emp_ibfk_1]
     * </p>
     */
    @Test
    void diffTableFieldsAdded() {
        DatabaseMeta original = load("ut/diffsTest/diffTableFieldsAdded/original.json");
        DatabaseMeta current = load("ut/diffsTest/diffTableFieldsAdded/current.json");

        // database
        RootDiff diff = Diffs.diff(original, current);
        assertEquals(DiffType.MODIFIED, diff.getDiffType());

        // modified table: departments
        assertSame(1, diff.getFields().size());
        FieldDiff tableField = diff.getFields().iterator().next();
        assertEquals("tables", tableField.getFieldName());
        assertEquals(DiffType.MODIFIED, tableField.getDiffType());

        // tables without original & current value
        assertNull(tableField.getOriginal());
        assertNull(tableField.getCurrent());

        // modified 1 tables
        assertSame(1, tableField.getFields().size());
        Map<String, FieldDiff> tableFieldMap = tableField.getFields()
                .stream()
                .collect(Collectors.toMap(FieldDiff::getFieldName, Function.identity()));
        TableMeta currentDepartment = current.getTables()
                .stream()
                .filter(t -> "departments".equals(t.getName()))
                .findFirst()
                .orElseThrow();

        List.of("departments").forEach(tableName -> {
            assertTrue(tableFieldMap.containsKey(tableName));
            FieldDiff departments = tableFieldMap.get(tableName);
            assertIsModified(departments);
            assertNull(departments.getOriginal());
            assertNull(departments.getCurrent());

            // columns / indexes / triggers / foreignKes
            assertSame(4, departments.getFields().size());
            for (FieldDiff field : departments.getFields()) {
                assertIsModified(field);
                if ("columns".equals(field.getFieldName())) {
                    assertSame(2, field.getFields().size());
                    for (FieldDiff columnField : field.getFields()) {
                        assertIsAdded(columnField);
                        assertNull(columnField.getOriginal());
                        assertNotNull(columnField.getCurrent());
                        ColumnMeta colMeta = (ColumnMeta) columnField.getCurrent();
                        boolean matched = currentDepartment.getColumns().stream()
                                .anyMatch(idx -> Objects.equals(idx, colMeta));
                        assertTrue(matched);
                    }
                }
                if ("indexes".equals(field.getFieldName())) {
                    assertSame(1, field.getFields().size());
                    FieldDiff indexes = field.getFields().iterator().next();
                    assertIsAdded(indexes);
                    assertNull(indexes.getOriginal());
                    IndexMeta index = (IndexMeta) indexes.getCurrent();
                    assertNotNull(index);
                    boolean matched = currentDepartment.getIndexes().stream()
                            .anyMatch(idx -> Objects.equals(idx, index));
                    assertTrue(matched);
                }
                if ("triggers".equals(field.getFieldName())) {
                    assertSame(1, field.getFields().size());
                    FieldDiff triggers = field.getFields().iterator().next();
                    assertIsAdded(triggers);
                    assertNull(triggers.getOriginal());
                    TriggerMeta tg = (TriggerMeta) triggers.getCurrent();
                    assertNotNull(tg);
                    boolean matched = currentDepartment.getTriggers().stream().anyMatch(t -> Objects.equals(t, tg));
                    assertTrue(matched);
                }
                if ("foreignKeys".equals(field.getFieldName())) {
                    assertSame(1, field.getFields().size());
                    FieldDiff foreignKeys = field.getFields().iterator().next();
                    assertIsAdded(foreignKeys);
                    assertNull(foreignKeys.getOriginal());
                    ForeignKeyMeta fk = (ForeignKeyMeta) foreignKeys.getCurrent();
                    assertNotNull(fk);
                    boolean matched = currentDepartment.getForeignKeys().stream().anyMatch(f -> Objects.equals(f, fk));
                    assertTrue(matched);
                }
            }
        });
    }

    /**
     * dept_emp
     * fk: dept_emp_ibfk_2
     * column: from_date, to_date
     * index: dept_no
     * trigger: before_insert
     */
    @Test
    void diffTableFieldsRemoved() {
        DatabaseMeta original = load("ut/diffsTest/diffTableFieldsRemoved/original.json");
        DatabaseMeta current = load("ut/diffsTest/diffTableFieldsRemoved/current.json");

        // database
        RootDiff diff = Diffs.diff(original, current);
        assertEquals(DiffType.MODIFIED, diff.getDiffType());

        // modified table: departments
        assertSame(1, diff.getFields().size());
        FieldDiff tableField = diff.getFields().iterator().next();
        assertEquals("tables", tableField.getFieldName());
        assertEquals(DiffType.MODIFIED, tableField.getDiffType());

        // tables without original & current value
        assertNull(tableField.getOriginal());
        assertNull(tableField.getCurrent());

        // modified 1 tables
        assertSame(1, tableField.getFields().size());
        Map<String, FieldDiff> tableFieldMap = tableField.getFields()
                .stream()
                .collect(Collectors.toMap(FieldDiff::getFieldName, Function.identity()));
        TableMeta originalTable = original.getTables()
                .stream()
                .filter(t -> "dept_emp".equals(t.getName()))
                .findFirst()
                .orElseThrow();

        /**
         * dept_emp
         * fk: dept_emp_ibfk_2
         * column: from_date, to_date
         * index: dept_no
         * trigger: before_insert
         */
        List.of("dept_emp").forEach(tableName -> {
            assertTrue(tableFieldMap.containsKey(tableName));
            FieldDiff departments = tableFieldMap.get(tableName);
            assertIsModified(departments);
            assertNull(departments.getOriginal());
            assertNull(departments.getCurrent());

            // columns / indexes / triggers / foreignKes
            assertSame(4, departments.getFields().size());
            for (FieldDiff field : departments.getFields()) {
                assertIsModified(field);
                if ("columns".equals(field.getFieldName())) {
                    assertSame(2, field.getFields().size());
                    for (FieldDiff columnField : field.getFields()) {
                        assertIsRemoved(columnField);
                        assertNull(columnField.getCurrent());
                        assertNotNull(columnField.getOriginal());
                        ColumnMeta colMeta = (ColumnMeta) columnField.getOriginal();
                        boolean matched = originalTable.getColumns().stream()
                                .anyMatch(idx -> Objects.equals(idx, colMeta));
                        assertTrue(matched);
                    }
                }
                if ("indexes".equals(field.getFieldName())) {
                    assertSame(1, field.getFields().size());
                    FieldDiff indexes = field.getFields().iterator().next();
                    assertIsRemoved(indexes);
                    assertNull(indexes.getCurrent());
                    IndexMeta index = (IndexMeta) indexes.getOriginal();
                    assertNotNull(index);
                    boolean matched = originalTable.getIndexes().stream()
                            .anyMatch(idx -> Objects.equals(idx, index));
                    assertTrue(matched);
                }
                if ("triggers".equals(field.getFieldName())) {
                    assertSame(1, field.getFields().size());
                    FieldDiff triggers = field.getFields().iterator().next();
                    assertIsRemoved(triggers);
                    assertNull(triggers.getCurrent());
                    TriggerMeta tg = (TriggerMeta) triggers.getOriginal();
                    assertNotNull(tg);
                    boolean matched = originalTable.getTriggers().stream().anyMatch(t -> Objects.equals(t, tg));
                    assertTrue(matched);
                }
                if ("foreignKeys".equals(field.getFieldName())) {
                    assertSame(1, field.getFields().size());
                    FieldDiff foreignKeys = field.getFields().iterator().next();
                    assertIsRemoved(foreignKeys);
                    assertNull(foreignKeys.getCurrent());
                    ForeignKeyMeta fk = (ForeignKeyMeta) foreignKeys.getOriginal();
                    assertNotNull(fk);
                    boolean matched = originalTable.getForeignKeys().stream().anyMatch(f -> Objects.equals(f, fk));
                    assertTrue(matched);
                }
            }
        });
    }

    @Test
    void diffTableFieldsModified() {
        DatabaseMeta original = load("ut/diffsTest/diffTableFieldsModified/original.json");
        DatabaseMeta current = load("ut/diffsTest/diffTableFieldsModified/current.json");

        // database
        RootDiff diff = Diffs.diff(original, current);
        assertEquals(DiffType.MODIFIED, diff.getDiffType());

        // modified table: departments, dept_emp
        assertSame(1, diff.getFields().size());
        FieldDiff tablesField = diff.getFields().iterator().next();
        assertEquals("tables", tablesField.getFieldName());
        assertEquals(DiffType.MODIFIED, tablesField.getDiffType());

        // tables without original & current value
        assertNull(tablesField.getOriginal());
        assertNull(tablesField.getCurrent());

        // modified 1 tables
        assertSame(2, tablesField.getFields().size());
        Map<String, FieldDiff> tableFieldMap = tablesField.getFields()
                .stream()
                .collect(Collectors.toMap(FieldDiff::getFieldName, Function.identity()));
        Map<String, TableMeta> originalTaleMap = original.getTables()
                .stream()
                .collect(Collectors.toMap(TableMeta::getName, Function.identity()));
        Map<String, TableMeta> currentTableMap = current.getTables()
                .stream()
                .collect(Collectors.toMap(TableMeta::getName, Function.identity()));

        /**
         * departments
         * column: dept_no add comment
         * column: dept_name add comment
         * indexes: dept_name change unique=false
         *
         * dept_emp
         * column: emp_no change default value
         * column: dept_noL change auto increment true
         * indexes: dept_no
         * triggers: before_insert
         * foreignKeys: dept_emp_ibfk_2
         */
        List.of("departments", "dept_emp").forEach(tableName -> {
            assertTrue(tableFieldMap.containsKey(tableName));
            FieldDiff tableField = tableFieldMap.get(tableName);
            assertIsModified(tableField);
            assertNull(tableField.getOriginal());
            assertNull(tableField.getCurrent());

            // columns / indexes / triggers / foreignKes
            assertSame(4, tableField.getFields().size());
            for (FieldDiff field : tableField.getFields()) {
                if ("columns".equals(field.getFieldName())) {
                    assertIsModified(field);
                    assertSame(2, field.getFields().size());
                    for (FieldDiff columnField : field.getFields()) {
                        assertIsModified(columnField);
                        assertNotNull(columnField.getCurrent());
                        assertNotNull(columnField.getOriginal());
                        ColumnMeta originalCol = (ColumnMeta) columnField.getOriginal();
                        boolean matched = originalTaleMap.get(tableName)
                                .getColumns()
                                .stream()
                                .anyMatch(idx -> Objects.equals(idx, originalCol));
                        assertTrue(matched);

                        ColumnMeta currentCol = (ColumnMeta) columnField.getCurrent();
                        assertTrue(currentTableMap.get(tableName)
                                .getColumns()
                                .stream()
                                .anyMatch(idx -> Objects.equals(idx, currentCol)));
                    }
                }
                if ("indexes".equals(field.getFieldName())) {
                    assertIsModified(field);
                    assertSame(1, field.getFields().size());
                    FieldDiff indexes = field.getFields().iterator().next();
                    assertIsModified(indexes);
                    assertNotNull(indexes.getCurrent());
                    assertNotNull(indexes.getOriginal());

                    IndexMeta originalIndex = (IndexMeta) indexes.getOriginal();
                    boolean matched = originalTaleMap.get(tableName)
                            .getIndexes()
                            .stream()
                            .anyMatch(idx -> Objects.equals(idx, originalIndex));
                    assertTrue(matched);

                    IndexMeta currentIndex = (IndexMeta) indexes.getCurrent();
                    assertTrue(currentTableMap.get(tableName)
                            .getIndexes()
                            .stream()
                            .anyMatch(idx -> Objects.equals(idx, currentIndex)));
                }
                if (tableName.equals("dept_emp") && "triggers".equals(field.getFieldName())) {
                    assertIsModified(field);
                    assertSame(1, field.getFields().size());
                    FieldDiff triggers = field.getFields().iterator().next();
                    assertIsModified(triggers);
                    assertNotNull(triggers.getCurrent());
                    assertNotNull(triggers.getOriginal());
                    TriggerMeta originalTg = (TriggerMeta) triggers.getOriginal();
                    assertTrue(originalTaleMap.get(tableName).getTriggers().stream()
                            .anyMatch(t -> Objects.equals(t, originalTg)));

                    TriggerMeta currentTg = (TriggerMeta) triggers.getCurrent();
                    assertTrue(currentTableMap.get(tableName).getTriggers().stream()
                            .anyMatch(t -> Objects.equals(t, currentTg)));
                }
                if (tableName.equals("dept_emp") && "foreignKeys".equals(field.getFieldName())) {
                    assertIsModified(field);
                    assertSame(1, field.getFields().size());
                    FieldDiff foreignKeys = field.getFields().iterator().next();
                    assertIsModified(foreignKeys);
                    assertNotNull(foreignKeys.getCurrent());
                    assertNotNull(foreignKeys.getOriginal());
                    ForeignKeyMeta originalFk = (ForeignKeyMeta) foreignKeys.getOriginal();
                    assertTrue(originalTaleMap.get(tableName)
                            .getForeignKeys().stream().anyMatch(f -> Objects.equals(f, originalFk)));

                    ForeignKeyMeta currentFk = (ForeignKeyMeta) foreignKeys.getCurrent();
                    assertTrue(currentTableMap.get(tableName)
                            .getForeignKeys().stream().anyMatch(f -> Objects.equals(f, currentFk)));
                }
                if (tableName.equals("departments") && List.of("foreignKeys", "triggers").contains(field.getFieldName())) {
                    assertIsNone(field);
                }
            }
        });
    }

    private void assertIsModified(FieldDiff diff) {
        assertEquals(DiffType.MODIFIED, diff.getDiffType());
    }

    private void assertIsNone(FieldDiff diff) {
        assertEquals(DiffType.NONE, diff.getDiffType());
    }

    private void assertIsRemoved(FieldDiff diff) {
        assertEquals(DiffType.REMOVED, diff.getDiffType());
    }

    private void assertIsAdded(FieldDiff diff) {
        assertEquals(DiffType.ADDED, diff.getDiffType());
    }

    private DatabaseMeta load(String url) {
        URL originalUrl = Thread.currentThread().getContextClassLoader()
                .getResource(url);
        try {
            return objectMapper.readValue(originalUrl, DatabaseMeta.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}