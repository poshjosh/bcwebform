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

package com.bc.web.form.functions;

import com.bc.reflection.ReflectionUtil;
import com.bc.web.form.DefaultFormField;
import com.bc.web.form.Form;
import com.bc.web.form.FormField;
import com.bc.web.form.FormFieldBuilder;
import com.bc.web.form.StandardFormFieldTypes;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    public CreateFormFieldsFromObject() {
        this((field) -> true, -1);
    }
    
    public CreateFormFieldsFromObject(Predicate<Field> isFormField, int maxDepth) {
        this.isFormField = Objects.requireNonNull(isFormField);
        this.maxDepth = maxDepth;
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

            LOG.log(Level.FINE, "Declared fields: {0}", fields.length);

            Arrays.asList(fields).stream()
                    .filter(isFormField)
                    .map((field) -> this.newFormField(form, field))
                    .forEach((formField) -> result.add(formField));
            
            objectType = objectType.getSuperclass();
        }
        
        return Collections.unmodifiableList(result);
    }
    
    @Override
    public FormField newFormField(Form form, Field field) {
        
        final int maxLen = this.getMaxLength(form, field);
        final int lineMaxLen = getLineMaxLength(form, field);
        final int numberOfLines = maxLen <= lineMaxLen ? 1 : maxLen / lineMaxLen;
        
        final FormFieldBuilder builder = new FormField.Builder()
                .apply(new DefaultFormField(form, field.getName()))
                .choices(this.getChoices(form, field))
                .value(this.getValue(form, field))
                .maxLength(maxLen)
                .numberOfLines(numberOfLines)
                .optional(this.isOptional(form, field))
                .multiChoice(this.isMultiChoice(form, field))
                .multiValue(this.isMultiValue(form, field))
                .referencedForm(this.getReferencedForm(form, field))
                .type(this.getType(form, field));
                    
        return this.buildFormField(form, field, builder);
    }
    
    public int getLineMaxLength(Form form, Field field) {
        return 256;
    }
    
    public Form getReferencedForm(Form form, Field field) {
        return null;
    }

    public boolean isMultiChoice(Form form, Field field) {
        return false;
    }

    public boolean isMultiValue(Form form, Field field) {
        final Class type = field.getType();
        final boolean output = Collection.class.isAssignableFrom(type) || 
                Map.class.isAssignableFrom(type) ||
                Object[].class.isAssignableFrom(type);
        LOG.finer(() -> "Multivalue: " + output + ", field type: " + type + ", field: " + field);
        return output;
    }

    public Map getChoices(Form form, Field field) {
        return Collections.EMPTY_MAP;
    }

    public Object getValue(Form form, Field field) {
        Object value = this.getValueFromField(form, field);
        if(value == null) {
            value = this.getValueFromMethod(form, field);
        }
        return value;
    }

    public Object getValueFromField(Form form, Field field) {
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
    
    public Object getValueFromMethod(Form form, Field field) {
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

    public int getMaxLength(Form form, Field field) {
        return -1;
    }
    
    public boolean isOptional(Form form, Field field) {
        return false;
    }

    public String getType(Form form, Field field) {
        final String type;
        if(new IsPasswordField().test(field.getName())) {
            type = StandardFormFieldTypes.PASSWORD; 
        }else{
            type = this.getFieldTypeFunctor().apply(field);
        }
        return type;
    }    

    public Function<Field, String> getFieldTypeFunctor() {
        return new GetFormFieldTypeForField(StandardFormFieldTypes.TEXT);
    }

    protected FormField buildFormField(Form form, Field field, FormFieldBuilder builder) {
        return builder.build();
    }
}
