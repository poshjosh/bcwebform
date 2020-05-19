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

import com.bc.webform.exceptions.ValuesOverwriteByDefaultException;
import java.util.List;
import java.util.Objects;
import com.bc.webform.functions.FormFieldsCreator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 4:51:00 PM
 */
public class FormBuilder implements Builder<Form>{

    private static final Logger LOG = Logger.getLogger(FormBuilder.class.getName());
    
    private FormBean delegate;
    
    private FormFieldsCreator fieldsCreator;
    
    private Comparator<FormField> fieldsComparator;
    
    private Object fieldDataSource;

    public FormBuilder() { 
        delegate = new FormBean();
    }
    
    @Override
    public FormBuilder reset() {
        delegate = new FormBean();
        return this;
    }
    
    @Override
    public Form build() {
        
        try{
            
            delegate.checkRequiredFieldsAreSet();

            if(delegate.getFormFields() == null || delegate.getFormFields().isEmpty()) {

                Objects.requireNonNull(fieldsCreator);

                LOG.log(Level.FINE, () -> "Field data source: " + 
                        (fieldDataSource == null ? null : fieldDataSource.getClass().getSimpleName()) + 
                        ", form: name=" + delegate.getName() + ", parent name=" + 
                        (delegate.getParent()==null?null:delegate.getParent().getName()));            

                final List<FormField> fieldList = new ArrayList<>(fieldsCreator.apply(delegate, fieldDataSource));

                if(fieldsComparator != null) {
                    fieldList.sort(fieldsComparator);
                }

                delegate.setFormFields(fieldList);
            }

            this.building(delegate);
            
            // Always return a copy to shield us from any, after the fact, 
            // changes to the original
            //
            final Form result = delegate.copy();

            return result;

        }finally{
            
            this.reset();
        }
    }
    
    protected FormBean building(FormBean builder) {
        return builder;
    }
    
    /**
     * Apply default values.
     * 
     * The default values are gotten by building a {@link com.bc.webform.DefaultForm DefaultForm}
     * for the supplied name.
     * 
     * <p><b>Note:</b></p>
     * This method should be the first method called. Calling the method 
     * after any value has been set will lead to IllegalStateException 
     * @param name The name of the {@link com.bc.webform.DefaultForm DefaultForm} 
     * to build and whose values will be used to update the current build.
     * @throws java.lang.IllegalStateException
     * @return this object
     */
    public FormBuilder applyDefaults(String name) throws IllegalStateException{
        if(delegate.isAnyFieldSet()) {
            throw new ValuesOverwriteByDefaultException(delegate);
        }
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

    public FormBuilder parent(Form form) {
        this.delegate.setParent(form);
        return this;
    }
}
/**
 * 

    public FormBuilder parent(Form form) {
        this.delegate.setParent(form);
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
        delegate.setLabel(displayName);
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
 * 
 */



