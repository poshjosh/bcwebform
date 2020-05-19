package com.bc.webform;

import java.util.Optional;

/**
 * @author hp
 */


public interface ReferenceFormCreator<S, F> {
    
    ReferenceFormCreator NO_OP = (frm, src, fld) -> Optional.empty();
    
    Optional<Form> createReferenceForm(Form parent, S source, F field);
}
