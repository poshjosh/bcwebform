package com.bc.webform.functions;

/**
 * @author hp
 */
public interface IsReferencedTypeField<S, F> {

    boolean isReferencedType(S formDataSource, F dataSourceField);
}

