package com.bc.webform.form.member;

import com.bc.webform.choices.SelectOption;
import java.util.Collections;
import java.util.List;

/**
 * @author hp
 */
public interface MultiChoiceContext<S, F> {
    
    MultiChoiceContext NO_OP = (s, f) -> Collections.EMPTY_LIST;
    
    default boolean isMultiChoice(S formDataSource, F dataSourceField) {
        return false;
    }
    
    List<SelectOption> getChoices(S formDataSource, F dataSourceField); 
}
