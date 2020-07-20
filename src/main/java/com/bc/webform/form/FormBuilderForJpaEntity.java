package com.bc.webform.form;

import com.bc.webform.form.member.FormMemberBuilderForJpaEntity;
import com.bc.webform.functions.IsFormFieldTestForJpaEntity;
import com.bc.webform.TypeTests;
import com.bc.webform.TypeTestsImpl;

/**
 * @author hp
 */
public class FormBuilderForJpaEntity extends FormBuilderForPojo{

    public FormBuilderForJpaEntity() {
        
        if(this.getFormMemberBuilder() == null) {
            this.formMemberBuilder(new FormMemberBuilderForJpaEntity());
        }
        
        if(this.getSourceFieldsProvider() == null) {
            this.sourceFieldsProvider(new TypeTestsImpl());
        }
    }

    public FormBuilderForJpaEntity sourceFieldsProvider(TypeTests typeTests) {
        this.sourceFieldsProvider(new IsFormFieldTestForJpaEntity(typeTests));
        return this;
    }
}
