package com.bc.webform.functions;

import java.util.function.Predicate;

/**
 * @author hp
 */
public class IsEnumType implements Predicate<Class>{

    @Override
    public boolean test(Class type) {
//        return type.getAnnotation(Enumerated.class) != null || type.isEnum();
        return type.isEnum();
    }
}
