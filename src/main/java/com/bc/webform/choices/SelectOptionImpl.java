package com.bc.webform.choices;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author hp
 */
public final class SelectOptionImpl<V> implements SelectOption<V>, Serializable{
    
    private final V value;
    private final String text;

    public SelectOptionImpl(V value, String textToDisplay) {
        this.value = Objects.requireNonNull(value);
        this.text = Objects.requireNonNull(textToDisplay);
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SelectOptionImpl<?> other = (SelectOptionImpl<?>) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
