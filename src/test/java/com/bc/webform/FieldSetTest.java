package com.bc.webform;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import static junit.framework.Assert.fail;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author hp
 */
public abstract class FieldSetTest<T extends FieldSet> {
    
    private static final class InstanceSupplier<C extends FieldSet> implements Supplier<C>{
        private final Class<C> type;
        public InstanceSupplier(Class<C> type) {
            this.type = Objects.requireNonNull(type);
        }
        @Override
        public C get() {
            try{
                return type.newInstance();
            }catch(IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final TestData testData = new TestData();
    
    private final Supplier<T> instanceSupplier;
    
    public FieldSetTest(Class<T> type) {
        this(new InstanceSupplier<T>(type));
    }
    
    public FieldSetTest(Supplier<T> instanceSupplier) { 
        this.instanceSupplier = Objects.requireNonNull(instanceSupplier);
    }

    public T newInstance() {
        return this.instanceSupplier.get();
    }
    
    @Test
    public void isAnyFieldSet_givenNoFieldIsSet_ShouldReturnFalse() {
        System.out.println("isAnyFieldSet_givenNoFieldIsSet_ShouldReturnFalse");
        final T instance = newInstance();
        final boolean expResult = false;
        final boolean result = instance.isAnyFieldSet();
        assertEquals(expResult, result);
    }

    public void isAnyFieldSet_givenAFieldIsSet_ShouldReturnTrue(
            String fieldName, Object fieldValue) {
        System.out.println("isAnyFieldSet_givenAFieldIsSet_ShouldReturnTrue("+fieldName+", "+fieldValue+")");
        T instance = newInstance();
        this.setFieldValue(instance, fieldName, fieldValue);
        final boolean expResult = true;
        final boolean result = instance.isAnyFieldSet();
        assertEquals(expResult, result);
    }
    
    @Test
    public void checkRequiredFieldsAreSet_givenNoFieldSet_ShowThrowException() {
        final String method = "checkRequiredFieldsAreSet_givenNoFieldSet_ShowThrowException";
        System.out.println(method);
        final T instance = newInstance();
        try{
            instance.checkRequiredFieldsAreSet();
            fail(method + " should fail, but completed execution");
        }catch(RuntimeException ignored) { }
    }

    @Test
    public void copy_ShouldReturnInstanceEqualToOriginal() {
        System.out.println("methodCopy_ShouldReturnInstanceEqualToOriginal");
        final FieldSet instance = newInstance();
        final FieldSet expResult = instance;
        final FieldSet result = instance.copy();
        assertEquals(expResult, result);
    }
    
    public void testCopy(T instance) {
        final FieldSet copy = instance.copy(); 
        final Method [] methods = instance.getClass().getMethods();
        final Predicate<Method> methodIsGetter = new com.bc.reflection.function.MethodIsGetter();
        for(Method method : methods) {
            if(methodIsGetter.test(method)) {
                try{
                    final Object lhs = method.invoke(instance);
                    final Object rhs = method.invoke(copy);
                    if(!Objects.deepEquals(lhs, rhs)) {
                        fail("Copy failed for " + instance.getClass().getSimpleName() + 
                        ". Return value from method inconsistent between original and copy. Method: " + method.getName());
                    }
                }catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    
    public void setFieldValue(Object source, String name, Object value) {
        try{
            final Field field = source.getClass().getDeclaredField(name);
            final boolean flag = field.isAccessible();
            try{
                if(!flag) {
                    field.setAccessible(true);
                }
                field.set(source, value);
            }finally{
                field.setAccessible(flag);
            }
        }catch(IllegalAccessException | IllegalArgumentException | 
                NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
