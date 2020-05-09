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

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 4:22:32 PM
 */
public class FormBean implements Form, Serializable{
    
    private String id;
    private String name;
    private String displayName;
    private List<FormField> formFields;
    private List<String> datePatterns;
    private List<String> timePatterns;
    private List<String> datetimePatterns;

    public FormBean() { }
    
    public FormBean(Form form) { 
        this.id = form.getId();
        this.name = form.getName();
        this.displayName = form.getDisplayName();
        this.formFields = form.getFormFields();
        this.datePatterns = form.getDatePatterns();
        this.timePatterns = form.getTimePatterns();
        this.datetimePatterns = form.getDatetimePatterns();
    }
    
    public FormBean copy() {
        return new FormBean(this);
    }

    @Override
    public Optional<FormField> getFormField(String name) {
        return this.getFormFields().stream().filter((ff) -> Objects.equals(ff.getName(), name)).findFirst();
    }
    
    @Override
    public List<String> getFieldNames() {
        return this.getFormFields().stream()
                .map((ff) -> ff.getName())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getRequiredFieldNames() {
        return this.getFormFields().stream()
                .filter((ff) -> !ff.isOptional())
                .map((ff) -> ff.getName())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getOptionalFieldNames() {
        return this.getFormFields().stream()
                .filter((ff) -> ff.isOptional())
                .map((ff) -> ff.getName())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getFileFieldNames() {
        return this.getFormFields().stream()
                .filter((ff) -> StandardFormFieldTypes.FILE.equalsIgnoreCase(ff.getType()))
                .map((ff) -> ff.getName())
                .collect(Collectors.toList());
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }

    @Override
    public List<String> getDatePatterns() {
        return datePatterns;
    }

    public void setDatePatterns(List<String> datePatterns) {
        this.datePatterns = datePatterns;
    }

    @Override
    public List<String> getTimePatterns() {
        return timePatterns;
    }

    public void setTimePatterns(List<String> timePatterns) {
        this.timePatterns = timePatterns;
    }

    @Override
    public List<String> getDatetimePatterns() {
        return datetimePatterns;
    }

    public void setDatetimePatterns(List<String> datetimePatterns) {
        this.datetimePatterns = datetimePatterns;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FormBean other = (FormBean) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName()).append("{\n");
        builder.append("ID: ").append(this.getId());
        builder.append(", field names : ").append(this.getFieldNames());
        builder.append("\n}");
        return builder.toString();
    }
}
