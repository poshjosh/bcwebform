package com.bc.webform.form.member;

import com.bc.webform.TypeTests;
import com.bc.webform.choices.SelectOption;
import com.bc.webform.choices.SelectOptions;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import javax.persistence.EnumType;

/**
 * @author hp
 */
public class MultiChoiceContextForPojo implements MultiChoiceContext<Object, Field>{

    private final TypeTests typeTests;
    private final EnumType enumType;
    private final BiFunction<Enum, Locale, Object> printer;
    private final Locale locale;

    public MultiChoiceContextForPojo(TypeTests typeTests) {
        this(typeTests, EnumType.STRING, null, Locale.getDefault());
    }
    
    public MultiChoiceContextForPojo(
            TypeTests typeTests,
            EnumType enumType,
            BiFunction<Enum, Locale, Object> printer, 
            Locale locale) {
        this.typeTests = Objects.requireNonNull(typeTests);
        this.enumType = Objects.requireNonNull(enumType);
        this.printer = printer;
        this.locale = Objects.requireNonNull(locale);
    }

    @Override
    public boolean isMultiChoice(Object source, Field field) {
        return typeTests.isEnumType(field.getType());
    }
    
    @Override
    public List<SelectOption> getChoices(Object source, Field field) {
        final Class fieldType = field.getType();
        if(this.typeTests.isEnumType(fieldType)) {
            return this.getEnumChoices(fieldType);
        }else{
            return null;
        }
    }
    
    public List<SelectOption> getEnumChoices(Class type) {
        if(this.typeTests.isEnumType(type)) {
            BiFunction format = this.printer;
            switch(enumType) {
                case STRING:
                    return SelectOptions.fromEnumName(type, locale, format);
                case ORDINAL:
                    return SelectOptions.fromEnumOrdinal(type, locale, format);
                default:
                    throw new IllegalArgumentException("Unexpected javax.persistence.EnumType: "  + enumType);
            }        
        }
        return Collections.EMPTY_LIST;
    }

    public TypeTests getTypeTests() {
        return typeTests;
    }
}
