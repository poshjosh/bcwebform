package com.bc.webform.form.member.builder;

import com.bc.webform.Builder;
import com.bc.webform.form.Form;
import com.bc.webform.form.member.FormInputContext;
import com.bc.webform.form.member.FormMember;
import com.bc.webform.form.member.MultiChoiceContext;
import com.bc.webform.form.member.ReferencedFormContext;
import com.bc.webform.functions.IsMultipleInput;

/**
 * 
 * @author hp
 * @param <S> The type of the data formDataSource for the Form
 * @param <F> The type of the fields of the data formDataSource for the Form
 * @param <V> The type of value each dataSource in the form holds
 */
public interface FormMemberBuilder<S, F, V> extends Builder<FormMember<F, V>>{

    @Override
    FormMember<F, V> build();

    /**
     * Apply default values.
     * 
     * The default values are gotten by building a {@link com.bc.webform.DefaultFormField DefaultFormField}
     * for the supplied name.
     * 
     * <p><b>Note:</b></p>
     * This method should be the first method called. Calling the method 
     * after any value has been set will lead to IllegalStateException 
     * @param form The form to which the form dataSource being built is a member.
     * @param name The name of the {@link com.bc.webform.DefaultFormField DefaultFormField} 
     * to build and whose values will be used to update the current build.
     * @throws com.bc.webform.exceptions.ValuesOverwriteByDefaultException
     * @return this object
     */
    FormMemberBuilder<S, F, V> applyDefaults(Form form, String name)
            throws com.bc.webform.exceptions.ValuesOverwriteByDefaultException;
    
    @Override
    FormMemberBuilder<S, F, V> apply(FormMember<F, V> t);

    FormMemberBuilder<S, F, V> dataSource(F memberDataSource);

    FormMemberBuilder<S, F, V> form(Form form);

    FormMemberBuilder<S, F, V> multipleInputTest(
            IsMultipleInput<S, F> multiValueTest);
    
    FormMemberBuilder<S, F, V> formInputContext(
            FormInputContext<S, F, V> fieldValueProvider);
    
    FormMemberBuilder<S, F, V> multiChoiceContext(
            MultiChoiceContext<S, F> multiChoiceContext);

    FormMemberBuilder<S, F, V> referencedFormContext(
            ReferencedFormContext<S, F> referenceFormContext);
}
