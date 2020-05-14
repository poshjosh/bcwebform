package com.bc.webform.functions;

/**
 * @author hp
 */
public interface TypeTests{
    boolean isContainerType(Class type);
    boolean isEnumType(Class type); 
    boolean isDomainType(Class type);        
}
