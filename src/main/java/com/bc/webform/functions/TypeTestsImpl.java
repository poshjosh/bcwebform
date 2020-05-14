package com.bc.webform.functions;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author hp
 */
public class TypeTestsImpl implements TypeTests{
    
    private final Predicate<Class> isContainerType;
    private final Predicate<Class> isDomainType;
    private final Predicate<Class> isEnumType;

    public TypeTestsImpl() {
        this(new IsContainerType(), new IsDomainType(), new IsEnumType());
    }

    public TypeTestsImpl(
            Predicate<Class> isContainerType, 
            Predicate<Class> isDomainType, 
            Predicate<Class> isEnumType) {
        this.isContainerType = Objects.requireNonNull(isContainerType);
        this.isDomainType = Objects.requireNonNull(isDomainType);
        this.isEnumType = Objects.requireNonNull(isEnumType);
    }

    @Override
    public boolean isContainerType(Class type) {
        return this.isContainerType.test(type);
    }

    @Override
    public boolean isDomainType(Class type) {
        return this.isDomainType.test(type);
    }
    
    @Override
    public boolean isEnumType(Class type) {
        return this.isEnumType.test(type);
    }
}
