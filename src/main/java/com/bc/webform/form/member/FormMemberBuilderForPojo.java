package com.bc.webform.form.member;

import com.bc.webform.TypeTests;
import com.bc.webform.TypeTestsImpl;
import java.lang.reflect.Field;

/**
 * @author hp
 */
public class FormMemberBuilderForPojo extends FormMemberBuilderImpl<Object, Field, Object>{

    public FormMemberBuilderForPojo() { 
    
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
    }

    public FormMemberBuilderForPojo multiChoiceContext(TypeTests typeTests) {
        this.multiChoiceContext(new MultiChoiceContextForPojo(typeTests));
        return this;
    }
}
