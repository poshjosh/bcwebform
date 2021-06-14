package com.bc.webform.form;

import java.util.function.Predicate;
import com.bc.webform.form.member.FormMember;
import com.bc.webform.form.member.FormMember;

/**
 * @author hp
 */

@FunctionalInterface
public interface FormMemberTest<F, V> extends Predicate<FormMember<F, V>>{
 
    @Override
    boolean test(FormMember<F, V> formField);
}
