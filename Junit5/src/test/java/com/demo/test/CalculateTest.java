package com.demo.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.demo.bean.Calculate;

@RunWith(JUnitPlatform.class)
public class CalculateTest {
	
	@Test
	public void testAdd() {
		Calculate cal=new Calculate();
		int actual=cal.add(10,30);
		int expected=40;
		assertEquals(expected,actual);
	}
}
