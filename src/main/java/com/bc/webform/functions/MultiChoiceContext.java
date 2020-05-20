package com.bc.webform.functions;

import java.util.Collections;
import java.util.Map;

/**
 * @author hp
 */
public interface MultiChoiceContext<S, F> {
    
    MultiChoiceContext NO_OP = (s, f) -> Collections.EMPTY_MAP;
    
    default boolean isMultiChoice(S formDataSource, F dataSourceField) {
        return false;
    }
    
    Map getChoices(S formDataSource, F dataSourceField); 
}
