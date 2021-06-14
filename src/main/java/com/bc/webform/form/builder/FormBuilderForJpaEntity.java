package com.bc.webform.form.builder;

import com.bc.webform.form.member.builder.FormMemberBuilderForJpaEntity;
import com.bc.webform.functions.IsFormFieldTestForJpaEntity;
import com.bc.webform.TypeTests;
import com.bc.webform.TypeTestsImpl;

/**
 * @author hp
 */
public class FormBuilderForJpaEntity extends FormBuilderForPojo{

    public FormBuilderForJpaEntity() { 
        this(new TypeTestsImpl());
    }
    
    public FormBuilderForJpaEntity(TypeTests typeTests) {
        this.sourceFieldsProvider(typeTests);
    }

    public FormBuilderForJpaEntity sourceFieldsProvider(TypeTests typeTests) {
        this.sourceFieldsProvider(new IsFormFieldTestForJpaEntity(typeTests));
        return this;
    }

    @Override
    protected void preBuild() {
        
        if(this.getFormMemberBuilderProvider() == null) {
            
            this.formMemberBuilderProvider(() -> new FormMemberBuilderForJpaEntity());
        }
        
        super.preBuild();
    }
}
