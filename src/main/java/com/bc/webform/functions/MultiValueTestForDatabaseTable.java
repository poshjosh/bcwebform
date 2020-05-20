package com.bc.webform.functions;

/**
 * @author hp
 */
public class MultiValueTestForDatabaseTable implements IsMultiValueField<String, String>{

    @Override
    public boolean isMultiValue(String tableName, String columnName) {
        // @TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
