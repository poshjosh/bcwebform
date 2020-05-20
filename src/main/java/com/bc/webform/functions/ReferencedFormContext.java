package com.bc.webform.functions;

import com.bc.webform.Form;
import java.util.Optional;

/**
 * @author hp
 */


public interface ReferencedFormContext<S, F> extends IsReferencedTypeField<S, F>{
    
    ReferencedFormContext NO_OP = (frm, src, fld) -> null;

    @Override
    default boolean isReferencedType(S formDataSource, F dataSourceField) {
        return false;
    }
    
    default Optional<Form> createReferencedForm(Form form, S formDataSource, F dataSourceField) {
        return Optional.empty();
    }
    
    Optional<String> getReferencedFormHref(Form form, S formDataSource, F dataSourceField);
}
