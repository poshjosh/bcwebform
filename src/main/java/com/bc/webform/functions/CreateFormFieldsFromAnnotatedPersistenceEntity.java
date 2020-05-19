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

import com.bc.webform.FormFieldBuilderFromAnnotatedPersistenceEntity;
import com.bc.webform.FormFieldBuilderFromSource;
import java.lang.reflect.Field;
import java.util.function.Predicate;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2019 1:23:50 PM
 */
public class CreateFormFieldsFromAnnotatedPersistenceEntity extends CreateFormFieldsFromObject{

//    private static final Logger LOG = Logger.getLogger(
//            CreateFormFieldsFromAnnotatedPersistenceEntity.class.getName());

    public CreateFormFieldsFromAnnotatedPersistenceEntity() { 
        this(new FormFieldBuilderFromAnnotatedPersistenceEntity());
    }

    public CreateFormFieldsFromAnnotatedPersistenceEntity(
            FormFieldBuilderFromSource formFieldBuilder) {
        this((field) -> true, -1, formFieldBuilder);
    }
    
    public CreateFormFieldsFromAnnotatedPersistenceEntity(
            Predicate<Field> isFormField, 
            int maxLevelOfClassHierarchyToCheckForFields, 
            FormFieldBuilderFromSource formFieldBuilder) {
        
        super(isFormField, maxLevelOfClassHierarchyToCheckForFields, formFieldBuilder);
    }
}
