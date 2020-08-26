package com.bc.webform.form.member;

import com.bc.webform.form.Form;
import com.bc.webform.functions.IsReferencedTypeField;

/**
 * @author hp
 */
public interface ReferencedFormContext<S, F> extends IsReferencedTypeField<S, F>{
    
    ReferencedFormContext NO_OP = (form, dataSourceField) -> false;
    
    @Override
    boolean isReferencedType(Form<S> form, F dataSourceField);
}
