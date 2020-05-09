/*
 * Copyright 2019 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bc.web.form;

import java.util.List;
import java.util.Objects;
import com.bc.web.form.functions.FormFieldsCreator;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 4:51:00 PM
 */
public class FormBuilder implements Builder<Form>{

    private FormBean delegate;
    
    private FormFieldsCreator fieldsCreator;
    
    private Comparator<FormField> fieldsComparator;
    
    private Object fieldDataSource;

    public FormBuilder() { 
        delegate = new FormBean();
    }
    
    @Override
    public Form build() {
        
        Objects.requireNonNull(delegate.getFormFields());
        Objects.requireNonNull(delegate.getId());
        Objects.requireNonNull(delegate.getName());
        Objects.requireNonNull(delegate.getDisplayName());
        Objects.requireNonNull(delegate.getDatePatterns());
        Objects.requireNonNull(delegate.getTimePatterns());
        Objects.requireNonNull(delegate.getDatetimePatterns());
        
        if(delegate.getFormFields() == null || delegate.getFormFields().isEmpty()) {

            Objects.requireNonNull(fieldsCreator);
            
            final List<FormField> fieldList = new ArrayList<>(fieldsCreator.apply(delegate, fieldDataSource));
            
            if(fieldsComparator != null) {
                fieldList.sort(fieldsComparator);
            }
            
            delegate.setFormFields(fieldList);
        }
        
        final Form result = new FormBean(delegate);
        
        this.reset();
        
        return result;
    }
    
    @Override
    public FormBuilder reset() {
        delegate = new FormBean();
        return this;
    }
    
    public FormBuilder withDefault(String name) {
        return this.apply(new DefaultForm(name));
    }

    @Override
    public FormBuilder apply(Form form) {
        delegate = new FormBean(form);
        return this;
    }
    
    public FormBuilder fieldDataSource(Object source) {
        this.fieldDataSource = source;
        return this;
    }

    public FormBuilder fieldsCreator(FormFieldsCreator formFieldsCreator) {
        this.fieldsCreator = formFieldsCreator;
        return this;
    }
    
    public FormBuilder fieldsComparator(Comparator<FormField> comparator) {
        this.fieldsComparator = comparator;
        return this;
    }
    
    public FormBuilder id(String id) {
        this.delegate.setId(id);
        return this;
    }

    public FormBuilder name(String name) {
        delegate.setName(name);
        return this;
    }

    public FormBuilder displayName(String displayName) {
        delegate.setDisplayName(displayName);
        return this;
    }

    public FormBuilder formFields(List<FormField> formFields) {
        this.delegate.setFormFields(formFields);
        return this;
    }

    public FormBuilder datePatterns(List<String> patterns) {
        delegate.setDatePatterns(patterns);
        return this;
    }

    public FormBuilder timePatterns(List<String> patterns) {
        delegate.setTimePatterns(patterns); 
        return this;
    }

    public FormBuilder datetimePatterns(List<String> patterns) {
        delegate.setDatetimePatterns(patterns);
        return this;
    }
}
