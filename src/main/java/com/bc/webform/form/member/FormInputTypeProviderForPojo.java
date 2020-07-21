package com.bc.webform.form.member;

import com.bc.webform.StandardFormFieldTypes;
import com.bc.webform.functions.IsPasswordField;
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * @author hp
 */
public class FormInputTypeProviderForPojo 
        implements FormInputTypeProvider<Object, Field>{
    
    private static final Logger LOG = Logger.getLogger(FormInputTypeProviderForPojo.class.getName());

    private final String resultIfNone;
    
    private final Predicate<String> passwordFieldTest;

    public FormInputTypeProviderForPojo() {
        this(new IsPasswordField(), StandardFormFieldTypes.TEXT);
    }
    
    public FormInputTypeProviderForPojo(
            Predicate<String> passwordInputTest, String resultIfNone) {
        this.passwordFieldTest = Objects.requireNonNull(passwordInputTest);
        this.resultIfNone = resultIfNone;
    }

    @Override
    public String getDataType(Object formDataSource, Field dataSourceField) {
        return dataSourceField.getType().getSimpleName();
    }
    
    @Override
    public String getType(Object source, Field field) {
        final String result;
        final Class cls = field.getType();
        if(passwordFieldTest.test(field.getName())) {
            result = StandardFormFieldTypes.PASSWORD;  
        }else if(CharSequence.class.isAssignableFrom(cls)) {
            result = StandardFormFieldTypes.TEXT;
        }else if(cls == Boolean.class || cls == boolean.class) {
            result = StandardFormFieldTypes.CHECKBOX;
        }else if(LocalTime.class.isAssignableFrom(cls) || 
                OffsetTime.class.isAssignableFrom(cls) ||
                java.sql.Time.class.isAssignableFrom(cls)) {
            result = StandardFormFieldTypes.TIME;
        }else if(java.time.ZonedDateTime.class.isAssignableFrom(cls) ||
                java.time.LocalDateTime.class.isAssignableFrom(cls) ||
                java.time.OffsetDateTime.class.isAssignableFrom(cls) ||
                java.sql.Timestamp.class.isAssignableFrom(cls)){
            result = StandardFormFieldTypes.DATETIME;
        }else if(java.util.Date.class.isAssignableFrom(cls) ||
                java.time.LocalDate.class.isAssignableFrom(cls)){
            result = StandardFormFieldTypes.DATE;
        }else if(Number.class.isAssignableFrom(cls)) {
            result = StandardFormFieldTypes.NUMBER;
        }else{
            result = null;
        }
        
        LOG.finer(() -> "Input: " + cls + ", output: " + result);
        
        return result == null ? resultIfNone : result;
    }
}
