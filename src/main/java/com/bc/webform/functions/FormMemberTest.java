package com.bc.webform.functions;

import java.util.function.Predicate;
import com.bc.webform.FormMember;

/**
 * @author hp
 */

@FunctionalInterface
public interface FormMemberTest extends Predicate<FormMember>{
 
    @Override
    boolean test(FormMember formField);
}
