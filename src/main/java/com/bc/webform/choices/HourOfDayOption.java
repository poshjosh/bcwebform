package com.bc.webform.choices;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an HTML form select option for an hour in a 24 hour day.
 * @author chinomso ikwuagwu
 */
public final class HourOfDayOption implements SelectOption<Integer>, Serializable{
    
    private final Integer hour;
    
    public HourOfDayOption(Integer hour) {
        this.hour = Objects.requireNonNull(hour);
        if(hour < 0) {
            throw new IllegalArgumentException("hour < 0");
        }
        if(hour >= 24) {
            throw new IllegalArgumentException("hour >= 24");
        }
    }
    
    @Override
    public String getText() {
        final String suffix = hour < 12 ? " AM" : hour > 12 ? " PM" : " Noon";
        return hour + suffix;
    }

    @Override
    public Integer getValue() {
        return hour;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.hour);
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
        final HourOfDayOption other = (HourOfDayOption) obj;
        if (!Objects.equals(this.hour, other.hour)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
