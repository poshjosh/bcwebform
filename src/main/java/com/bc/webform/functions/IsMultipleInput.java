package com.bc.webform.functions;

/**
 * @author hp
 */
public interface IsMultipleInput<S, F> {
    
    boolean isMultiple(S formDataSource, F dataSourceField);
}
