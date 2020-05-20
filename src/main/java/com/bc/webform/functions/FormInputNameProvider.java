package com.bc.webform.functions;

/**
 * @author hp
 */
public interface FormInputNameProvider<S, F> {
 
    String getName(S dataSource, F dataSourceField);
}
