package com.bc.webform.choices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author hp
 */
public final class SelectOptions {
    
    private SelectOptions() {}
    
    public static final List<SelectOption<Integer>> fromEnumOrdinal(Class<Enum> enumType) {
        return fromEnumOrdinal(enumType, Locale.getDefault(), null);
    }

    public static final List<SelectOption<Integer>> fromEnumOrdinal(
            Class<Enum> enumType, Locale locale, BiFunction<Enum, Locale, String> printer) {
        requireEnumType(enumType);
        final Enum [] enums = enumType.getEnumConstants();
        final List<SelectOption<Integer>> options = new ArrayList<>(enums.length);
        for(Enum en : enums) {
            options.add(toEnumOrdinalSelectOption(en, locale, printer));
        }
        return Collections.unmodifiableList(options);
    }

    public static final List<SelectOption<String>> fromEnumName(Class<Enum> enumType) {
        return fromEnumName(enumType, Locale.getDefault(), null);
    }

    public static final List<SelectOption<String>> fromEnumName(
            Class<Enum> enumType, Locale locale, BiFunction<Enum, Locale, String> printer) {
        requireEnumType(enumType);
        final Enum [] enums = enumType.getEnumConstants();
        final List<SelectOption<String>> options = new ArrayList<>(enums.length);
        for(Enum en : enums) {
            options.add(toEnumNameSelectOption(en, locale, printer));
        }
        return Collections.unmodifiableList(options);
    }
    
    private static void requireEnumType(Class<Enum> enumType) {
        if( ! enumType.isEnum()) {
            throw new IllegalArgumentException("Not an enum type: " + enumType);
        }
    }
    
    public static <V> List<SelectOption<V>> from(List<V> values) {
        return from(values, Locale.getDefault(), null);
    }
    
    public static <V> List<SelectOption<V>> from(
            List<V> values, Locale locale, BiFunction<V, Locale, String> printer) {
        return values.stream()
                .map((value) -> toSelectOption(value, locale, printer))
                .collect(Collectors.toList());
    }
    
    private static <V> SelectOption<V> toSelectOption(
            V value, Locale locale, BiFunction<V, Locale, String> printer) {
        return from(value, print(value, locale, printer));
    }

    private static SelectOption<String> toEnumNameSelectOption(
            Enum value, Locale locale, BiFunction<Enum, Locale, String> printer) {
        return from(value.name(), print(value, locale, printer));
    }
    
    private static SelectOption<Integer> toEnumOrdinalSelectOption(
            Enum value, Locale locale, BiFunction<Enum, Locale, String> printer) {
        return from(value.ordinal(), print(value, locale, printer));
    }

    private static <V> String print(V value, Locale locale, BiFunction<V, Locale, String> printer) {
        Objects.requireNonNull(value);
        return printer == null ? value.toString() : printer.apply(value, locale);
    }

    public static <V> SelectOption<V> from(V value, String displayValue) {
        return new SelectOptionImpl<>(value, displayValue);
    }
}
