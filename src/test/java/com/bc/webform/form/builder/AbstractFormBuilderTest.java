package com.bc.webform.form.builder;

import com.bc.webform.form.DefaultForm;
import com.bc.webform.form.Form;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * @author hp
 */
public abstract class AbstractFormBuilderTest<S, F, V> {
    
    public abstract FormBuilder<S, F, V> getInstance();
    
    public abstract void buildValid(FormBuilder<S, F, V> formBuilder);

    public abstract void buildNonValid(FormBuilder<S, F, V> formBuilder);

    @Test
    public void build_givenValidBuilder_shouldCompleteSuccessfully() {
        System.out.println("build_givenValidBuilder_shouldCompleteSuccessfully");
        final FormBuilder<S, F, V> instance = this.getInstance();
        this.buildValid(instance);
        instance.build();
    }

    @Test
    public void build_givenNonValidBuilder_shouldThrowException() {
        final String method = "build_givenNonValidBuilder_shouldThrowException";
        System.out.println(method);
        final FormBuilder<S, F, V> instance = this.getInstance();
        this.buildNonValid(instance);
        try{
            instance.build();
            fail(method + ", should fail but completed execution");
        }catch(Exception ignored) {}
    }

    @Test
    public void build_whenCalledTwice_shouldBeIdempotent() {
        try{
        System.out.println("build_whenCalledTwice_shouldBeIdempotent");
        FormBuilder<S, F, V> instance = this.getInstance();
        this.buildValid(instance);
        final Form expResult = instance.build();
        instance = this.getInstance();
        this.buildValid(instance);
        final Form result = instance.build();
        assertEquals(expResult, result);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getRandomFormName() {
        return "SampleForm";
    }

    public Form getDefaultForm() {
        return this.getDefaultForm(this.getRandomFormName());
    }
    
    public Form getDefaultForm(String name) {
        return new DefaultForm(name);
    }
}
