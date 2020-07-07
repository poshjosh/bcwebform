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

import java.lang.reflect.Field;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2019 1:23:50 PM
 */
public class FormInputContextForJpaEntity extends FormInputContextForPojo{

//    private static final Logger LOG = Logger.getLogger(
//            FormInputContextForJpaEntity.class.getName());

    public FormInputContextForJpaEntity() {
        this(
                new FormInputTypeProviderForJpaField(),
                new FormInputValueProviderForPojo()
        );
    }

    public FormInputContextForJpaEntity(
            FormInputTypeProvider<Object, Field> formInputTypeProvider, 
            FormInputValueProvider<Object, Field, Object> formInputValueProvider) {
        super(formInputTypeProvider, formInputValueProvider);
    }

// Use field.getName() so we can update the Bean#fieldName
// No need for the Column annotation    
//    @Override
//    public String getName(Object source, Field field) {
        // The field name is either 
        // 1. The value of the name property of the Column annotation
        // 2. Or the value of field.getName()
        // The value of the name property of the field.getType() table annotation
        // is not applicable.
        
//        final Column column = field.getAnnotation(Column.class);
//        final String nameFromCol = column == null ? null : column.name();
//        final String result = nameFromCol == null ? field.getName() : nameFromCol;
//        return result;
//    }

    @Override
    public boolean isOptional(Object source, Field field) {
        final Null nul = field.getAnnotation(Null.class);
        if(nul != null) {
            return true;
        }
        final NotNull notNull = field.getAnnotation(NotNull.class);
        if(notNull != null) {
            return false;
        }
        final Column column = field.getAnnotation(Column.class);
        if(column != null && ! column.nullable()) {
            return false;
        }
        final Basic basic = field.getAnnotation(Basic.class);
        if(basic != null && ! basic.optional()) {
            return false;
        }
        final Size size = field.getAnnotation(Size.class);
        if(size != null && size.min() > 0) {
            return false;
        }
        final ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
        if(manyToOne != null && ! manyToOne.optional()) {
            return false;
        }
        final OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        if(oneToOne != null && ! oneToOne.optional()) {
            return false;
        }
        return true;
    }
}
