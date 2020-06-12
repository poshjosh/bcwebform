package com.bc.webform;

import java.util.List;
import java.util.Optional;

/**
 * @(#)Form.java   30-Apr-2015 23:39:23
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */
public interface Form<S> extends Identifiable {
    
    class Builder<S, F, V> extends FormBuilderImpl<S, F, V>{ }
    
    /**
     * @param <F> The parameter type of the data source fields for the returned builder
     * @param fieldType The type of the data source fields for the returned builder
     * @return A new builder with this Form's values applied via the
     * {@link com.bc.webform.FormBuilder#apply(java.lang.Object)} method.
     * @see #builder(java.lang.Class) 
     */
    default <F> FormBuilder<S, F, Object> building(Class<F> fieldType) {
        return builder(fieldType).apply(this);
    }
    
    /**
     * @param <F> The parameter type of the data source fields for the returned builder
     * @param fieldType The type of the data source fields for the returned builder
     * @return A new builder.
     * @see #building(java.lang.Class) 
     */
    default <F> FormBuilder<S, F, Object> builder(Class<F> fieldType) {
        return new FormBuilderImpl();
    }
    
    Form copy();
    
    FormBean writableCopy();

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
    
    Form<S> getParent();

    List<FormMember> getMembers();

    Optional<FormMember> getMemberOptional(String name);
    
    List<String> getMemberNames();
    
    S getDataSource();
}
