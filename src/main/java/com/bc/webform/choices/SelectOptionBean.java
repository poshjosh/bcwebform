package com.bc.webform.choices;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author hp
 */
public class SelectOptionBean<V> implements SelectOption<V>, Serializable{
    
    private V value;
    private String text;

    public SelectOptionBean() { }
    
    @Override
    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        final SelectOptionBean<?> other = (SelectOptionBean<?>) obj;
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
