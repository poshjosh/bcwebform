package com.bc.webform.functions;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author hp
 */
public class TypeTestsImpl implements TypeTests{
    
    private final Predicate<Class> containerTypeTest;
    private final Predicate<Class> domainTypeTest;
    private final Predicate<Class> enumTypeTest;

    public TypeTestsImpl() {
        this(new IsContainerType(), new IsDomainType(), new IsEnumType());
    }

    public TypeTestsImpl(
            Predicate<Class> isContainerType, 
            Predicate<Class> isDomainType, 
            Predicate<Class> isEnumType) {
        this.containerTypeTest = Objects.requireNonNull(isContainerType);
        this.domainTypeTest = Objects.requireNonNull(isDomainType);
        this.enumTypeTest = Objects.requireNonNull(isEnumType);
    }

    @Override
    public boolean isContainerType(Class type) {
        return this.containerTypeTest.test(type);
    }

    @Override
    public boolean isDomainType(Class type) {
        return this.domainTypeTest.test(type);
    }
    
    @Override
    public boolean isEnumType(Class type) {
        return this.enumTypeTest.test(type);
    }

    public Predicate<Class> getContainerTypeTest() {
        return containerTypeTest;
    }

    public Predicate<Class> getDomainTypeTest() {
        return domainTypeTest;
    }

    public Predicate<Class> getEnumTypeTest() {
        return enumTypeTest;
    }
}
