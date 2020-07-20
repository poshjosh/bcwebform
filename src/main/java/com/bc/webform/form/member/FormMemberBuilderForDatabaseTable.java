package com.bc.webform.form.member;

import com.bc.db.meta.access.MetaDataAccess;
import com.bc.db.meta.access.MetaDataAccessImpl;
import com.bc.webform.StandardFormFieldTypes;
import com.bc.webform.functions.IsPasswordField;
import com.bc.webform.TableMetadata;
import com.bc.webform.TableMetadataImpl;
import java.util.Objects;
import java.util.function.Predicate;
import javax.persistence.EntityManagerFactory;

/**
 * @author hp
 */
public class FormMemberBuilderForDatabaseTable extends FormMemberBuilderImpl<String, String, Object>{

    private int maxLength = -1;
    
    private final EntityManagerFactory entityManagerFactory;

    public FormMemberBuilderForDatabaseTable(EntityManagerFactory entityManagerFactory) {
        
        this.entityManagerFactory = Objects.requireNonNull(entityManagerFactory);
            
        if(this.getFormInputContext() == null) {
            this.formInputContext(new IsPasswordField(), StandardFormFieldTypes.TEXT);
        }

        if(this.getMultipleInputTest() == null) {
            // By default fields do not accept multiple inputs
            this.multipleInputTest((formDataSource, dataSourceField) -> false);
        }
    }
    
    @Override
    public int initMaxLength() {
        if(this.maxLength == -1) {
            final String columName = this.getDataSource();
            this.maxLength = this.getTableMetadata(entityManagerFactory)
                    .getColumnDisplaySize(columName);
        }
        return this.maxLength;
    }

    public FormMemberBuilderForDatabaseTable formInputContext(
            Predicate<String> passwordInputTest, 
            String inputTypeIfNone) {
        
        final TableMetadata tableMetadata = this.getTableMetadata(this.entityManagerFactory);
        
        final FormInputTypeProvider<String, String> typeProvider = 
                new FormInputTypeProviderForDatabaseTable(
                        tableMetadata, passwordInputTest, inputTypeIfNone);
        
        this.formInputContext(tableMetadata, typeProvider);
        
        return this;
    }

    public FormMemberBuilderForDatabaseTable formInputContext(
            TableMetadata tableMetadata,
            FormInputTypeProvider<String, String> typeProvider) {
        
        this.formInputContext(
                new FormInputContextForDatabaseTable(tableMetadata, typeProvider));
        
        return this;
    }

    public TableMetadata getTableMetadata(EntityManagerFactory emf) {

        final MetaDataAccess metaDataAccess = new MetaDataAccessImpl(emf);
        
        final String tableName = this.getForm().getDataSource();
        
        final TableMetadata tableMetadata = new TableMetadataImpl(
                metaDataAccess, null, null, tableName);
        
        return tableMetadata;
    }


    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
