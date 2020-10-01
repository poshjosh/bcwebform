package com.bc.webform.form;

import com.bc.webform.form.builder.FormBuilder;
import com.bc.webform.form.builder.FormBuilderImpl;
import com.bc.webform.form.member.FormMember;
import com.bc.webform.Identifiable;
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
     * @return A new builder.
     * @see #building(java.lang.Class) 
     */
    default <F> FormBuilder<S, F, Object> builder(Class<F> fieldType) {
        return new FormBuilderImpl();
    }
    
    Form copy();

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

    <M extends FormMember> List<M> getMembers();

    <M extends FormMember> Optional<M> getMemberOptional(String name);
    
    List<String> getMemberNames();
    
    S getDataSource();
}
