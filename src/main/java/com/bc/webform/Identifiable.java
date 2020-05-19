package com.bc.webform;

/**
 * @author hp
 */
public interface Identifiable {
    
    String getId();
    
    String getName();
    
    /**
     * Alias for {@link #getLabel() }
     * @return The display name
     * @see #getLabel() 
     */
    default String getDisplayName() {
        return this.getLabel();
    }
    
    String getLabel();

}
