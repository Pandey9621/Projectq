package com.demo.bean.test;
import static org.junit.Assert.assertEquals;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.demo.bean.Calculate;

public class CalculateTest {
	
	private static Calculate cal=null;
	@BeforeClass
	public static void init() {
		cal=new Calculate();
	}
	@AfterClass
	public static void destroy() {
	 cal=null;	
	}
	@Test
	public void testAdd() {
		int actual=cal.add(10,30);
		int expected=40;
		assertEquals(expected,actual);
	}
	@Test
     public void testMul() {
    	 int actual=cal.mul(2,3);
    	 int expected=6;
    	 assertEquals(expected, actual);
     }
	@Test
     public void testSub() {
		int actual=cal.sub(5,3);
    	 int expected=2;
    	 assertEquals(expected, actual);
     }
	@Test
     public void testDiv() {
    	int actual=cal.div(5,5);
    	int expected=1;
    	assertEquals(expected, actual);
     }
}
