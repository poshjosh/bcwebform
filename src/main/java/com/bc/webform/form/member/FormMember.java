package com.bc.webform.form.member;

import com.bc.webform.form.member.builder.FormMemberBuilder;
import com.bc.webform.form.member.builder.FormMemberBuilderImpl;
import com.bc.webform.Identifiable;
import com.bc.webform.choices.SelectOption;
import com.bc.webform.form.Form;
import java.util.List;


/**
 * @(#)FormField.java   30-Apr-2015 19:08:04
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */
public interface FormMember<F, V> extends Identifiable {
    
    class Builder<S, F, V> extends FormMemberBuilderImpl<S, F, V>{ }

    /**
     * @return A new builder.
     * @see #builder(java.lang.Class)  
     */
    default FormMemberBuilder<Object, F, V> builder() {
        return this.builder(Object.class);
    }
    
    /**
     * @param <S> The parameter type of the data source for the returned builder
     * @param sourceType The type of the data source for the returned builder
     * @return A new builder.
     * @see #building() 
     */
    default <S> FormMemberBuilder<S, F, V> builder(Class<S> sourceType) {
        return new FormMemberBuilderImpl<>();
    }
    
    FormMember<F, V> copy();
    
    FormMemberBean<F, V> writableCopy();

    // We override this here because some templating engines cannot 
    // access it from the super type
    /**
     * Alias for {@link #getLabel() }
     * @return The display name
     * @see #getLabel() 
     */
    @Override
    public default String getDisplayName() {
        return Identifiable.super.getDisplayName();
    }

    @Override
    public String getLabel();

    @Override
    public String getName();

    @Override
    public String getId();
    
    FormMember<F, V> withValue(V value);
    
    String getAdvice();

    V getValue();

    /**
     * Choices are represented by &lt;select&gt; HTML element
     * @return Map of the choices, usually id=display_value mappings.
     */
    List<SelectOption> getChoices();

    int getMaxLength();
        
    int getSize();
    
    int getNumberOfLines();
    
    /**
     * The data type, different from {@link #getType()}. 
     * 
     * May refer to a specific object type not covered by HTML specs.
     * 
     * @return The data type
     */
    String getDataType();

    /**
     * @return The input type. E.g <code>date,datetime-local,file,hidden,number,password,text</code> amongst others
     */
    String getType();

    /**
     * @return The form which contains this form dataSource
     */
    Form getForm();
    
    Boolean isDisabled();

    Boolean isOptional();
    
    Boolean isRequired();

    Boolean isMultiChoice();
    
    /**
     * Specifies whether the user is allowed to enter more than one value.
     * 
     * @return <code>true</code> if the user is allowed more than one value
     * otherwise returns <code>false</code>
     * @see https://www.w3schools.com/tags/att_input_multiple.asp
     */
    Boolean isMultiple();

    /**
     * If a form represents a <code>Person</code> and one of the fields of the 
     * form is <code>primaryAddress</code>, it is possible for this field
     * to refer to a form also and the <code>primaryAddress</code> form is the
     * referenced form.
     * 
     * Use this method to display the referenced form in the browser. When the
     * reference form completes, it should return to the form which led to it
     * in the first place.
     * 
     * @return A link to the form which encapsulates this form field or <code>null</code>
     * @see #isFormReference() 
     * @see #getReferencedForm() 
     */
    Boolean isFormReference();
    
    F getDataSource();
}
