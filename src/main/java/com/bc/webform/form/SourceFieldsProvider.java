package com.bc.webform.form;

import com.bc.webform.form.Form;
import java.util.Set;

/**
 * @author hp
 * @param <S> The type of the Form source
 * @param <F> The type of the field source
 */
public interface SourceFieldsProvider<S, F> {

    Set<F> apply(Form<S> form, S formSource);
}
