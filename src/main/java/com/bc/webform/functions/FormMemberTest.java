package com.bc.webform.functions;

import java.util.function.Predicate;
import com.bc.webform.FormMember;

/**
 * @author hp
 */

@FunctionalInterface
public interface FormMemberTest<F, V> extends Predicate<FormMember<F, V>>{
 
    @Override
    boolean test(FormMember<F, V> formField);
}
