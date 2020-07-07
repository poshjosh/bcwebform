package com.bc.webform;

import com.bc.webform.form.member.FormMemberBean;
import com.bc.webform.form.member.FormMember;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author hp
 */
public class FormMemberBeanTest extends FieldSetTest<FormMemberBean>{
    
    private final TestData testData = new TestData();
    
    public FormMemberBeanTest() { 
        super(FormMemberBean.class);
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
        final FormMemberBean instance = newInstance();
        final FormMember expResult = instance.withValue(value);
        final FormMember result = instance.withValue(value);
        assertEquals(expResult, result);
        assertEquals(expResult, result.withValue(value));
    }

    @Test
    public void testCopy() {
        System.out.println("testCopy");
        this.testCopy(testData.newFormFieldBeanWithTestData(true));
    }
}
