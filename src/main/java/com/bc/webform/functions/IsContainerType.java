package com.bc.webform.functions;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * @author hp
 */
public class IsContainerType implements Predicate<Class>{

    private static final Logger LOG = Logger.getLogger(IsContainerType.class.getName());
    
    @Override
    public boolean test(Class type) {
        
        final boolean output = Collection.class.isAssignableFrom(type) || 
                Map.class.isAssignableFrom(type) ||
                Object[].class.isAssignableFrom(type);
        
        LOG.finer(() -> "Container type: " + output + ", type: " + type);
        
        return output;
    }
}
