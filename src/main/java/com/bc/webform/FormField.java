package com.bc.webform;

import java.util.Map;


/**
 * @(#)FormField.java   30-Apr-2015 19:08:04
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */
public interface FormField<VALUE_TYPE> extends StandardFormFieldTypes {
    
    class Builder<VALUE_TYPE> extends FormFieldBuilder<VALUE_TYPE>{ }
    
    FormField<VALUE_TYPE> withValue(VALUE_TYPE value);
    
    String getId();

    String getName();

    String getLabel();
    
    String getAdvice();

    VALUE_TYPE getValue();

    /**
     * Choices are represented by &lt;select&gt; HTML tag
     * @return A copy of the choices.
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
     * form is <code>primaryAddress</code>. It is possible for this field
     * to refer to a form also. The <code>primaryAddress</code> form is the
     * referenced form.
     * @return The form which encapsulates this form field. 
     * @see #isFormReference() 
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
