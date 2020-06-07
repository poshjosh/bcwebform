package com.bc.webform;

import com.bc.webform.functions.FormInputContextForPojo;
import com.bc.webform.functions.MultiChoiceContextForPojo;
import com.bc.webform.functions.TypeTests;
import com.bc.webform.functions.TypeTestsImpl;
import java.lang.reflect.Field;

/**
 * @author hp
 */
public class FormMemberBuilderForPojo extends FormMemberBuilderImpl<Object, Field, Object>{

    public FormMemberBuilderForPojo() { }

    @Override
    public FormMember<Field, Object> build() {
        
        if(this.getFormInputContext() == null) {
            this.formInputContext(new FormInputContextForPojo());
        }
        
        if(this.getMultipleInputTest() == null) {
            // By default fields do not accept multiple inputs
            this.multipleInputTest((formDataSource, dataSourceField) -> false);
        }
        
        if(this.getMultiChoiceContext() == null) {
            this.multiChoiceContext(new TypeTestsImpl());
        }
        
        return super.build(); 
    }

    public FormMemberBuilderForPojo multiChoiceContext(TypeTests typeTests) {
        this.multiChoiceContext(new MultiChoiceContextForPojo(typeTests));
        return this;
    }
}
