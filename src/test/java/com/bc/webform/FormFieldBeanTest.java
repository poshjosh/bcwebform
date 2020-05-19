package com.bc.webform;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author hp
 */
public class FormFieldBeanTest extends FieldSetTest<FormFieldBean>{
    
    private final TestData testData = new TestData();
    
    public FormFieldBeanTest() { 
        super(FormFieldBean.class);
    }

    @Test
    public void isAnyFieldSet_givenAFieldIsSet_ShouldReturnTrue() {
        System.out.println("isAnyFieldSet_givenAFieldIsSet_ShouldReturnTrue");
        this.isAnyFieldSet_givenAFieldIsSet_ShouldReturnTrue("name", "SampleFormField");
    }

    @Test
    public void withValue_ShouldBeIdempotent() {
        System.out.println("withValue");
        final Object value = new Object();
        final FormFieldBean instance = newInstance();
        final FormField expResult = instance.withValue(value);
        final FormField result = instance.withValue(value);
        assertEquals(expResult, result);
        assertEquals(expResult, result.withValue(value));
    }

    @Test
    public void testCopy() {
        System.out.println("testCopy");
        this.testCopy(testData.newFormFieldBeanWithTestData(true));
    }
}
