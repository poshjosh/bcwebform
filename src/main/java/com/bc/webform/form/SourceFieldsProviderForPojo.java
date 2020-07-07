package com.bc.webform.form;

import com.bc.webform.form.Form;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author hp
 */
public class SourceFieldsProviderForPojo implements SourceFieldsProvider<Object, Field>{

    private static final Logger LOG = Logger.getLogger(SourceFieldsProviderForPojo.class.getName());
    
    private final Predicate<Field> isFormField;
    
    /**
     * How far up the class inheritance hierarchy to check for form fields
     */
    private final int maxLevelOfClassHierarchyToCheckForFields;

    public SourceFieldsProviderForPojo() {
        this((field) -> true, -1);
    }
    
    public SourceFieldsProviderForPojo(
            Predicate<Field> isFormField, 
            int maxLevelOfClassHierarchyToCheckForFields) {
        this.isFormField = Objects.requireNonNull(isFormField);
        this.maxLevelOfClassHierarchyToCheckForFields = maxLevelOfClassHierarchyToCheckForFields;
    }
    
    @Override
    public Set<Field> apply(Form form, Object source) {

        final Set<Field> result = new LinkedHashSet<>();
        
        Class objectType = source.getClass();
        
        int depth = 0;
        
        while( ! Object.class.equals(objectType) && 
                this.isWithinMaxLevelOfClassHierarchyToCheckForFields(depth)) {
        
            ++depth;
            
            if(LOG.isLoggable(Level.FINE)) {
                
                final int d = depth;
                
                LOG.log(Level.FINE, () -> "Depth: " + d + ", object: " + 
                        source.getClass().getSimpleName() + 
                        ", form: name=" + form.getName() + ", parent name=" + 
                        (form.getParent()==null?null:form.getParent().getName()));            
            }
            
            final Field [] fields = objectType.getDeclaredFields();

            LOG.log(Level.FINE, "Declared fields: {0}", (Arrays.toString(fields)));
            
            result.addAll(
                    Arrays.asList(fields).stream()
                            .filter(isFormField).collect(Collectors.toSet()));
            
            objectType = objectType.getSuperclass();
        }
        
        return Collections.unmodifiableSet(result);
    }

    public boolean isWithinMaxLevelOfClassHierarchyToCheckForFields(int level) {
        return (maxLevelOfClassHierarchyToCheckForFields < 0 || 
                level < maxLevelOfClassHierarchyToCheckForFields);
    }
}
