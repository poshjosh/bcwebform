package com.bc.webform;

import com.bc.webform.functions.IsFormFieldTestForDatabaseTable;
import com.bc.webform.functions.SourceFieldsProviderForDatabaseTable;
import java.util.function.BiPredicate;
import javax.persistence.EntityManagerFactory;

/**
 * @author hp
 */
public class FormBuilderForDatabaseTable extends FormBuilderImpl<String, String, Object>{

    private EntityManagerFactory entityManagerFactory;
    
    @Override
    public Form build() {
        
        if(this.getSourceFieldsProvider() == null) {
            this.sourceFieldsProvider(
                    new IsFormFieldTestForDatabaseTable(entityManagerFactory));
        }
        
        if(this.getFormMemberBuilder() == null) {
            this.formMemberBuilder(
                    new FormMemberBuilderForDatabaseTable()
                            .entityManagerFactory(entityManagerFactory));
        }
        
        return super.build(); 
    }
    
    public FormBuilderForDatabaseTable sourceFieldsProvider(
            BiPredicate<String, String> isFormField) {

        this.sourceFieldsProvider(
                new SourceFieldsProviderForDatabaseTable(entityManagerFactory, isFormField));
        
        return this;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public FormBuilderForDatabaseTable entityManagerFactory(
            EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        return this;
    }
}
