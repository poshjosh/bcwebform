package com.bc.webform.functions;

import com.bc.webform.domain.Blog;
import java.util.Collection;
import org.junit.Test;

/**
 * @author hp
 */
public class IsContainerTypeTest extends TypeTestBase<IsContainerType>{
    
    public IsContainerTypeTest() { 
        super(() -> new IsContainerType());
    }

    @Test
    public void test_givenNonContainerType_ShouldReturnFalse() {
        System.out.println("test_givenNonContainerType_ShouldReturnFalse");
        test_givenType_ShouldReturn(Blog.class, false);
    }

    @Test
    public void test_givenValidContainerType_ShouldReturnTrue() {
        System.out.println("test_givenValidContainerType_ShouldReturnTrue");
        test_givenType_ShouldReturn(Collection.class, true);
    }
}
