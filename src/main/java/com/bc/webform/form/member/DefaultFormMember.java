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
import com.bc.webform.form.Form;
import com.bc.webform.functions.IdFromName;
import com.bc.webform.functions.LabelFromName;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 3:02:13 PM
 */
public class DefaultFormMember<F, V> extends FormMemberBean<F, V>{

    public DefaultFormMember(Form form, String name) {
        this(form, new IdFromName().apply(name), name, new LabelFromName().apply(name), null, 35);
    }
    
    public DefaultFormMember(Form form, String id, String name, String label, V value, int size) {
        this.setForm(Objects.requireNonNull(form));
        this.setId(Objects.requireNonNull(id));
        this.setName(Objects.requireNonNull(name));
        this.setLabel(Objects.requireNonNull(label));
        this.setValue(value);
        this.setSize(size);
        this.setMultiChoice(false);
        this.setChoices(Collections.EMPTY_LIST);
        this.setMaxLength(-1);
        this.setNumberOfLines(-1);
        this.setType(StandardFormFieldTypes.TEXT);
        this.setRequired(true);
        this.setMultiple(value instanceof Collection || value instanceof Map || value instanceof Object[]);
    }
}
