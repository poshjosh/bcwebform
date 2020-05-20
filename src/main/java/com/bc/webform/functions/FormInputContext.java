package com.bc.webform.functions;

/**
 * @author hp
 */
public interface FormInputContext<S, F, V> extends 
        FormInputTypeProvider<S, F>, FormInputValueProvider<S, F, V>{
    
    boolean isOptional(S formDataSource, F dataSourceField);
    
    @Override
    String getType(S formDataSource, F dataSourceField);
    
    String getName(S dataSource, F dataSourceField);

    @Override
    V getValue(S dataSource, F dataSourceField);
} 
