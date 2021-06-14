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

import com.bc.webform.TypeTests;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Predicate;
import javax.persistence.GeneratedValue;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2019 1:33:15 PM
 */
public class IsFormFieldTestForJpaEntity implements Predicate<Field>{
    
    private final TypeTests typeTests;

    public IsFormFieldTestForJpaEntity(TypeTests typeTests) {
        this.typeTests = Objects.requireNonNull(typeTests);
    }

    @Override
    public boolean test(Field field) {
        
        if(Modifier.isStatic(field.getModifiers())
                || field.getAnnotation(GeneratedValue.class) != null
                || "serialVersionUID".equalsIgnoreCase(field.getName()) 
                || field.getName().startsWith("_persistence")){ 

            return false;

        }else if(this.typeTests.isContainerType(field.getType()) 
                && ! this.typeTests.isEnumType(field.getType())){    

            return false;

        }else{

            return true;
        }
    }
}
