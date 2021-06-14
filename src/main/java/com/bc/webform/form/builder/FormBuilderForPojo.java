package com.bc.webform.form.builder;

import com.bc.webform.form.FormMemberTypeMatchesParentFormType;
import com.bc.webform.form.SourceFieldsProviderForPojo;
import com.bc.webform.form.member.builder.FormMemberBuilderForPojo;
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
    protected void preBuild() {
        
        if(this.getFormMemberTest() == null) {
            this.formMemberTest(new FormMemberTypeMatchesParentFormType().negate());
        }
        
        if(this.getFormMemberBuilderProvider() == null) {
            this.formMemberBuilderProvider(() -> new FormMemberBuilderForPojo());
        }
        
        if(this.getSourceFieldsProvider() == null) {
            this.sourceFieldsProvider(new AcceptAll());
        }
        
        super.preBuild();
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
