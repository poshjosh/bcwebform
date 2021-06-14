package com.bc.webform.choices;

/**
 * Represents an HTML form select option.
 * @author chinomso ikwuagwu
 */
public interface SelectOption<V> {
    String getText();
    V getValue();
}
