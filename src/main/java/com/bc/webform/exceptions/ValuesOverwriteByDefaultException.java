package com.bc.webform.exceptions;

/**
 * @author hp
 */
public class ValuesOverwriteByDefaultException extends ValuesOverwriteException{

    public ValuesOverwriteByDefaultException() { }

    public ValuesOverwriteByDefaultException(Object instance) {
        this("applyDefaults should be the first method called. Calling the method at this time will override already set values\n" + instance);
    }

    public ValuesOverwriteByDefaultException(String msg) {
        super(msg);
    }
    
}
