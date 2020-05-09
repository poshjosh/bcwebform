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

import java.lang.reflect.Field;
import java.util.function.Predicate;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2019 1:33:15 PM
 */
public class AnnotatedPersistenceFieldIsFormFieldTest implements Predicate<Field>{
    
    private final IsContainerField isContainerField;

    public AnnotatedPersistenceFieldIsFormFieldTest() {
        this.isContainerField = new IsContainerField();
    }

    @Override
    public boolean test(Field field) {
        if("serialVersionUID".equalsIgnoreCase(field.getName()) 
                || field.getName().startsWith("_persistence") 
                || field.getAnnotation(GeneratedValue.class) != null){ 
            return false;
        }else if(this.isContainerField.test(field) && ! this.isEnumerated(field)){    
            return false;
        }else{
            return true;
        }
    }

    public boolean isEnumerated(Field field) {
        return field.getAnnotation(Enumerated.class) != null;
    }
}
