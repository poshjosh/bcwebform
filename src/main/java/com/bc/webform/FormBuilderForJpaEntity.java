package com.bc.webform;

import com.bc.webform.functions.IsFormFieldTestForJpaEntity;
import com.bc.webform.functions.TypeTests;
import com.bc.webform.functions.TypeTestsImpl;

/**
 * @author hp
 */
public class FormBuilderForJpaEntity extends FormBuilderForPojo{

    @Override
    public Form build() {
        
        if(this.getFormMemberBuilder() == null) {
            this.formMemberBuilder(new FormMemberBuilderForJpaEntity());
        }
        
        if(this.getSourceFieldsProvider() == null) {
            this.sourceFieldsProvider(new TypeTestsImpl());
        }
        
        return super.build(); 
    }

    public FormBuilderForJpaEntity sourceFieldsProvider(TypeTests typeTests) {
        this.sourceFieldsProvider(new IsFormFieldTestForJpaEntity(typeTests));
        return this;
    }
}
