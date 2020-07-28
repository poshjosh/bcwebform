package com.bc.webform.form.builder;

import com.bc.webform.form.SourceFieldsProviderForDatabaseTable;
import com.bc.webform.form.member.builder.FormMemberBuilderForDatabaseTable;
import com.bc.webform.functions.IsFormFieldTestForDatabaseTable;
import java.util.Objects;
import java.util.function.BiPredicate;
import javax.persistence.EntityManagerFactory;

/**
 * @author hp
 */
public class FormBuilderForDatabaseTable extends FormBuilderImpl<String, String, Object>{

    private final EntityManagerFactory entityManagerFactory;

    public FormBuilderForDatabaseTable(EntityManagerFactory entityManagerFactory) {
        
        this.entityManagerFactory = Objects.requireNonNull(entityManagerFactory);
        
        if(this.getSourceFieldsProvider() == null) {
            this.sourceFieldsProvider(
                    new IsFormFieldTestForDatabaseTable(entityManagerFactory));
        }
        
        if(this.getFormMemberBuilder() == null) {
            this.formMemberBuilder(
                    new FormMemberBuilderForDatabaseTable(entityManagerFactory));
        }
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
}
