package com.bc.webform.form.member;

import com.bc.webform.form.Form;
import com.bc.webform.functions.IsReferencedTypeField;
import java.util.Optional;

/**
 * @author hp
 */
public interface ReferencedFormContext<S, F> extends IsReferencedTypeField<S, F>{
    
    ReferencedFormContext NO_OP = (form, dataSourceField) -> Optional.empty();

    @Override
    default boolean isReferencedType(Form<S> form, F dataSourceField) {
        return false;
    }
    
    Optional<String> getReferencedFormHref(Form<S> form, F dataSourceField);
}
