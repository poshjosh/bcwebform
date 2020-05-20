package com.bc.webform;

import com.bc.webform.domain.Blog;
import java.lang.reflect.Field;

/**
 * @author hp
 */
public class FormBuilderForJpaEntityTest 
        extends AbstractFormBuilderTest<Object, Field, Object>{

    @Override
    public FormBuilder<Object, Field, Object> getInstance() {
        return new FormBuilderForJpaEntity();
    }

    @Override
    public void buildNonValid(FormBuilder<Object, Field, Object> builder) {
        this.buildValid(builder);
        builder.formDataSource(null);
    }

    @Override
    public void buildValid(FormBuilder<Object, Field, Object> builder) {
        builder.applyDefaults(this.getRandomFormName());
        builder.formDataSource(new Blog());
    }
}
