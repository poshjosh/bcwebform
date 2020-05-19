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

import com.bc.db.meta.access.MetaDataAccess;
import com.bc.db.meta.access.MetaDataAccessImpl;
import com.bc.webform.Form;
import com.bc.webform.FormField;
import com.bc.webform.FormFieldBuilderImpl;
import com.bc.webform.ReferenceFormCreator;
import com.bc.webform.StandardFormFieldTypes;
import java.sql.ResultSetMetaData;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 4:09:42 PM
 */
public class CreateFormFieldsFromDatabaseTable implements FormFieldsCreator<String, String>{

//    private static final Logger LOG = Logger.getLogger(CreateFormFieldsFromDatabaseTable.class.getName());
    
    private final MetaDataAccess metaDataAccess;
    private final String catalog;
    private final String schema;
    private final BiPredicate<String, String> isFormField;
    private final ReferenceFormCreator<String, String> referenceFormCreator;
    private TableMetadata tableMetadata;

    public CreateFormFieldsFromDatabaseTable(EntityManagerFactory emf) {
        this(new MetaDataAccessImpl(emf), new ColumnNameIsFormFieldTest(emf));
    }
        
    public CreateFormFieldsFromDatabaseTable(EntityManagerFactory emf, BiPredicate<String, String> isFormField) {
        this(new MetaDataAccessImpl(emf), isFormField);
    }
    
    public CreateFormFieldsFromDatabaseTable(MetaDataAccess mda, BiPredicate<String, String> isFormField) {
        this(mda, null, null, isFormField, (form, source, field) -> null);
    }
    
    public CreateFormFieldsFromDatabaseTable(
            MetaDataAccess metaDataAccess, 
            String catalog, 
            String schema, 
            BiPredicate<String, String> isFormField,
            ReferenceFormCreator<String, String> referenceFormCreator) {
        this.metaDataAccess = Objects.requireNonNull(metaDataAccess);
        this.catalog = catalog;
        this.schema = schema;
        this.isFormField = Objects.requireNonNull(isFormField);
        this.referenceFormCreator = Objects.requireNonNull(referenceFormCreator);
    }

    @Override
    public List<FormField> apply(Form form, String table) {
        this.tableMetadata = new TableMetadataImpl(metaDataAccess, catalog, schema, table);
        return Collections.unmodifiableList(tableMetadata.getColumnNames().stream()
                .filter((col) -> this.isFormField.test(table, col))
                .map((name) -> this.newFormField(form, table, name))
                .collect(Collectors.toList()));
    }
    
    @Override
    public FormField newFormField(Form form, String table, String field) {
        
        final int maxLen = this.getMaxLength(form, table, field);
        final int lineMaxLen = getLineMaxLength(form, table, field);
        final int numberOfLines = maxLen <= lineMaxLen ? 1 : maxLen / lineMaxLen;
        
        final FormFieldBuilderImpl builder = new FormField.Builder()
                .applyDefaults(form, field)
                .form(form);
                
                builder.getDelegate()
                .form(form)        
                .choices(this.getChoices(form, table, field))
                .value(this.getValue(form, table, field))
                .maxLength(maxLen)
                .numberOfLines(numberOfLines)
                .optional(this.isOptional(form, table, field))
                .multiChoice(this.isMultiChoice(form, table, field))
                .multiValue(this.isMultiValue(form, table, field))
                .referencedFormHref(this.getReferencedFormHref(form, table, field))
                .referencedForm(this.getReferencedForm(form, table, field))
                .type(this.getType(form, table, field));
                
        return this.buildFormField(form, table, field, builder);
    }
    
    public int getLineMaxLength(Form form, String table, String field) {
        return 256;
    }

    public String getReferencedFormHref(Form form, String table, String field) {
        return null;
    }
    
    public Form getReferencedForm(Form form, String table, String field) {
        return this.referenceFormCreator.createReferenceForm(form, table, field).orElse(null);
    }
    
    public boolean isMultiChoice(Form form, String table, String field) {
        return false;
    }
    
    public boolean isMultiValue(Form form, String table, String field) {
        return false;
    }
    
    public Map getChoices(Form form, String table, String field) {
        return Collections.EMPTY_MAP;
    }

    public Object getValue(Form form, String table, String field) {
        return null;
    }
    
    public int getMaxLength(Form form, String table, String field) {
        return this.tableMetadata.getColumnDisplaySize(field);
    }
    
    public boolean isOptional(Form form, String table, String field) {
        final int sqlNullable = this.tableMetadata.getColumnNullable(field);
        return sqlNullable == ResultSetMetaData.columnNullable;
    }

    public String getType(Form form, String table, String field) {
        final String type;
        if(new IsPasswordField().test(field)) {
            type = StandardFormFieldTypes.PASSWORD; 
        }else{
            final int sqlDataType = this.tableMetadata.getColumnDataType(field);
            type = this.getFieldTypeFunctor(form, table, field).apply(sqlDataType);
        }
        return type;
    }    
    
    public Function<Integer, String> getFieldTypeFunctor(Form form, String table, String field) {
        return new GetFormFieldTypeForSqlType(StandardFormFieldTypes.TEXT);
    }
        
    protected FormField buildFormField(Form form, String table, String fieldName, FormFieldBuilderImpl builder) {
        return builder.build();
    }
}
