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
public class FormBean<S> implements IdentifiableFieldSet, Form<S>, Serializable{
    
    private Form parent;
    private String id;
    private String name;
    private String label;
    private List<FormMember> formFields;
    private S dataSource;

    public FormBean() { }
    
    public FormBean(Form<S> form) { 
        this.parent = form.getParent();
        this.id = form.getId();
        this.name = form.getName();
        this.label = form.getLabel();
        this.formFields = form.getMembers();
        this.dataSource = form.getDataSource();
    }

    // We override this here because some templating engines cannot 
    // access it from the super type
    /**
     * Alias for {@link #getLabel() }
     * @return The display name
     * @see #getLabel() 
     */
    @Override
    public String getDisplayName() {
        return Form.super.getDisplayName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAnyFieldSet() {
        return (this.getParent() != null || this.getId() != null ||
                this.getName() != null || this.getLabel() != null ||
                this.getMembers() != null);
    }
    
    @Override
    public void checkRequiredFieldsAreSet() {
        Objects.requireNonNull(this.getId());
        Objects.requireNonNull(this.getName());
        Objects.requireNonNull(this.getLabel());
        Objects.requireNonNull(this.getMembers());
    }
    
    @Override
    public FormBean copy() {
        return new FormBean(this);
    }

    @Override
    public Optional<FormMember> getMember(String name) {
        return this.getMembers().stream()
                .filter((ff) -> Objects.equals(ff.getName(), name)).findFirst();
    }

    @Override
    public List<FormMember> getHiddenMembers() {
        return this.getMembers().stream()
                .filter((ff) -> StandardFormFieldTypes.HIDDEN.equals(ff.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FormMember> getNonHiddenMembers() {
        return this.getMembers().stream()
                .filter((ff) -> ! StandardFormFieldTypes.HIDDEN.equals(ff.getType()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getMemberNames() {
        return this.getMembers().stream()
                .map((ff) -> ff.getName())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getRequiredMemberNames() {
        return this.getMembers().stream()
                .filter((ff) -> !ff.isOptional())
                .map((ff) -> ff.getName())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getOptionalMemberNames() {
        return this.getMembers().stream()
                .filter((ff) -> ff.isOptional())
                .map((ff) -> ff.getName())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getFileTypeMemberNames() {
        return this.getMembers().stream()
                .filter((ff) -> StandardFormFieldTypes.FILE.equalsIgnoreCase(ff.getType()))
                .map((ff) -> ff.getName())
                .collect(Collectors.toList());
    }
    
     ///////////////////////////////////
     // builder methods
     ///////////////////////////////////

    public FormBean<S> parent(Form form) {
        this.setParent(form);
        return this;
    }
    
    public FormBean<S> id(String id) {
        this.setId(id);
        return this;
    }

    public FormBean<S> name(String name) {
        this.setName(name);
        return this;
    }

    public FormBean<S> displayName(String displayName) {
        this.setLabel(displayName);
        return this;
    }

    public FormBean<S> formFields(List<FormMember> formFields) {
        this.setFormFields(formFields);
        return this;
    }

    public FormBean<S> dataSource(S dataSource) {
        this.setDataSource(dataSource);
        return this;
    }

    ///////////////////////////////////
    // getters and setters
    ///////////////////////////////////
    
    @Override
    public Form getParent() {
        return parent;
    }

    public void setParent(Form parent) {
        this.parent = parent;
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
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public S getDataSource() {
        return dataSource;
    }

    public void setDataSource(S dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<FormMember> getMembers() {
        return formFields;
    }

    public void setFormFields(List<FormMember> formFields) {
        this.formFields = formFields;
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
        builder.append(", parent: ").append(parent==null?null:parent.getName());
        builder.append(", dataSource: ").append(dataSource);
        builder.append("\nfield names : ").append(this.getMemberNames());
        builder.append("\n}");
        return builder.toString();
    }
}
