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

import com.bc.webform.functions.IdFromName;
import com.bc.webform.functions.LabelFromName;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 3:02:13 PM
 */
public class DefaultFormMember<F, V> implements FormMember<F, V>, Serializable {

    private final Form form;
    private final String id;
    private final String name;
    private final String label;
    private final V value;
    private final int size;

    public DefaultFormMember(Form form, String name) {
        this(form, new IdFromName().apply(name), name, new LabelFromName().apply(name), null, 35);
    }
    
    public DefaultFormMember(Form form, String id, String name, String label, V value, int size) {
        this.form = Objects.requireNonNull(form);
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.label = Objects.requireNonNull(label);
        this.value = value;
        this.size = size;
    }

    @Override
    public FormMember<F, V> copy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FormMemberBean<F, V> writableCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FormMember<F, V> withValue(V value) {
        return new DefaultFormMember(form, id, name, label, value, size);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getAdvice() {
        return null;
    }

    @Override
    public V getValue() {
        return null;
    }

    @Override
    public Map getChoices() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public int getMaxLength() {
        return -1;
    }

    @Override
    public int getNumberOfLines() {
        return -1;
    }

    @Override
    public String getType() {
        return StandardFormFieldTypes.TEXT;
    }

    @Override
    public Form getForm() {
        return form;
    }

    @Override
    public String getReferencedFormHref() {
        return null;
    }

    @Override
    public Form getReferencedForm() {
        return null;
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public boolean isRequired() {
        return ! this.isOptional();
    }

    @Override
    public boolean isMultiChoice() {
        return ! this.getChoices().isEmpty();
    }

    @Override
    public boolean isMultiple() {
        return value instanceof Collection || value instanceof Map || value instanceof Object[]; 
    }

    @Override
    public boolean isFormReference() {
        return this.getReferencedForm() != null;
    }

    @Override
    public F getDataSource() {
        return null;
    }
}
