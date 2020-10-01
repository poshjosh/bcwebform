package com.bc.webform;

import com.bc.webform.choices.SelectOption;
import com.bc.webform.choices.SelectOptionBean;
import com.bc.webform.form.Form;
import com.bc.webform.form.FormBean;
import com.bc.webform.form.member.FormMember;
import com.bc.webform.form.member.FormMemberBean;

/**
 * @author hp
 */
public final class WebformUtil {
    
    private WebformUtil() {}
    
    public static <V> SelectOptionBean<V> toBean(SelectOption<V> option) {
        return option == null ? null :
                option instanceof SelectOptionBean ? (SelectOptionBean)option :
                new SelectOptionBean(option.getValue(), option.getText());
    }
    
    public static <F, V> FormMemberBean<F, V> toBean(FormMember<F, V> formMember) {
        return formMember == null ? null : 
                formMember instanceof FormMemberBean ? (FormMemberBean)formMember :
                new FormMemberBean(formMember);
    }

    public static <S> FormBean<S> toBean(Form<S> form) {
        return form == null ? null : 
                form instanceof FormBean ? (FormBean)form :
                new FormBean(form);
    }
}
