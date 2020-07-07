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
import com.bc.webform.form.Form;
import java.io.Serializable;
import java.util.Map;
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
    private Map choices;
    private int maxLength;
    private int size;
    private int numberOfLines;
    private String type;
    private Form form;
    private String referencedFormHref;
    private Form referencedForm;
    private boolean optional;
    private boolean multiChoice;
    private boolean multiple;
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
        this.form = f.getForm();
        this.referencedFormHref = f.getReferencedFormHref();
        this.referencedForm = f.getReferencedForm();
        this.optional = f.isOptional();
        this.multiChoice = f.isMultiChoice();
        this.multiple = f.isMultiple();
        this.dataSource = f.getDataSource();
    }

    // We override this here because some templating engines cannot 
    // access it from the super type
    /**
     * Alias for {@link #getLabel() }
     * @return The display name
     * @see #getLabel() 
     */
    @Override
    public String getDisplayName() {
        return FormMember.super.getDisplayName();
    }
    
    @Override
    public boolean isAnyFieldSet() {
        return this.getId() != null || this.getName() != null ||
                this.getLabel() != null || this.getAdvice() != null ||
                this.getValue() != null ||this.getChoices() != null ||
                this.getMaxLength() != 0 || this.getSize() != 0 ||
                this.getNumberOfLines() != 0 || this.getType() != null ||
                this.getForm() != null || this.getReferencedFormHref() != null ||
                this.getReferencedForm() != null || this.isOptional() || 
                this.isMultiChoice() || this.isMultiple() ||
                this.getDataSource() != null;
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
        return this.writableCopy();
    }
    
    @Override
    public FormMemberBean<F, V> writableCopy() {
        return new FormMemberBean(this);
    }

    @Override
    public FormMember<F, V> withValue(V value) {
        return this.writableCopy().value(value);
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

    public FormMemberBean<F, V> choices(Map choices) {
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
    
    public FormMemberBean<F, V> form(Form form) {
        this.setForm(form);
        return this;
    }

    public FormMemberBean<F, V> referencedFormHref(String referencedFormHref) {
        this.setReferencedFormHref(referencedFormHref);
        return this;
    }
    
    public FormMemberBean<F, V> referencedForm(Form referencedForm) {
        this.setReferencedForm(referencedForm);
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
    public Map getChoices() {
        return choices;
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
    @Override
    public String getReferencedFormHref() {
        return referencedFormHref;
    }

    public void setReferencedFormHref(String referencedFormHref) {
        this.referencedFormHref = referencedFormHref;
    }

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
    @Override
    public Form getReferencedForm(){
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
    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
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

//    @Override
//    public String toString() {
//        return "FormMember{" + name + '=' + value + '}';
//    }

    @Override
    public String toString() {
        return "FormField{" + "name=" + name + ", advice=" + advice + 
                ", value=" + value + ", choices=" + (choices==null?null:choices.size()) + 
                ", maxLength=" + maxLength + ", size=" + size + 
                ", numberOfLines=" + numberOfLines + ", type=" + type + 
                ", optional=" + optional + ", multiChoice=" + multiChoice + 
                ", multiValue=" + multiple + ", form=" + (form==null?null:form.getName()) +
                ", dataSource=" + dataSource +
                ", referencedFormHref=" + referencedFormHref + 
                ", referencedForm=" + (referencedForm == null ? null : referencedForm.getName()) + '}';
    }
}
