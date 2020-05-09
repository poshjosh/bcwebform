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

package com.bc.webform;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 10:53:17 AM
 */
public class FormFieldBean<V> implements FormField<V>, Serializable {
    
    private String id;
    private String name;
    private String label;
    private String advice;
    private V value;
    private Map choices;
    private int maxLength;
    private int size;
    private int numberOfLines;
    private String type;
    private Form form;
    private Form referencedForm;
    private boolean optional;
    private boolean multiChoice;
    private boolean multiValue;

    public FormFieldBean() { }

    public FormFieldBean(FormField<V> f) {
        this.id = f.getId();
        this.name = f.getName();
        this.label = f.getLabel();
        this.advice = f.getAdvice();
        this.value = f.getValue();
        this.choices = f.getChoices();
        this.maxLength = f.getMaxLength();
        this.size = f.getSize();
        this.numberOfLines = f.getNumberOfLines();
        this.type = f.getType();
        this.form = f.getForm();
        this.referencedForm = f.getReferencedForm();
        this.optional = f.isOptional();
        this.multiChoice = f.isMultiChoice();
        this.multiValue = f.isMultiValue();
    }

    public FormFieldBean copy() {
        return new FormFieldBean(this);
    }
    
    @Override
    public FormField<V> withValue(V value) {
        final FormFieldBean<V> copy = this.copy();
        copy.setValue(value);
        return copy;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @Override
    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Choices are represented by &lt;select&gt; HTML tag
     * @return A copy of the choices.
     */
    @Override
    public Map getChoices() {
        return new HashMap(choices);
    }

    public void setChoices(Map choices) {
        this.choices = choices;
    }

    @Override
    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    @Override
    public Form getReferencedForm() {
        return referencedForm;
    }

    public void setReferencedForm(Form referencedForm) {
        this.referencedForm = referencedForm;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public boolean isRequired() {
        return ! optional;
    }

    public void setRequired(boolean required) {
        this.optional = ! required;
    }

    @Override
    public boolean isMultiValue() {
        return multiValue;
    }

    public void setMultiValue(boolean multiValue) {
        this.multiValue = multiValue;
    }

    @Override
    public boolean isMultiChoice() {
        return multiChoice;
    }

    public void setMultiChoice(boolean multiChoice) {
        this.multiChoice = multiChoice;
    }

    @Override
    public boolean isFormReference() {
        return this.referencedForm != null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FormFieldBean<?> other = (FormFieldBean<?>) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "FormField{" + name + '=' + value + '}';
//    }

    @Override
    public String toString() {
        return "FormField{" + "name=" + name + ", advice=" + advice + 
                ", value=" + value + ", choices=" + (choices==null?null:choices.size()) + 
                ", maxLength=" + maxLength + ", size=" + size + 
                ", numberOfLines=" + numberOfLines + ", type=" + type + 
                ", optional=" + optional + ", multiChoice=" + multiChoice + ", multiValue=" + multiValue + '}';
    }
}
