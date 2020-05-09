package com.bc.webform.functions;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * @author hp
 */
public class IsContainerField implements Predicate<Field>{

    private static final Logger LOG = Logger.getLogger(IsContainerField.class.getName());
    
    @Override
    public boolean test(Field field) {
        
        final Class type = field.getType();
        
        final boolean output = Collection.class.isAssignableFrom(type) || 
                Map.class.isAssignableFrom(type) ||
                Object[].class.isAssignableFrom(type);
        
        LOG.finer(() -> "Multivalue: " + output + ", field type: " + type + ", field: " + field);
        
        return output;
    }
}
