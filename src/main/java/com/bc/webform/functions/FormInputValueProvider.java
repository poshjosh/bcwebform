package com.bc.webform.functions;

/**
 * @author hp
 */
public interface FormInputValueProvider<S, F, V> {
    
    V getValue(S dataSource, F dataSourceField);
}
