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
import com.bc.web.form.Form;
import com.bc.web.form.PreferMandatoryField;
import com.bc.web.form.StandardFormFieldTypes;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2019 1:23:50 PM
 */
public class CreateFormFieldsFromAnnotatedPersistenceEntity extends CreateFormFieldsFromObject{

    private static final Logger LOG = Logger.getLogger(CreateFormFieldsFromAnnotatedPersistenceEntity.class.getName());

    public CreateFormFieldsFromAnnotatedPersistenceEntity() {
        super(new AnnotatedPersistenceFieldIsFormFieldTest(), -1);
    }

    public CreateFormFieldsFromAnnotatedPersistenceEntity(Predicate<Field> isFormField, int maxDepth) {
        super(isFormField, maxDepth);
    }

    @Override
    public Form getReferencedForm(Form form, Field field) {
        final Optional<Class> optional = this.getReferenceType(form, field);
        final Class targetType = optional.orElse(null);
        if(targetType == null) {
            return null;
        }else{
            try{
                final Object dto = this.newInstance(targetType);
                return new Form.Builder()
                        .withDefault(this.getFormFieldName(form, field))
                        .fieldsCreator(this)
                        .fieldsComparator(new PreferMandatoryField())
                        .fieldDataSource(dto).build();
            }catch(Exception e) {
                LOG.log(Level.WARNING, "Failed to create form for: " + targetType, e);
                return null;
            }
        }
    }
    
    public String getFormFieldName(Form form, Field field) {
        final Class fieldType = field.getType();
        final Table table = (Table)fieldType.getAnnotation(Table.class);
        final String name;
        if(table == null) {
            name = field.getName(); //fieldType.getSimpleName();
        }else{
            name = table.name();
        }    
        return name;
    }
    
    public Optional<Class> getReferenceType(Form form, Field field) {
        Class output = null;
        if(this.isEnumerated(field) || this.isMultiValue(form, field)) {
            output = null;
        }else{
            final Class fieldType = field.getType();
            if(this.isDomainObject(fieldType)) {
                output = fieldType;
            }else{
                output = null;
            }
        }
        LOG.log(Level.FINER, "Field: {0}, reference type: {1}",
                new Object[]{field.getName(), output});
        return Optional.ofNullable(output);
    }

    @Override
    public Map getChoices(Form form, Field field) {
        if(this.isMultiChoice(form, field)) {
            final Class fieldType = field.getType();
            if(fieldType.isEnum()) {
                final Object [] enums = fieldType.getEnumConstants();
                if(enums != null) {
                    final Map choices = new HashMap<>(enums.length, 1.0f);
                    for(int i = 0; i<enums.length; i++) {
                        choices.put((i + 1), enums[i]);
                    }
                    return Collections.unmodifiableMap(choices);
                }
            }
        }
        return super.getChoices(form, field); 
    }
    
    public Object newInstance(Class type) {
        return new ReflectionUtil().newInstance(type);
    }

    @Override
    public boolean isMultiChoice(Form form, Field field) {
        return this.isEnumerated(field);
    }
    
    public boolean isDomainObject(Class type) {
        return type.getAnnotation(Entity.class) != null || type.getAnnotation(Table.class) != null;
    }
    
    public boolean isEnumerated(Field field) {
        return field.getAnnotation(Enumerated.class) != null;
    }

    @Override
    public Function<Field, String> getFieldTypeFunctor() {
        return new GetFormFieldTypeForAnnotatedPersistenceField(StandardFormFieldTypes.TEXT);
    }
    
    @Override
    public int getMaxLength(Form form, Field field) {
        final Size size = field.getAnnotation(Size.class);
        int maxLen = -1;
        if(size != null) {
            maxLen = size.max();
        }
        if(maxLen == -1) {
            final Column column = field.getAnnotation(Column.class);
            maxLen = column == null ? -1 : column.length();
        }
        final int n = maxLen;
        LOG.log(Level.FINER, () -> form.getName() + '.' + field.getName() + ".maxLength = " + n);
        return maxLen;
    }
    
    @Override
    public boolean isOptional(Form form, Field field) {
        final Null nul = field.getAnnotation(Null.class);
        if(nul != null) {
            return true;
        }
        final NotNull notNull = field.getAnnotation(NotNull.class);
        if(notNull != null) {
            return false;
        }
        final Column column = field.getAnnotation(Column.class);
        if(column != null && !column.nullable()) {
            return false;
        }
        final Basic basic = field.getAnnotation(Basic.class);
        if(basic != null && !basic.optional()) {
            return false;
        }
        final Size size = field.getAnnotation(Size.class);
        if(size != null && size.min() > 0) {
            return false;
        }
        final ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
        if(manyToOne != null && manyToOne.optional()) {
            return false;
        }
        final OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        if(oneToOne != null && oneToOne.optional()) {
            return false;
        }
        return true;
    }
}
