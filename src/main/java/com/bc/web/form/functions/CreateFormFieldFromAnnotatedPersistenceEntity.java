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

import com.bc.web.form.Form;
import com.bc.web.form.StandardFormFieldTypes;
import java.lang.reflect.Field;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2019 1:23:50 PM
 */
public class CreateFormFieldFromAnnotatedPersistenceEntity extends CreateFormFieldsFromObject{

    public CreateFormFieldFromAnnotatedPersistenceEntity() {
        super(new AnnotatedPersistenceFieldIsFormFieldTest(), -1);
    }

    protected CreateFormFieldFromAnnotatedPersistenceEntity(Predicate<Field> isFormField, int maxDepth) {
        super(isFormField, maxDepth);
    }

    @Override
    public Function<Field, String> getFieldTypeFunctor() {
        return new GetFormFieldTypeForAnnotatedPersistenceField(StandardFormFieldTypes.TEXT);
    }
    
    @Override
    public int getMaxLength(Form form, Field field) {
        final Size size = field.getAnnotation(Size.class);
        if(size != null) {
            return size.max();
        }
        final Column column = field.getAnnotation(Column.class);
        return column == null ? -1 : column.length();
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
//        final Size size = field.getAnnotation(Size.class);
//        if(size != null && size.min() > 0) {
//            return false;
//        }
        return true;
    }
}
