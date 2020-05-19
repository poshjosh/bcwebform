package com.bc.webform.functions;

import com.bc.webform.domain.Blog;
import com.bc.webform.domain.BlogType;
import org.junit.Test;

/**
 * @author hp
 */
public class IsEnumTypeTest extends TypeTestBase<IsEnumType>{
    
    public IsEnumTypeTest() { 
        super(() -> new IsEnumType());
    }

    @Test
    public void test_givenNonEnumType_ShouldReturnFalse() {
        System.out.println("test_givenNonEnumType_ShouldReturnFalse");
        test_givenType_ShouldReturn(Blog.class, false);
    }

    @Test
    public void test_givenValidEnumType_ShouldReturnTrue() {
        System.out.println("test_givenValidEnumType_ShouldReturnTrue");
        test_givenType_ShouldReturn(BlogType.class, true);
    }
}

