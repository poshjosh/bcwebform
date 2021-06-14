package com.bc.webform.form;

import com.bc.webform.FieldSetTest;
import com.bc.webform.TestData;
import com.bc.webform.form.FormBean;
import org.junit.Test;

/**
 * @author hp
 */
public class FormBeanTest extends FieldSetTest<FormBean>{
    
    private final TestData testData = new TestData();
    
    public FormBeanTest() { 
        super(FormBean.class);
    }

    @Test
    public void isAnyFieldSet_givenAFieldIsSet_ShouldReturnTrue() {
        System.out.println("isAnyFieldSet_givenAFieldIsSet_ShouldReturnTrue");
        this.isAnyFieldSet_givenAFieldIsSet_ShouldReturnTrue("name", "SampleForm");
    }

    @Test
    public void testCopy() {
        System.out.println("testCopy");
        this.testCopy(testData.newFormBeanWithTestData());
    }
}
