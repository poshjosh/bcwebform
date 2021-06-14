package com.bc.webform.form.member;

/**
 * @author hp
 */
public interface FormInputTypeProvider<S, F> {

    String getType(S formDataSource, F dataSourceField);
    
    String getDataType(S formDataSource, F dataSourceField);
}

