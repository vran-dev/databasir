package com.databasir.core.meta.provider;

import java.util.Optional;

/**
 * TODO use to extension repository
 */
public interface SqlProvider {

    Default DEFAULT = new Default();

    /**
     * <p>
     * generate sql to select database information, should return the follow columns
     * </p>
     *
     * <table>
     *     <tr>
     *         <th> column name </th>
     *         <th> column type </th>
     *         <th> description </th>
     *         <th> nullable </th>
     *     </tr>
     *     <tr>
     *         <td> TABLE_CAT </td>
     *         <td> String </td>
     *         <td> catalog name </td>
     *         <td> NO </td>
     *     </tr>
     * </table>
     * <br>
     *
     */
    default Optional<String> databaseMetaSql(String databaseName) {
        return Optional.empty();
    }

    /**
     * generate sql to select table information, should return the follow columns
     * <table>
     *     <tr>
     *         <th> column name </th>
     *         <th> column type </th>
     *         <th> description </th>
     *         <th> nullable </th>
     *     </tr>
     *     <tr>
     *         <td> TABLE_CAT </td>
     *         <td> String </td>
     *         <td> catalog name </td>
     *         <td> NO </td>
     *     </tr>
     * </table>
     *
     */
    default Optional<String> tableMetaSql(String databaseName, String tableName) {
        return Optional.empty();
    }

    default Optional<String> tableColumnMetaSql(String databaseName, String tableName) {
        return Optional.empty();
    }

    default Optional<String> tableIndexMetaSql(String databaseName, String tableName) {
        return Optional.empty();
    }

    default Optional<String> tableTriggerMetaSql(String databaseName, String tableName) {
        return Optional.empty();
    }

    class Default implements SqlProvider {

    }
}
