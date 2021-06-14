package com.bc.webform.form.member;

/**
 * @author hp
 */
public interface FormInputContext<S, F, V> extends 
        FormInputTypeProvider<S, F>, 
        FormInputNameProvider<S, F>,
        FormInputValueProvider<S, F, V>{
    
    boolean isOptional(S formDataSource, F dataSourceField);
    
    @Override
    String getType(S formDataSource, F dataSourceField);
    
    @Override
    String getName(S dataSource, F dataSourceField);

    @Override
    V getValue(S dataSource, F dataSourceField);
    
    @Override
    boolean setValue(S dataSource, F dataSourceField, V fieldValue);
} 
