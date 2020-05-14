package com.bc.webform.functions;

import java.util.function.Predicate;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author hp
 */
public class IsDomainType implements Predicate<Class>{
    
    public boolean test(Class type) {
        return type.getAnnotation(Entity.class) != null || type.getAnnotation(Table.class) != null;
    }
}
