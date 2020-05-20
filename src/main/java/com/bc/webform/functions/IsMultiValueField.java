package com.bc.webform.functions;

/**
 * @author hp
 */
public interface IsMultiValueField<S, F> {
    
    boolean isMultiValue(S formDataSource, F dataSourceField);
}
