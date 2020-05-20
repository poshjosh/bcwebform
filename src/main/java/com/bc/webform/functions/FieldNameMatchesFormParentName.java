package com.bc.webform.functions;

import com.bc.webform.Form;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.bc.webform.FormMember;

/**
 * @author hp
 */
public class FieldNameMatchesFormParentName implements FormFieldTest{
    
    private static final Logger LOG = Logger.getLogger(FieldNameMatchesFormParentName.class.getName());
    
    public FieldNameMatchesFormParentName() { }

    @Override
    public boolean test(FormMember formField) {
        
        final String formParentName = this.getFormParentName(formField);

        final boolean accept = formField.getName().equalsIgnoreCase(formParentName);

        final Level level = accept ? Level.FINE : Level.FINER;

        LOG.log(level, () -> "Form field: " + formField.getName() +
                " has a name matching it's parent form's name, i.e: " + formParentName);

        return accept;
    }
    
    public String getFormParentName(FormMember formField){
    
        final Form form = formField.getForm();
        
        final String formParentName = form.getParent() == null ? 
                null : form.getParent().getName();
        
        return formParentName;
    }
}
