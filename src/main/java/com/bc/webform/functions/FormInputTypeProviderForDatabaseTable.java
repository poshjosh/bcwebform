package com.bc.webform.functions;

import com.bc.webform.StandardFormFieldTypes;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author hp
 */
public class FormInputTypeProviderForDatabaseTable 
        extends FormMemberTypeForSqlTypeProvider
        implements FormInputTypeProvider<String, String>{

    private final TableMetadata tableMetadata;

    private final Predicate<String> passwordFieldTest;
    
    public FormInputTypeProviderForDatabaseTable(TableMetadata tableMetadata) {
        this(tableMetadata, new IsPasswordField(), StandardFormFieldTypes.TEXT);
    }
    
    public FormInputTypeProviderForDatabaseTable(
            TableMetadata tableMetadata,
            Predicate<String> passwordInputTest, 
            String resultIfNone) {
        super(resultIfNone);
        this.tableMetadata = Objects.requireNonNull(tableMetadata);
        this.passwordFieldTest = Objects.requireNonNull(passwordInputTest);
    }

    @Override
    public String getType(String tableName, String columnName) {
        final String type;
        if(this.passwordFieldTest.test(columnName)) {
            type = StandardFormFieldTypes.PASSWORD; 
        }else{
            final int sqlDataType = this.tableMetadata.getColumnDataType(columnName);
            type = super.apply(sqlDataType);
        }
        return type;
    }
}
