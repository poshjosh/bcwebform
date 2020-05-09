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
public interface Form {
    
    class Builder extends FormBuilder{}

    String getId();
    
    String getName();
    
    String getDisplayName();

    List<FormField> getFormFields();

    Optional<FormField> getFormField(String name);
    
    List<String> getFieldNames();
    
    List<String> getRequiredFieldNames();
    
    List<String> getOptionalFieldNames();
    
    List<String> getFileFieldNames();

    List<String> getDatePatterns();

    List<String> getTimePatterns();

    List<String> getDatetimePatterns();
}
