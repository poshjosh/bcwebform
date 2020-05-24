package com.bc.webform;

import com.bc.webform.functions.FormInputContextForPojo;
import com.bc.webform.functions.MultiValueTestForPojo;
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
        
        if(this.getMultiValueTest() == null) {
            this.multiValueTest(new TypeTestsImpl());
        }
        
        return super.build(); 
    }
    
    public FormMemberBuilderForPojo multiValueTest(TypeTests typeTests) {
        this.multiValueTest(new MultiValueTestForPojo(typeTests));
        return this;
    }
}
