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

import com.bc.db.meta.access.MetaDataAccess;
import com.bc.db.meta.access.MetaDataAccessImpl;
import com.bc.web.form.DefaultFormField;
import com.bc.web.form.Form;
import com.bc.web.form.FormField;
import com.bc.web.form.FormFieldBuilder;
import com.bc.web.form.StandardFormFieldTypes;
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
    private TableMetadata tableMetadata;
    private final BiPredicate<String, String> isFormField;

    public CreateFormFieldsFromDatabaseTable(EntityManagerFactory emf) {
        this(new MetaDataAccessImpl(emf), new ColumnNameIsFormFieldTest(emf));
    }
        
    public CreateFormFieldsFromDatabaseTable(EntityManagerFactory emf, BiPredicate<String, String> isFormField) {
        this(new MetaDataAccessImpl(emf), isFormField);
    }
    
    public CreateFormFieldsFromDatabaseTable(MetaDataAccess mda, BiPredicate<String, String> isFormField) {
        this(mda, null, null, isFormField);
    }
    
    public CreateFormFieldsFromDatabaseTable(MetaDataAccess metaDataAccess, 
            String catalog, String schema, BiPredicate<String, String> isFormField) {
        this.metaDataAccess = Objects.requireNonNull(metaDataAccess);
        this.catalog = catalog;
        this.schema = schema;
        this.isFormField = Objects.requireNonNull(isFormField);
    }

    @Override
    public List<FormField> apply(Form form, String table) {
        this.tableMetadata = new TableMetadataImpl(metaDataAccess, catalog, schema, table);
        return Collections.unmodifiableList(tableMetadata.getColumnNames().stream()
                .filter((col) -> this.isFormField.test(table, col))
                .map((name) -> this.newFormField(form, name))
                .collect(Collectors.toList()));
    }
    
    @Override
    public FormField newFormField(Form form, String field) {
        
        final int maxLen = this.getMaxLength(form, field);
        final int lineMaxLen = getLineMaxLength(form, field);
        final int numberOfLines = maxLen <= lineMaxLen ? 1 : maxLen / lineMaxLen;
        
        final FormFieldBuilder builder = new FormField.Builder()
                .apply(new DefaultFormField(form, field))
                .choices(this.getChoices(form, field))
                .value(this.getValue(form, field))
                .maxLength(maxLen)
                .numberOfLines(numberOfLines)
                .optional(this.isOptional(form, field))
                .multiChoice(this.isMultiChoice(form, field))
                .multiValue(this.isMultiValue(form, field))
                .referencedForm(this.getReferencedForm(form, field))
                .type(this.getType(form, field));
                
        return this.buildFormField(field, builder);
    }
    
    public int getLineMaxLength(Form form, String field) {
        return 256;
    }
    
    public Form getReferencedForm(Form form, String field) {
        return null;
    }
    
    public boolean isMultiChoice(Form form, String field) {
        return false;
    }
    
    public boolean isMultiValue(Form form, String field) {
        return false;
    }
    
    public Map getChoices(Form form, String field) {
        return Collections.EMPTY_MAP;
    }

    public Object getValue(Form form, String field) {
        return null;
    }
    
    public int getMaxLength(Form form, String field) {
        return this.tableMetadata.getColumnDisplaySize(field);
    }
    
    public boolean isOptional(Form form, String field) {
        final int sqlNullable = this.tableMetadata.getColumnNullable(field);
        return sqlNullable == ResultSetMetaData.columnNullable;
    }

    public String getType(Form form, String field) {
        final String type;
        if(new IsPasswordField().test(field)) {
            type = StandardFormFieldTypes.PASSWORD; 
        }else{
            final int sqlDataType = this.tableMetadata.getColumnDataType(field);
            type = this.getFieldTypeFunctor().apply(sqlDataType);
        }
        return type;
    }    
    
    public Function<Integer, String> getFieldTypeFunctor() {
        return new GetFormFieldTypeForSqlType(StandardFormFieldTypes.TEXT);
    }
        
    protected FormField buildFormField(String fieldName, FormFieldBuilder builder) {
        return builder.build();
    }
}
