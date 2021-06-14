package com.bc.webform.functions;

import com.bc.webform.form.Form;

/**
 * @author hp
 */
public interface IsReferencedTypeField<S, F> {

    boolean isReferencedType(Form<S> form, F dataSourceField);
}

