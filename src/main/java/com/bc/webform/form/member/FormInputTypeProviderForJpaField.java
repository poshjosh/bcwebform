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

import com.bc.webform.StandardFormFieldTypes;
import java.lang.reflect.Field;
import java.util.function.Predicate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 20, 2019 4:32:48 PM
 */
public class FormInputTypeProviderForJpaField 
        extends FormInputTypeProviderForPojo {

    public FormInputTypeProviderForJpaField() { }

    public FormInputTypeProviderForJpaField(
            Predicate<String> passwordInputTest, String resultIfNone) {
        super(passwordInputTest, resultIfNone);
    }

    @Override
    public String getType(Object source, Field field) {
        final String type;
        final Temporal temporal = field.getAnnotation(Temporal.class);
        final TemporalType tt = temporal == null ? null : temporal.value();
        if(tt == null) {
            type = super.getType(source, field);
        }else{
            switch (tt) {
                case DATE:
                    type = StandardFormFieldTypes.DATE;
                    break;
                case TIMESTAMP:
                    type = StandardFormFieldTypes.DATETIME;
                    break;
                case TIME:
                    type = StandardFormFieldTypes.TIME;
                    break;
                default:
                    type = super.getType(source, field);
                    break;
            }
        }
        return type;
    }
}
