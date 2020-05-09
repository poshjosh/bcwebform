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

import java.util.Map;
import java.util.Objects;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 2:44:35 PM
 */
public class FormFieldBuilder<V> implements Builder<FormField<V>>{

    private FormFieldBean delegate;
    
    public FormFieldBuilder() { 
        delegate = new FormFieldBean();
    }
    
    @Override
    public FormField<V> build() {
        
        Objects.requireNonNull(delegate.getForm());
        Objects.requireNonNull(delegate.getId());
        Objects.requireNonNull(delegate.getName());
        Objects.requireNonNull(delegate.getLabel());
        
        final FormFieldBean result = new FormFieldBean(delegate);
        
        this.reset();
        
        return result;
    }
    
    @Override
    public FormFieldBuilder reset() {
        delegate = new FormFieldBean();
        return this;
    }
    
    public FormFieldBuilder withDefaults(Form form, String name) {
        return this.apply(new DefaultFormField(form, name));
    }
    
    @Override
    public FormFieldBuilder apply(FormField<V> formField) {
        delegate = new FormFieldBean(formField);
        return this;
    }

    public FormFieldBuilder id(String id) {
        this.delegate.setId(id);
        return this;
    }

    public FormFieldBuilder name(String name) {
        delegate.setName(name);
        return this;
    }

    public FormFieldBuilder label(String label) {
        delegate.setLabel(label);
        return this;
    }

    public FormFieldBuilder advice(String advice) {
        delegate.setAdvice(advice);
        return this;
    }

    public FormFieldBuilder value(V value) {
        delegate.setValue(value);
        return this;
    }

    public FormFieldBuilder choices(Map choices) {
        delegate.setChoices(choices);
        return this;
    }

    public FormFieldBuilder maxLength(int maxLength) {
        delegate.setMaxLength(maxLength);
        return this;
    }

    public FormFieldBuilder size(int size) {
        delegate.setSize(size);
        return this;
    }

    public FormFieldBuilder numberOfLines(int n) {
        delegate.setNumberOfLines(n);
        return this;
    }

    public FormFieldBuilder type(String type) {
        delegate.setType(type);
        return this;
    }
    
    public FormFieldBuilder form(Form form) {
        delegate.setForm(form);
        return this;
    }

    public FormFieldBuilder referencedForm(Form referencedForm) {
        delegate.setReferencedForm(referencedForm);
        return this;
    }
    
    public FormFieldBuilder optional(boolean optional) {
        delegate.setOptional(optional);
        return this;
    }

    public FormFieldBuilder required(boolean required) {
        delegate.setRequired(required);
        return this;
    }

    public FormFieldBuilder multiChoice(boolean multiChoice) {
        delegate.setMultiChoice(multiChoice);
        return this;
    }

    public FormFieldBuilder multiValue(boolean multiValue) {
        delegate.setMultiValue(multiValue);
        return this;
    }
}
