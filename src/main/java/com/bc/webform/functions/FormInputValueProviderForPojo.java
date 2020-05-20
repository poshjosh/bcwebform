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
        
        Object value = this.getValueFromField(declaringInstance, field);
        if(value == null) {
            value = this.getValueFromMethod(declaringInstance, field);
        }
        final Class declaringClass = field.getDeclaringClass();
        if(LOG.isLoggable(Level.FINER)) {
            LOG.log(Level.FINER, "{0}.{1} = {2}", 
                    new Object[]{
                        declaringClass.getSimpleName(), field.getName(), value});
        }
        return value;
    }

    public Object getValueFromField(Object declaringInstance, Field field) {
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
    
    public Object getValueFromMethod(Object declaringInstance, Field field) {
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
