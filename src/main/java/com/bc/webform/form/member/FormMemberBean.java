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

package com.bc.webform.form.member;

import com.bc.webform.IdentifiableFieldSet;
import com.bc.webform.WebformUtil;
import com.bc.webform.choices.SelectOptionBean;
import com.bc.webform.form.Form;
import com.bc.webform.form.FormBean;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 10:53:17 AM
 */
public class FormMemberBean<F, V> implements IdentifiableFieldSet, FormMember<F, V>, Serializable {
    
    private String id;
    private String name;
    private String label;
    private String advice;
    private V value;
    private List<SelectOptionBean> choices;
    private int maxLength = -1;
    private int size = -1;
    private int numberOfLines = -1;
    private String type;
    private String dataType;
    private FormBean form;
    private Boolean formReference;
    private Boolean readOnly;
    private Boolean readOnlyValue;
    private Boolean optional;
    private Boolean multiChoice;
    private Boolean multiple;
    private F dataSource;
    
    public FormMemberBean() { }

    public FormMemberBean(FormMember<F, V> f) {
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
        this.dataType = f.getDataType();
        this.form = WebformUtil.toBean(f.getForm());
        this.formReference = f.isFormReference();
        this.readOnly = f.isReadOnly();
        this.readOnlyValue = f.isReadOnlyValue();
        this.optional = f.isOptional();
        this.multiChoice = f.isMultiChoice();
        this.multiple = f.isMultiple();
        this.dataSource = f.getDataSource();
    }
    
    @Override
    public boolean isAnyFieldSet() {
        return this.id != null || this.name != null ||
                this.label != null || this.advice != null ||
                this.value != null || 
                this.choices != null ||
                this.maxLength != -1 || this.size != -1 ||
                this.numberOfLines != -1 || this.type != null ||
                this.dataType != null ||
                this.form != null || this.formReference != null ||
                this.readOnly != null || this.readOnlyValue != null ||
                this.optional != null || this.multiChoice != null || 
                this.multiple != null || this.dataSource != null;
    }
    
    @Override
    public void checkRequiredFieldsAreSet() {
        Objects.requireNonNull(this.getForm());
        Objects.requireNonNull(this.getId());
        Objects.requireNonNull(this.getName());
        Objects.requireNonNull(this.getLabel());
        Objects.requireNonNull(this.getDataSource());
    }
    
    @Override
    public FormMemberBean<F, V> copy() {
        return new FormMemberBean(this);
    }

    @Override
    public FormMemberBean<F, V> withValue(V value) {
        return this.copy().value(value);
    }
    
    ////////////////////////////////////////////
    // builder methods
    ///////////////////////////////////////////
    
    public FormMemberBean<F, V> id(String id) {
        this.setId(id);
        return this;
    }

    public FormMemberBean<F, V> name(String name) {
        this.setName(name);
        return this;
    }

    public FormMemberBean<F, V> label(String label) {
        this.setLabel(label);
        return this;
    }

    public FormMemberBean<F, V> advice(String advice) {
        this.setAdvice(advice);
        return this;
    }

    public FormMemberBean<F, V> value(V value) {
        this.setValue(value);
        return this;
    }

    public FormMemberBean<F, V> choices(List<SelectOptionBean> choices) {
        this.setChoices(choices);
        return this;
    }

    public FormMemberBean<F, V> maxLength(int maxLength) {
        this.setMaxLength(maxLength);
        return this;
    }

    public FormMemberBean<F, V> size(int size) {
        this.setSize(size);
        return this;
    }

    public FormMemberBean<F, V> numberOfLines(int n) {
        this.setNumberOfLines(n);
        return this;
    }

    public FormMemberBean<F, V> type(String type) {
        this.setType(type);
        return this;
    }
    
    public FormMemberBean<F, V> dataType(String dataType) {
        this.setDataType(dataType);
        return this;
    }

    public FormMemberBean<F, V> form(FormBean form) {
        this.setForm(form);
        return this;
    }

