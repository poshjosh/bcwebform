package com.bc.webform.form;

import com.bc.webform.form.Form;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.bc.webform.form.member.FormMember;
import com.bc.webform.form.member.FormMember;

/**
 * @author hp
 */
public class FormMemberNameMatchesParentFormName<F, V> implements FormMemberTest<F, V>{
    
    private static final Logger LOG = Logger.getLogger(FormMemberNameMatchesParentFormName.class.getName());
    
    public FormMemberNameMatchesParentFormName() { }

    @Override
    public boolean test(FormMember<F, V> formMember) {
        
        final String formParentName = this.getParentFormName(formMember);

        final boolean accept = formMember.getName().equalsIgnoreCase(formParentName);

        final Level level = accept ? Level.FINE : Level.FINEST;

        LOG.log(level, () -> "Form field: " + formMember.getName() +
                " has a name matching it's parent form's name, i.e: " + formParentName);

        return accept;
    }
    
    public String getParentFormName(FormMember formField){
    
        final Form form = formField.getForm();
        
        final String formParentName = form.getParent() == null ? 
                null : form.getParent().getName();
        
        return formParentName;
    }
}
