package com.bc.web.form;

import com.bc.webform.FormBean;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Predicate;
import static junit.framework.Assert.fail;
import org.junit.Test;

/**
 * @author hp
 */
public class FormBeanTest {
    
    private final TestData testData = new TestData();
    
    @Test
    public void testCopy() {
        final FormBean instance = testData.newFormBeanWithTestData();
        final FormBean copy = instance.copy();
        final Method [] methods = instance.getClass().getMethods();
        final Predicate<Method> methodIsGetter = new com.bc.reflection.function.MethodIsGetter();
        for(Method method : methods) {
            if(methodIsGetter.test(method)) {
                try{
                    final Object lhs = method.invoke(instance);
                    final Object rhs = method.invoke(copy);
                    if(!Objects.deepEquals(lhs, rhs)) {
                        fail("Copy failed for FormBean. Return value from method inconsistent for copies. Method: " + method.getName());
                    }
                }catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }
            
        }
    }
    
    public FormBean newInstance() {
        return new FormBean();
    }
}
