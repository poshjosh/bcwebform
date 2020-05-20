package com.bc.webform.functions;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author hp
 */
public class FormInputContextForPojo implements FormInputContext<Object, Field, Object>{
    
    private final FormInputTypeProvider<Object, Field> formInputTypeProvider;
    
    private final FormInputValueProvider<Object, Field, Object> formInputValueProvider;

    public FormInputContextForPojo() {
        this(new FormInputTypeProviderForPojo(), new FormInputValueProviderForPojo());
    }

    public FormInputContextForPojo(
            FormInputTypeProvider<Object, Field> formInputTypeProvider, 
            FormInputValueProvider<Object, Field, Object> formInputValueProvider) {
        this.formInputTypeProvider = Objects.requireNonNull(formInputTypeProvider);
        this.formInputValueProvider = Objects.requireNonNull(formInputValueProvider);
    }

    @Override
    public boolean isOptional(Object formDataSource, Field dataSourceField) {
        return false;
    }

    @Override
    public String getType(Object formDataSource, Field dataSourceField) {
        return this.formInputTypeProvider.getType(formDataSource, dataSourceField);
    }

    @Override
    public String getName(Object dataSource, Field dataSourceField) {
        return dataSourceField.getName();
    }

    @Override
    public Object getValue(Object dataSource, Field dataSourceField) {
        return this.formInputValueProvider.getValue(dataSource, dataSourceField);
    }
}