    public FormMemberBean<F, V> disabled(Boolean disabled) {
        this.setReadOnly(disabled);
        return this;
    }

    public FormMemberBean<F, V> optional(boolean optional) {
        this.setOptional(optional);
        return this;
    }

    public FormMemberBean<F, V> required(boolean required) {
        this.setRequired(required);
        return this;
    }

    public FormMemberBean<F, V> multiChoice(boolean multiChoice) {
        this.setMultiChoice(multiChoice);
        return this;
    }

    public FormMemberBean<F, V> multiple(boolean multiple) {
        this.setMultiple(multiple);
        return this;
    }

    public FormMemberBean<F, V> dataSource(F dataSource) {
        this.setDataSource(dataSource);
        return this;
    }

    ////////////////////////////////////////////
    // getters and setters
    ////////////////////////////////////////////

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
     * Choices are represented by &lt;select&gt; HTML element
     * @return Map of the choices, usually id=display_value mappings.
     */
    @Override
    public List<SelectOptionBean> getChoices() {
        return choices;
    }

    public void setChoices(List<SelectOptionBean> choices) {
        this.choices = choices;
    }

    @Override
    public int getMaxLength() {
        return maxLength == -1 ? 0 : maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int getSize() {
        return size == -1 ? 0 : size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getNumberOfLines() {
        return numberOfLines == -1 ? 0 : numberOfLines;
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
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public FormBean getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = WebformUtil.toBean(form);
    }
    
    @Override
    public Boolean isReadOnly() {
        return readOnly == null ? Boolean.FALSE : readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public Boolean isReadOnlyValue() {
        return readOnlyValue;
    }

    public void setReadOnlyValue(Boolean readOnlyValue) {
        this.readOnlyValue = readOnlyValue;
    }

    @Override
    public Boolean isOptional() {
        return optional == null ? Boolean.TRUE : optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    @Override
    public Boolean isRequired() {
        return ! this.isOptional();
    }

    public void setRequired(Boolean required) {
        this.setOptional( ! required);
    }

    @Override
    public Boolean isMultiple() {
        return multiple == null ? Boolean.FALSE : multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    @Override
    public Boolean isMultiChoice() {
        return multiChoice == null ? Boolean.FALSE : multiChoice;
    }

    public void setMultiChoice(Boolean multiChoice) {
        this.multiChoice = multiChoice;
    }
    
    public FormMemberBean<F, V> formReference(Boolean arg) {
        this.setFormReference(arg);
        return this;
    }

    /**
     * Does this form member refer to a Form?
     * If a form represents a <code>Person</code> and one of the fields of the 
     * form is <code>primaryAddress</code>, it is possible for this field
     * to refer to a form also and the <code>primaryAddress</code> form is the
     * referenced form.
     * 
     * @return REturns {@code true} if this form member refers to a Form, 
     * otherwise returns {@code false}.
     */
    @Override
    public Boolean isFormReference() {
        return this.formReference;
    }

    public void setFormReference(Boolean formReference) {
        this.formReference = formReference;
    }

    @Override
    public F getDataSource() {
        return dataSource;
    }

    public void setDataSource(F dataSource) {
        this.dataSource = dataSource;
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
        final FormMemberBean other = (FormMemberBean) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FormMemberBean{" + "name=" + name + ", value=" + value + 
                ", choices=" + (choices==null?null:choices.size()) + 
                ", advice=" + advice + ", maxLength=" + maxLength + ", size=" + size + 
                ", numberOfLines=" + numberOfLines + ", type=" + type + ", dataType=" + dataType +
                ", readOnly=" + readOnly + ", readOnlyValue=" + readOnlyValue +
                ", optional=" + optional + ", multiChoice=" + multiChoice + 
                ", multiple=" + multiple + ", form=" + (form==null?null:form.getName()) +
                ", dataSource=" + dataSource +
                ", formReference=" + formReference + '}';
    }
}
