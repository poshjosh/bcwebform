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
public interface Form extends Identifiable {
    
    class Builder extends FormBuilderImpl{}
    
    /**
     * @return A new builder with this Form's values applied via the
     * {@link com.bc.webform.Builder#apply(java.lang.Object)} method.
     * @see #builder() 
     */
    default FormBuilder building() {
        return builder().apply(this);
    }
    
    /**
     * @return A new builder.
     * @see #building() 
     */
    default FormBuilder builder() {
        return new FormBuilderImpl();
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
    
    Form getParent();

    List<FormMember> getMembers();

    Optional<FormMember> getMember(String name);
    
    List<String> getMemberNames();
    
    List<String> getRequiredMemberNames();
    
    List<String> getOptionalMemberNames();
    
    List<String> getFileTypeMemberNames();

    List<String> getDatePatterns();

    List<String> getTimePatterns();

    List<String> getDatetimePatterns();
}
