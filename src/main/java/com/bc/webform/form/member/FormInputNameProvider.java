package com.bc.webform.form.member;

/**
 * @author hp
 */
public interface FormInputNameProvider<S, F> {
 
    String getName(S dataSource, F dataSourceField);
}
