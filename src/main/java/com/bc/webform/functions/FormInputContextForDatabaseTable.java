package com.bc.webform.functions;

import java.sql.ResultSetMetaData;
import java.util.Objects;

/**
 * @author hp
 */
public class FormInputContextForDatabaseTable implements FormInputContext<String, String, Object>{

    private final TableMetadata tableMetadata;
    
    private final FormInputTypeProvider<String, String> formInputTypeProvider;

    public FormInputContextForDatabaseTable(
            TableMetadata tableMetadata, 
            FormInputTypeProvider<String, String> formInputTypeProvider) {
        this.tableMetadata = Objects.requireNonNull(tableMetadata);
        this.formInputTypeProvider = Objects.requireNonNull(formInputTypeProvider);
    }
    
    @Override
    public boolean isOptional(String tableName, String columnName) {
        final int sqlNullable = this.tableMetadata.getColumnNullable(columnName);
        return sqlNullable == ResultSetMetaData.columnNullable;
    }

    @Override
    public String getType(String tableName, String columnName) {
        return this.formInputTypeProvider.getType(tableName, columnName);
    }

    @Override
    public String getName(String tableName, String columnName) {
        return columnName;
    }

    @Override
    public Object getValue(String tableName, String columnName) {
        // @TODO
        // When we are editing an existing model, we use this to get the value
        // However in this case, the model is represented by a database record
        // How do we access the record ?
        // The query should be something like
        // SELECT columnName FROM tableName WHERE [ID_COLUMN_NAME] = [ID_COLUMN_VALUE]
        // We can easily get the ID_COLUMN_NAME, given the tableName
        // What about the ID_COLUMN_VALUE ?
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
