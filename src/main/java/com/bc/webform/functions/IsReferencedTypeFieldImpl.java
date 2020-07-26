package com.bc.webform.functions;

import com.bc.webform.TypeTests;
import com.bc.webform.form.Form;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hp
 */
public class IsReferencedTypeFieldImpl implements IsReferencedTypeField<Object, Field> {
    
    private static final Logger LOG = Logger.getLogger(IsReferencedTypeFieldImpl.class.getName());
    
    private final TypeTests typeTests;

    public IsReferencedTypeFieldImpl(TypeTests typeTests) {
        this.typeTests = Objects.requireNonNull(typeTests);
    }
    
    @Override
    public boolean isReferencedType(Form<Object> form, Field field) {
        final boolean output;
        final Class fieldType = field.getType();
        if(typeTests.isEnumType(fieldType)) {
            output = false;
        }else if(typeTests.isDomainType(fieldType)){
            output = true;
        }else{
            output = false;
        }
        LOG.log(Level.FINER, "Field: {0}, is reference type: {1}",
                new Object[]{field.getName(), output});
        return output;
    }
}
