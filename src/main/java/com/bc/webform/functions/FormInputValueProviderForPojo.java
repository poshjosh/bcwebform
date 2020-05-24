package com.bc.webform.functions;

import com.bc.reflection.ReflectionUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hp
 */
public class FormInputValueProviderForPojo
        implements FormInputValueProvider<Object, Field, Object>{
    
    private static final Logger LOG = Logger.getLogger(FormInputValueProviderForPojo.class.getName());

    @Override
    public Object getValue(Object declaringInstance, Field field) {
        
        Object value = this.getValueViaField(declaringInstance, field);
        if(value == null) {
            value = this.getValueViaMethod(declaringInstance, field);
        }
        final Class declaringClass = field.getDeclaringClass();
        if(LOG.isLoggable(Level.FINER)) {
            LOG.log(Level.FINER, "{0}.{1} = {2}", 
                    new Object[]{
                        declaringClass.getSimpleName(), field.getName(), value});
        }
        return value;
    }

    @Override
    public boolean setValue(Object declaringInstance, Field field, Object value) {
        boolean success = this.setValueViaField(declaringInstance, field, value);
        if( ! success) {
            success = this.setValueViaMethod(declaringInstance, field, value);
        }
        final Class declaringClass = field.getDeclaringClass();
        if(LOG.isLoggable(Level.FINER)) {
            LOG.log(Level.FINER, "Success: {0}, setting {1}.{2} = {3}", 
                    new Object[]{
                    success, declaringClass.getSimpleName(), field.getName(), value});
        }
        return success;
    }

    public Object getValueViaField(Object declaringInstance, Field field) {
        Object fieldValue = null;
        if(declaringInstance != null) {
            final boolean flag = field.isAccessible();
            try{
                if( ! flag) {
                    field.setAccessible(true);
                }
                fieldValue = field.get(declaringInstance);
                if(LOG.isLoggable(Level.FINER)) {
                    LOG.log(Level.FINER, "Retreived value: {0}, from field: {1}", 
                            new Object[]{fieldValue, field});
                }
            }catch(IllegalArgumentException | IllegalAccessException e) {
                LOG.log(Level.WARNING, "Failed to access value for field: " + field, e);
            }finally{
                if( ! flag) {
                    field.setAccessible(flag);
                }
            }
        }
        return fieldValue;
    }
    
    public boolean setValueViaField(Object declaringInstance, Field field, Object value) {
        boolean success = false;
        if(declaringInstance != null) {
            final boolean flag = field.isAccessible();
            try{
                if( ! flag) {
                    field.setAccessible(true);
                }
                field.set(declaringInstance, value);
                success = true;
                if(LOG.isLoggable(Level.FINER)) {
                    LOG.log(Level.FINER, "Success: {0}, setting {1}.{2} = {3}", 
                            new Object[]{
                            success, field.getDeclaringClass().getSimpleName(), field.getName(), value});
                }
            }catch(IllegalArgumentException | IllegalAccessException e) {
                LOG.log(Level.WARNING, "Failed to access value for field: " + field, e);
            }finally{
                if( ! flag) {
                    field.setAccessible(flag);
                }
            }
        }
        return success;
    }

    public Object getValueViaMethod(Object declaringInstance, Field field) {
        Object methodValue = null;
        if(declaringInstance != null) {
            final Class objectType = declaringInstance.getClass();
            final Method [] methods = this.getMethods(objectType);
            if(methods != null && methods.length > 0) {
                try{
                    methodValue = new ReflectionUtil()
                            .getValue(objectType, declaringInstance, methods, field.getName());
                }catch(RuntimeException ignored) { }
            }
            if(LOG.isLoggable(Level.FINER)) {
                LOG.log(Level.FINER, 
                        "Retreived from associated method, value: {0}, field: {1}", 
                        new Object[]{methodValue, field});
            }
        }
        return methodValue;
    }
    
    public boolean setValueViaMethod(Object declaringInstance, Field field, Object value) {
        boolean success = false;
        if(declaringInstance != null) {
            final Class objectType = declaringInstance.getClass();
            final Method [] methods = this.getMethods(objectType);
            if(methods != null && methods.length > 0) {
                try{
                    new ReflectionUtil()
                            .setValue(objectType, declaringInstance, methods, field.getName(), value);
                    success = true;
                }catch(RuntimeException ignored) { }
            }
            if(LOG.isLoggable(Level.FINER)) {
                LOG.log(Level.FINER, 
                        "Success: {0}, setting value: {1} of field: {2}", 
                        new Object[]{success, value, field});
            }
        }
        return success;
    }

    private transient Method [] _methods;
    public Method [] getMethods(Class type) {
        if(this._methods == null || this._methods.length == 0) {
            this._methods = type.getDeclaredMethods();
        }else{
            if( ! this._methods[0].getDeclaringClass().equals(type)) {
                this._methods = type.getDeclaredMethods();
            }
        }
        return _methods;
    }
}
