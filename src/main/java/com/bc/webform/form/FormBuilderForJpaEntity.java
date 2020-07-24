package com.bc.webform.form;

import com.bc.webform.form.member.FormMemberBuilderForJpaEntity;
import com.bc.webform.functions.IsFormFieldTestForJpaEntity;
import com.bc.webform.TypeTests;
import com.bc.webform.TypeTestsImpl;
import com.bc.webform.form.member.FormMemberBuilder;
import java.lang.reflect.Field;

/**
 * @author hp
 */
public class FormBuilderForJpaEntity extends FormBuilderForPojo{

    public FormBuilderForJpaEntity() {
        this(new FormMemberBuilderForJpaEntity(), new TypeTestsImpl());
    }
    
    public FormBuilderForJpaEntity(
            FormMemberBuilder<Object, Field, Object> formMemberBuilder,
            TypeTests typeTests) {
        
        this.formMemberBuilder(formMemberBuilder);
        
        this.sourceFieldsProvider(typeTests);
    }

    public FormBuilderForJpaEntity sourceFieldsProvider(TypeTests typeTests) {
        this.sourceFieldsProvider(new IsFormFieldTestForJpaEntity(typeTests));
        return this;
    }
}
