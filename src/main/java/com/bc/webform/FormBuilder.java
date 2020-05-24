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

package com.bc.webform;

import com.bc.webform.exceptions.ValuesOverwriteByDefaultException;
import com.bc.webform.functions.SourceFieldsProvider;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 4:51:00 PM
 */
public interface FormBuilder<S, F, V> extends Builder<Form<S>>{

    @Override
    Form<S> build();
    
    /**
     * Apply default values.
     * 
     * The default values are gotten by building a {@link com.bc.webform.DefaultForm DefaultForm}
     * for the supplied name.
     * 
     * <p><b>Note:</b></p>
     * This method should be the first method called. Calling the method 
     * after any value has been set will lead to IllegalStateException 
     * @param name The name of the {@link com.bc.webform.DefaultForm DefaultForm} 
     * to build and whose values will be used to update the current build.
     * @throws com.bc.webform.exceptions.ValuesOverwriteByDefaultException
     * @return this object
     */
    FormBuilder<S, F, V> applyDefaults(String name) throws ValuesOverwriteByDefaultException;

    @Override
    FormBuilder<S, F, V> apply(Form form);
    
    FormBuilder<S, F, V> dataSource(S source);
    
    FormBuilder<S, F, V> sourceFieldsProvider(
            SourceFieldsProvider<S, F> sourceFieldsProvider);

    FormBuilder<S, F, V> formMemberBuilder(FormMemberBuilder<S, F, V> formFieldBuilder);

    FormBuilder<S, F, V> formMemberTest(Predicate<FormMember<F, V>> test);
    
    FormBuilder<S, F, V> formMemberComparator(Comparator<FormMember<F, V>> comparator);
    
    FormBuilder<S, F, V> id(String id);

    FormBuilder<S, F, V> parent(Form form);
}
