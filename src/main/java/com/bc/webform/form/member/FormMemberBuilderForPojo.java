package com.bc.webform.form.member;

import com.bc.webform.TypeTestsImpl;
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
