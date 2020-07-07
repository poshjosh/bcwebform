package com.bc.webform.form;

import com.bc.webform.form.member.FormMemberBuilderForPojo;
import java.lang.reflect.Field;
import java.util.function.Predicate;

/**
 * @author hp
 */
public class FormBuilderForPojo extends FormBuilderImpl<Object, Field, Object>{
    
    private static class AcceptAll implements Predicate<Field>{
        @Override
        public boolean test(Field field) {
            return true;
        }
    }

    @Override
    public Form build() {
        
        if(this.getFormMemberTest() == null) {
            this.formMemberTest(new FormMemberTypeMatchesParentFormType().negate());
        }
        
        if(this.getFormMemberBuilder() == null) {
            this.formMemberBuilder(new FormMemberBuilderForPojo());
        }
        
        if(this.getSourceFieldsProvider() == null) {
            this.sourceFieldsProvider(new AcceptAll());
        }
        
        return super.build();
    }

    public FormBuilderForPojo sourceFieldsProvider(Predicate<Field> isFormField) {
        return this.sourceFieldsProvider(isFormField, -1);
    }
    
    /**
     * @param isFormField
     * @param maxLevelOfClassHierarchyToCheckForFields How far up the class 
     * inheritance hierarchy to check for form fields
     * @return 
     */
    public FormBuilderForPojo sourceFieldsProvider(
            Predicate<Field> isFormField,
            int maxLevelOfClassHierarchyToCheckForFields) {
        
        this.sourceFieldsProvider(new SourceFieldsProviderForPojo(
                isFormField, maxLevelOfClassHierarchyToCheckForFields
        ));
        
        return this;
    }
}
