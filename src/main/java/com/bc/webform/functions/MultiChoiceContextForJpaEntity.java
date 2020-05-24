package com.bc.webform.functions;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author hp
 */
public class MultiChoiceContextForJpaEntity implements MultiChoiceContext<Object, Field>{

    private final TypeTests typeTests;
    
    public MultiChoiceContextForJpaEntity(TypeTests typeTests) { 
        this.typeTests = Objects.requireNonNull(typeTests);
    }

    @Override
    public boolean isMultiChoice(Object source, Field field) {
        return typeTests.isEnumType(field.getType());
    }

    @Override
    public Map getChoices(Object source, Field field) {
        final Class fieldType = field.getType();
        if(this.typeTests.isEnumType(fieldType)) {
            return this.getEnumChoices(fieldType);
        }else{
            return null;
        }
    }
    
    public Map getEnumChoices(Class fieldType) {
        if(this.typeTests.isEnumType(fieldType)) {
            final Object [] enums = fieldType.getEnumConstants();
            if(enums != null) {
                final Map choices = new HashMap<>(enums.length, 1.0f);
                for(int i = 0; i<enums.length; i++) {
                    choices.put(i, ((Enum)enums[i]).name());
                }
                return Collections.unmodifiableMap(choices);
            }
        }
        return null;
    }

    public TypeTests getTypeTests() {
        return typeTests;
    }
}
