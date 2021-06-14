package com.bc.webform.form;

import com.bc.webform.form.Form;
import com.bc.webform.form.member.FormMember;
import com.bc.webform.form.member.FormMember;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hp
 */
public class FormMemberTypeMatchesParentFormType 
        extends FormMemberNameMatchesParentFormName<Field, Object>{
    
    private static final Logger LOG = Logger
            .getLogger(FormMemberTypeMatchesParentFormType.class.getName());

    @Override
    public boolean test(FormMember<Field, Object> formMember) {
        
        final Class parentFormType = this.getParentFormType(formMember, null);

        final Class formMemberType = formMember.getDataSource() == null ? null :
                formMember.getDataSource().getType();
        
        final boolean accept = parentFormType != null && 
                parentFormType.equals(formMemberType);

        final Level level = accept ? Level.FINE : Level.FINEST;

        LOG.log(level, () -> "Form field: " + formMember.getName() +
                " has a type " + formMemberType + 
                " which matches it's parent form's type, i.e: " + parentFormType);

        return accept;
    }

    public Class getParentFormType(FormMember formMember, Class resultIfNone){
    
        final Form form = formMember.getForm();
        
        final Object dataSource = form.getParent() == null ? 
                null : form.getParent().getDataSource();
        
        return dataSource == null ? resultIfNone : dataSource.getClass();
    }
}
