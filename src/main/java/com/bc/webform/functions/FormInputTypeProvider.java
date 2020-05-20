package com.bc.webform.functions;

/**
 * @author hp
 */
public interface FormInputTypeProvider<S, F> {

    String getType(S formDataSource, F dataSourceField);
}

