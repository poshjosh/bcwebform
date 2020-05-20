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

import com.bc.webform.Form;
import java.util.List;
import java.util.function.BiFunction;
import com.bc.webform.FormMember;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 4:50:09 PM
 */
public interface FormFieldsCreator<E, e> extends BiFunction<Form, E, List<FormMember>>{

    @Override
    List<FormMember> apply(Form form, E source);

    FormMember newFormField(Form form, E source, e child_of_source);
}
