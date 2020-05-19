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

package com.bc.webform.functions;

import com.bc.webform.Form;
import com.bc.webform.FormField;
import com.bc.webform.FormFieldBuilderFromSource;
import com.bc.webform.FormFieldBuilderImpl;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 6:15:29 PM
 */
public class CreateFormFieldsFromObject implements FormFieldsCreator<Object, Field>{

    private static final Logger LOG = Logger.getLogger(CreateFormFieldsFromObject.class.getName());

    private final Predicate<Field> isFormField;
    
    private final FormFieldBuilderFromSource formFieldBuilder;
    
    /**
     * How far up the class inheritance hierarchy to check for form fields
     */
    private final int maxLevelOfClassHierarchyToCheckForFields;

    public CreateFormFieldsFromObject() {
        this((field) -> true, -1, new FormFieldBuilderImpl());
    }
    
    public CreateFormFieldsFromObject(
            Predicate<Field> isFormField, 
            int maxLevelOfClassHierarchyToCheckForFields,
            FormFieldBuilderFromSource formFieldBuilder) {
        this.isFormField = Objects.requireNonNull(isFormField);
        this.maxLevelOfClassHierarchyToCheckForFields = maxLevelOfClassHierarchyToCheckForFields;
        this.formFieldBuilder = Objects.requireNonNull(formFieldBuilder);
    }
    
    @Override
    public List<FormField> apply(Form form, Object source) {
        
        final List<FormField> result = new ArrayList<>();
        
        Class objectType = source.getClass();
        
        int depth = 0;
        
        while( ! Object.class.equals(objectType) && 
                this.isWithinMaxLevelOfClassHierarchyToCheckForFields(depth)) {
        
            ++depth;
            
            if(LOG.isLoggable(Level.FINE)) {
                final int d = depth;
                LOG.log(Level.FINE, () -> "Depth: " + d + ", object: " + 
                        source.getClass().getSimpleName() + 
                        ", form: name=" + form.getName() + ", parent name=" + 
                        (form.getParent()==null?null:form.getParent().getName()));            
            }
            
            final Field [] fields = objectType.getDeclaredFields();

            LOG.log(Level.FINE, "Declared fields: {0}", (Arrays.toString(fields)));
            
            final String name = form.getParent() == null ? null : form.getParent().getName();
            
            final Predicate<FormField> formFieldTest = (ff) -> {
                
                final boolean accept = ! ff.getName().equalsIgnoreCase(name);
                final Level level = accept ? Level.FINER : Level.FINE;
                LOG.log(level, () -> "Rejecting form field: " + 
                        source.getClass().getSimpleName() + '.' + ff.getName() +
                        ", because the field's name is same as it's formField.form.parent.name, i.e: " + name);
                return accept;
            };
            
            Arrays.asList(fields).stream()
                    .filter(isFormField)
                    .map((field) -> this.newFormField(form, source, field))
                    .filter(formFieldTest)
                    .forEach((formField) -> result.add(formField));
            
            objectType = objectType.getSuperclass();
        }
        
        return Collections.unmodifiableList(result);
    }
    
    public boolean isWithinMaxLevelOfClassHierarchyToCheckForFields(int level) {
        return (maxLevelOfClassHierarchyToCheckForFields < 0 || 
                level < maxLevelOfClassHierarchyToCheckForFields);
    }
    
    @Override
    public FormField newFormField(Form form, Object source, Field field) {
        
        formFieldBuilder.reset().form(form).source(source).field(field);
        
        return this.buildFormField(form, source, field, formFieldBuilder);
    }

    protected FormField buildFormField(
            Form form, Object source, Field field, 
            FormFieldBuilderFromSource builder) {
        
        final FormField formField = builder.build();
        
        LOG.log(Level.FINER, "{0}", formField);
        return formField;
    }
}
