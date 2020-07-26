package com.bc.webform.form.member;

import com.bc.webform.form.Form;
import com.bc.webform.functions.IsReferencedTypeField;
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
    
    Optional<String> getReferencedFormHref(Form form, S formDataSource, F dataSourceField);
}
