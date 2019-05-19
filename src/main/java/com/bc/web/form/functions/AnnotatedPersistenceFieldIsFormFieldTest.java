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

import java.lang.reflect.Field;
import java.util.function.Predicate;
import javax.persistence.GeneratedValue;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2019 1:33:15 PM
 */
public class AnnotatedPersistenceFieldIsFormFieldTest implements Predicate<Field>{

    @Override
    public boolean test(Field field) {
        if("serialVersionUID".equalsIgnoreCase(field.getName()) 
                || field.getName().startsWith("_persistence") 
                || field.getAnnotation(GeneratedValue.class) != null){ 
//                || Collection.class.isAssignableFrom(field.getType()) 
//                || Map.class.isAssignableFrom(field.getType())) {
            return false;
        }else{
            return true;
        }
    }

}
