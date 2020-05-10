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

import com.bc.reflection.ReflectionUtil;
import com.bc.webform.Form;
import com.bc.webform.FormField;
import com.bc.webform.FormFieldBuilder;
import com.bc.webform.StandardFormFieldTypes;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 6:15:29 PM
 */
public class CreateFormFieldsFromObject implements FormFieldsCreator<Object, Field>{

    private static final Logger LOG = Logger.getLogger(CreateFormFieldsFromObject.class.getName());

    private final Predicate<Field> isFormField;
    
    private final int maxDepth;
    
    private transient volatile Object object;
    
    private transient volatile Method [] methods;
    
    private final Predicate<Field> isContainerField;

    public CreateFormFieldsFromObject() {
        this((field) -> true, -1);
    }
    
    public CreateFormFieldsFromObject(Predicate<Field> isFormField, int maxDepth) {
        this.isFormField = Objects.requireNonNull(isFormField);
        this.maxDepth = maxDepth;
        this.isContainerField = new IsContainerField();
    }
    
    @Override
    public List<FormField> apply(Form form, Object object) {
        
        this.object = object;
        
        final List<FormField> result = new ArrayList<>();
        
        Class objectType = object.getClass();
        
        int depth = 0;
        
        while( ! Object.class.equals(objectType) && (maxDepth < 0 || depth < maxDepth)) {
        
            ++depth;
            
            final Field [] fields = objectType.getDeclaredFields();

            LOG.log(Level.FINE, "Declared fields: {0}", (Arrays.toString(fields)));

            Arrays.asList(fields).stream()
                    .filter(isFormField)
                    .map((field) -> this.newFormField(form, object, field))
                    .forEach((formField) -> result.add(formField));
            
            objectType = objectType.getSuperclass();
        }
        
        return Collections.unmodifiableList(result);
    }
    
    @Override
    public FormField newFormField(Form form, Object obj, Field field) {
        
        final int maxLen = this.getMaxLength(form, object, field);
        final int lineMaxLen = getLineMaxLength(form, object, field);
        final int numberOfLines = maxLen <= lineMaxLen ? 1 : maxLen / lineMaxLen;
        LOG.log(Level.FINER, () -> "MaxLen: " + maxLen + 
                ", lineMaxLen: " + lineMaxLen + ", numOfLines: " + numberOfLines);
        
        final FormFieldBuilder builder = new FormField.Builder()
                .withDefaults(form, field.getName())
                .choices(this.getChoices(form, object, field))
                .value(this.getValue(form, object, field))
                .maxLength(maxLen)
                .numberOfLines(numberOfLines)
                .optional(this.isOptional(form, object, field))
                .multiChoice(this.isMultiChoice(form, object, field))
                .multiValue(this.isMultiValue(form, object, field))
                .referencedForm(this.getReferencedForm(form, object, field))
                .type(this.getType(form, object, field));
                    
        return this.buildFormField(form, object, field, builder);
    }
    
    public int getLineMaxLength(Form form, Object object, Field field) {
        return 128;
    }
    
    public Form getReferencedForm(Form form, Object object, Field field) {
        return null;
    }

    public boolean isMultiChoice(Form form, Object object, Field field) {
        return false;
    }

    public boolean isMultiValue(Form form, Object object, Field field) {
        return this.isContainerField.test(field);
    }

    public Map getChoices(Form form, Object object, Field field) {
        return Collections.EMPTY_MAP;
    }

    public Object getValue(Form form, Object object, Field field) {
        Object value = this.getValueFromField(form, object, field);
        if(value == null) {
            value = this.getValueFromMethod(form, object, field);
        }
        return value;
    }

    public Object getValueFromField(Form form, Object object, Field field) {
        Object fieldValue = null;
        if(object != null) {
            final boolean flag = field.isAccessible();
            try{
                if( ! flag) {
                    field.setAccessible(true);
                }
                fieldValue = field.get(object);
                if(LOG.isLoggable(Level.FINER)) {
                    LOG.log(Level.FINER, "Retreived value: {0}, from field: {1}", new Object[]{fieldValue, field});
                }
            }catch(IllegalArgumentException | IllegalAccessException e) {
                LOG.log(Level.WARNING, "Failed to access value for field: " + field, e);
            }finally{
                if( ! flag) {
                    field.setAccessible(flag);
                }
            }
        }
        return fieldValue;
    }
    
    public Object getValueFromMethod(Form form, Object object, Field field) {
        Object methodValue = null;
        if(object != null) {
            final Class objectType = object.getClass();
            if(methods == null) {
                methods = objectType.getDeclaredMethods();
            }
            if(methods != null && methods.length > 0) {
                try{
                    methodValue = new ReflectionUtil().getValue(objectType, object, methods, field.getName());
                }catch(RuntimeException ignored) { }
            }
            if(LOG.isLoggable(Level.FINER)) {
                LOG.log(Level.FINER, "Retreived from associated method, value: {0}, field: {1}", new Object[]{methodValue, field});
            }
        }
        return methodValue;
    }

    public int getMaxLength(Form form, Object object, Field field) {
        return -1;
    }
    
    public boolean isOptional(Form form, Object object, Field field) {
        return false;
    }

    public String getType(Form form, Object object, Field field) {
        final String type;
        if(new IsPasswordField().test(field.getName())) {
            type = StandardFormFieldTypes.PASSWORD; 
        }else{
            type = this.getFieldTypeFunctor(form, object, field).apply(field);
        }
        return type;
    }    

    public Function<Field, String> getFieldTypeFunctor(Form form, Object object, Field field) {
        return new GetFormFieldTypeForField(StandardFormFieldTypes.TEXT);
    }

    protected FormField buildFormField(Form form, Object object, Field field, FormFieldBuilder builder) {
        final FormField formField = builder.build();
        LOG.log(Level.FINER, "{0}", formField);
        return formField;
    }
}
