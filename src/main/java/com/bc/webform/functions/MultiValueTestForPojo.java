package com.bc.webform.functions;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author hp
 */
public class MultiValueTestForPojo implements IsMultiValueField<Object, Field>{

    private final TypeTests typeTests;

    public MultiValueTestForPojo(TypeTests typeTests) {
        this.typeTests = Objects.requireNonNull(typeTests);
    }
    
    @Override
    public boolean isMultiValue(Object formDataSource, Field dataSourceField) {
        return this.typeTests.isContainerType(dataSourceField.getDeclaringClass());
    }

    public TypeTests getTypeTests() {
        return typeTests;
    }
}
