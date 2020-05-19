package com.bc.webform.exceptions;

/**
 * @author hp
 */
public class ValuesOverwriteException extends IllegalStateException {

    /**
     * Creates a new instance of <code>ValuesOverwriteException</code> without
     * detail message.
     */
    public ValuesOverwriteException() {
    }

    /**
     * Constructs an instance of <code>ValuesOverwriteException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ValuesOverwriteException(String msg) {
        super(msg);
    }
}
