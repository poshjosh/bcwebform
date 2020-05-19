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

package deleteme;

import com.bc.webform.FormFieldBean;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 2:44:35 PM
 */
public class FormFieldBuilderBase<V>{ //implements Builder<FormField<V>>{

    private FormFieldBean delegate;
    
    public FormFieldBuilderBase() { 
        delegate = new FormFieldBean();
    }
}
/**
 * 

    public FormFieldBuilderBase id(String id) {
        this.delegate.setId(id);
        return this;
    }

    public FormFieldBuilderBase name(String name) {
        delegate.setName(name);
        return this;
    }

    public FormFieldBuilderBase label(String label) {
        delegate.setLabel(label);
        return this;
    }

    public FormFieldBuilderBase advice(String advice) {
        delegate.setAdvice(advice);
        return this;
    }

    public FormFieldBuilderBase value(V value) {
        delegate.setValue(value);
        return this;
    }

    public FormFieldBuilderBase choices(Map choices) {
        delegate.setChoices(choices);
        return this;
    }

    public FormFieldBuilderBase maxLength(int maxLength) {
        delegate.setMaxLength(maxLength);
        return this;
    }

    public FormFieldBuilderBase size(int size) {
        delegate.setSize(size);
        return this;
    }

    public FormFieldBuilderBase numberOfLines(int n) {
        delegate.setNumberOfLines(n);
        return this;
    }

    public FormFieldBuilderBase type(String type) {
        delegate.setType(type);
        return this;
    }
    
    public FormFieldBuilderBase form(Form form) {
        delegate.setForm(form);
        return this;
    }

    public FormFieldBuilderBase referencedFormHref(String referencedFormHref) {
        delegate.setReferencedFormHref(referencedFormHref);
        return this;
    }
    
    public FormFieldBuilderBase referencedForm(Form referencedForm) {
        delegate.setReferencedForm(referencedForm);
        return this;
    }
    
    public FormFieldBuilderBase optional(boolean optional) {
        delegate.setOptional(optional);
        return this;
    }

    public FormFieldBuilderBase required(boolean required) {
        delegate.setRequired(required);
        return this;
    }

    public FormFieldBuilderBase multiChoice(boolean multiChoice) {
        delegate.setMultiChoice(multiChoice);
        return this;
    }

    public FormFieldBuilderBase multiValue(boolean multiValue) {
        delegate.setMultiValue(multiValue);
        return this;
    }


    public String getDisplayName() {
        return delegate.getDisplayName();
    }

    public boolean isAnyFieldSet() {
        return delegate.isAnyFieldSet();
    }

    public void checkRequiredFieldsAreSet() {
        delegate.checkRequiredFieldsAreSet();
    }

    public FormFieldBean copy() {
        return delegate.copy();
    }

    public String getId() {
        return delegate.getId();
    }

    public String getName() {
        return delegate.getName();
    }

    public String getLabel() {
        return delegate.getLabel();
    }

    public String getAdvice() {
        return delegate.getAdvice();
    }

    public Object getValue() {
        return delegate.getValue();
    }

    public Map getChoices() {
        return delegate.getChoices();
    }

    public int getMaxLength() {
        return delegate.getMaxLength();
    }

    public int getSize() {
        return delegate.getSize();
    }

    public int getNumberOfLines() {
        return delegate.getNumberOfLines();
    }

    public String getType() {
        return delegate.getType();
    }

    public Form getForm() {
        return delegate.getForm();
    }

    public String getReferencedFormHref() {
        return delegate.getReferencedFormHref();
    }

    public Form getReferencedForm() {
        return delegate.getReferencedForm();
    }

    public boolean isOptional() {
        return delegate.isOptional();
    }

    public boolean isRequired() {
        return delegate.isRequired();
    }

    public boolean isMultiValue() {
        return delegate.isMultiValue();
    }

    public boolean isMultiChoice() {
        return delegate.isMultiChoice();
    }

    public boolean isFormReference() {
        return delegate.isFormReference();
    }
 * 
 */