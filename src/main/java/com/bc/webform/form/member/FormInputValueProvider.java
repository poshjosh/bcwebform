package com.bc.webform.form.member;

/**
 * @author hp
 */
public interface FormInputValueProvider<S, F, V> {
    
    /**
     * Return a value from the field, for the specified data source.
     * 
     * <b>Note</b> If you return an instance of {@link com.bc.webform.choices.SelectOption}
     * then the returned value is used to populated a single item list of choices
     * for the related {@link com.bc.webform.form.member.FormMember}.
     * 
     * @param dataSource The source whose field a value is to be returned
     * @param dataSourceField The field whose value is to be returned
     * @return 
     */
    V getValue(S dataSource, F dataSourceField);
    
    boolean setValue(S dataSource, F dataSourceField, V fieldValue);
}
