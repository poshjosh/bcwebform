package com.bc.webform.functions;

/**
 * @author hp
 */
public interface FormInputValueProvider<S, F, V> {
    
    V getValue(S dataSource, F dataSourceField);
    
    boolean setValue(S dataSource, F dataSourceField, V fieldValue);
}
