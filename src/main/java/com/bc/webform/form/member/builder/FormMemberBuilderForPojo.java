package com.bc.webform.form.member.builder;

import com.bc.webform.TypeTestsImpl;
import com.bc.webform.form.member.FormInputContext;
import com.bc.webform.form.member.FormInputContextForPojo;
import com.bc.webform.form.member.MultiChoiceContext;
import com.bc.webform.form.member.MultiChoiceContextForPojo;
import java.lang.reflect.Field;

/**
 * @author hp
 */
public class FormMemberBuilderForPojo extends FormMemberBuilderImpl<Object, Field, Object>{

    public FormMemberBuilderForPojo() { 
        this(new FormInputContextForPojo(), new MultiChoiceContextForPojo(new TypeTestsImpl()));
    }
    
    public FormMemberBuilderForPojo(
            FormInputContext<Object, Field, Object> formInputContext,
            MultiChoiceContext<Object, Field> multiChoiceContext) { 
    
        this.formInputContext(formInputContext);
        
        // By default fields do not accept multiple inputs
        this.multipleInputTest((formDataSource, dataSourceField) -> false);
        
        this.multiChoiceContext(multiChoiceContext);
    }
}
