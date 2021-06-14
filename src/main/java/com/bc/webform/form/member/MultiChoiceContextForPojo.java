package com.bc.webform.form.member;

import com.bc.webform.TypeTests;
import com.bc.webform.choices.SelectOption;
import com.bc.webform.choices.SelectOptions;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author hp
 */
public class MultiChoiceContextForPojo implements MultiChoiceContext<Object, Field>{
    
    private static class Printer implements BiFunction<Object, Locale, String>{
        @Override
        public String apply(Object entity, Locale locale) {
            // toString will return null for generic enums
            if(entity instanceof Enum) {
                return entity.toString() != null ? entity.toString() : ((Enum)entity).name();
            }else{
                return String.valueOf(entity);
            }
        }
    }

    private final TypeTests typeTests;
    private final BiFunction<Object, Locale, String> printer;
    private final Locale locale;

    public MultiChoiceContextForPojo(TypeTests typeTests) {
        this(typeTests, new Printer(), Locale.getDefault());
    }
    
    public MultiChoiceContextForPojo(
            TypeTests typeTests,
            BiFunction<Object, Locale, String> printer, 
            Locale locale) {
        this.typeTests = Objects.requireNonNull(typeTests);
        this.printer = Objects.requireNonNull(printer);
        this.locale = Objects.requireNonNull(locale);
    }

    @Override
    public boolean isMultiChoice(Object source, Field field) {
        return typeTests.isEnumType(field.getType()) || typeTests.isDomainType(field.getType());
    }
    
    @Override
    public List<SelectOption> getChoices(Object source, Field field, Object fieldValue) {
        final Class fieldType = field.getType();
        if(this.typeTests.isEnumType(fieldType)) {
            return this.getEnumChoices(source, field, fieldValue);
        }else if(this.typeTests.isDomainType(fieldType)){
            return this.getObjectChoices(source, field, fieldValue);
        }else{
            return null;
        }
    }
    
    public List<SelectOption> getEnumChoices(Object source, Field field, Object fieldValue) {
        final Class type = field.getType();
        if(this.typeTests.isEnumType(type)) {
            BiFunction format = this.printer;
            return SelectOptions.fromEnumOrdinal(type, locale, format);
        }
        throw new IllegalArgumentException();
    }

    public List<SelectOption> getObjectChoices(Object source, Field field, Object fieldValue) {
        final Class fieldType = field.getType();
        if(this.typeTests.isDomainType(fieldType)){
            return this.getFieldValueChoice(source, field, fieldValue)
                    .map((option) -> Collections.singletonList(option))
                    .orElse(Collections.EMPTY_LIST);
        }
        throw new IllegalArgumentException();
    }
    
    public Optional<SelectOption> getFieldValueChoice(
            Object source, Field field, Object fieldValue) {
        final Class fieldType = field.getType();
        if(this.typeTests.isDomainType(fieldType)) {
            if(fieldValue != null) {
                SelectOption option = SelectOptions.from(fieldValue, printer.apply(fieldValue, locale));
                return Optional.of(option);
            }else{
                return Optional.empty();
            }
        }
        throw new IllegalArgumentException();
    }
    
    public TypeTests getTypeTests() {
        return typeTests;
    }

    public BiFunction<Object, Locale, String> getPrinter() {
        return printer;
    }

    public Locale getLocale() {
        return locale;
    }
}
