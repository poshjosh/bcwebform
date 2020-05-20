package com.bc.webform;

import com.bc.db.meta.access.MetaDataAccess;
import com.bc.db.meta.access.MetaDataAccessImpl;
import com.bc.webform.functions.FormInputContextForDatabaseTable;
import com.bc.webform.functions.FormInputTypeProvider;
import com.bc.webform.functions.FormInputTypeProviderForDatabaseTable;
import com.bc.webform.functions.IsPasswordField;
import com.bc.webform.functions.MultiValueTestForDatabaseTable;
import com.bc.webform.functions.TableMetadata;
import com.bc.webform.functions.TableMetadataImpl;
import java.util.function.Predicate;
import javax.persistence.EntityManagerFactory;

/**
 * @author hp
 */
public class FormMemberBuilderForDatabaseTable extends FormMemberBuilderImpl<String, String, Object>{

    private int maxLength = -1;
    
    private EntityManagerFactory entityManagerFactory;
    
    @Override
    public FormMember<Object> build() {
        try{
            
            if(this.getFormInputContext() == null) {
                this.formInputContext(new IsPasswordField(), StandardFormFieldTypes.TEXT);
            }

            if(this.getMultiValueTest() == null) {
                this.multiValueTest(new MultiValueTestForDatabaseTable());
            }

            return super.build();
            
        }finally{
            this.maxLength = -1;
        }
    }
    
    @Override
    public int initMaxLength() {
        if(this.maxLength == -1) {
            final String columName = this.getDataSourceField();
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
        
        final String tableName = this.getFormDataSource();
        
        final TableMetadata tableMetadata = new TableMetadataImpl(
                metaDataAccess, null, null, tableName);
        
        return tableMetadata;
    }


    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public FormMemberBuilderForDatabaseTable entityManagerFactory(
            EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        return this;
    }
}
