package core;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddServer {
	@BeforeClass
    public static void BeforeClass() {
        System.out.println("Before Class");
    }

    @Before
    public void before() {
        System.out.println("Before");
    }

    @Test
    public void test() {
        System.out.println("Test");
    }

    @After
    public void after() {
        System.out.println("After");
    }

    @AfterClass
    public static void AfterTest() {
        System.out.println("AfterClass");
    }
}
