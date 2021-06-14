package com.bc.webform;

/**
 * @author hp
 * @param <T> The actual type
 */
public interface FieldSet<T extends FieldSet> {
    
    boolean isAnyFieldSet();
    
    void checkRequiredFieldsAreSet();
    
    T copy();
}
