package com.bc.webform;

import com.bc.webform.functions.IsContainerType;
import com.bc.webform.functions.IsDomainType;
import com.bc.webform.functions.IsEnumType;
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
    
    public TypeTests withContainerTest(Predicate<Class> isContainerType) {
        return new TypeTestsImpl(isContainerType, this.domainTypeTest, this.enumTypeTest);
    }

    public TypeTests withDomainTest(Predicate<Class> isDomainType) {
        return new TypeTestsImpl(this.containerTypeTest, isDomainType, this.enumTypeTest);
    }
    
    public TypeTests withEnumTest(Predicate<Class> isEnumType) {
        return new TypeTestsImpl(this.containerTypeTest, this.domainTypeTest, isEnumType);
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
