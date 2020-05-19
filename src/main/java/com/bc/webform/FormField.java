package com.bc.webform;

import java.util.Map;


/**
 * @(#)FormField.java   30-Apr-2015 19:08:04
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */
public interface FormField<VALUE_TYPE> 
        extends Identifiable, StandardFormFieldTypes {
    
    class Builder<VALUE_TYPE> extends FormFieldBuilderImpl<VALUE_TYPE>{ }

    /**
     * @return A new builder with this Form's values applied via the
     * {@link com.bc.webform.Builder#apply(java.lang.Object)} method.
     * @see #builder() 
     */
    default FormFieldBuilderImpl building() {
        return builder().apply(this);
    }
    
    /**
     * @return A new builder.
     * @see #building() 
     */
    default FormFieldBuilderImpl builder() {
        return new FormFieldBuilderImpl();
    }
    
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
    
    FormField<VALUE_TYPE> withValue(VALUE_TYPE value);
    
    String getAdvice();

    VALUE_TYPE getValue();

    /**
     * Choices are represented by &lt;select&gt; HTML element
     * @return Map of the choices, usually id=display_value mappings.
     */
    Map getChoices();

    int getMaxLength();
        
    int getSize();
    
    int getNumberOfLines();

    /**
     * @return The input type. E.g <code>date,datetime-local,file,hidden,number,password,text</code> amongst others
     */
    String getType();

    /**
     * @return The form which contains this form field
     */
    Form getForm();
    
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
    String getReferencedFormHref();

    /**
     * If a form represents a <code>Person</code> and one of the fields of the 
     * form is <code>primaryAddress</code>, it is possible for this field
     * to refer to a form also and the <code>primaryAddress</code> form is the
     * referenced form.
     * 
     * Use this method to display the referenced form in-line. However, it is
     * recommended to display the referenced form in a different process via
     * {@link #getReferencedFormHref()}. 
     * 
     * @return The form which encapsulates this form field or <code>null</code>
     * @see #isFormReference() 
     * @see #getReferencedFormHref() 
     */
    Form getReferencedForm();

    boolean isOptional();
    
    boolean isRequired();

    boolean isMultiChoice();
    
    boolean isMultiValue();

    /**
     * @return <code>true</code> if this field refers to a form with one/more other fields 
     * @see #getReferencedForm() 
     */
    boolean isFormReference();
}
