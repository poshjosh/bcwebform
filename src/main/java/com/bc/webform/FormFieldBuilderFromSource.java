package com.bc.webform;

/**
 * 
 * @author hp
 * @param <S> The type of the data source for the Form
 * @param <F> The type of the fields of the data source for the Form
 * @param <V> The type of value each field in the form holds
 */
public interface FormFieldBuilderFromSource<S, F, V> extends Builder<FormField<V>>{

    @Override
    FormField<V> build();

    @Override
    FormFieldBuilderFromSource<S, F, V> apply(FormField<V> t);

    FormFieldBuilderFromSource<S, F, V> field(F field);

    FormFieldBuilderFromSource<S, F, V> form(Form form);
    
    @Override
    FormFieldBuilderFromSource<S, F, V> reset();

    FormFieldBuilderFromSource<S, F, V> source(S source);
}
