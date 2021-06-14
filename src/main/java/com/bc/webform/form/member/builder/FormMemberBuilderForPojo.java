package com.bc.webform.form.member.builder;

import com.bc.webform.TypeTestsImpl;
import com.bc.webform.form.member.FormInputContextForPojo;
import com.bc.webform.form.member.MultiChoiceContextForPojo;
import java.lang.reflect.Field;

/**
 * @author hp
 */
public class FormMemberBuilderForPojo extends FormMemberBuilderImpl<Object, Field, Object>{

    public FormMemberBuilderForPojo() { }

    @Override
    protected void preBuild() {

        if(this.getFormInputContext() == null) {
            this.formInputContext(new FormInputContextForPojo());
        }
        
        if(this.getMultipleInputTest() == null) {
            this.multipleInputTest((formDataSource, dataSourceField) -> false);
        }

        if(this.getMultiChoiceContext() == null) {
            this.multiChoiceContext(new MultiChoiceContextForPojo(new TypeTestsImpl()));
        }

        super.preBuild();
    }
}
