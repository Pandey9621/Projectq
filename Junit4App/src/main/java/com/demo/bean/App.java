package com.demo.bean;

/**
 * Hello world!
 *
 */
public class App 
{  
	static int a=10;
    public static void main( String[] args )
    {
 
      System.out.println("result "+a);
    }
    public void m1() {
    	App a=new App();
    	System.out.println(a.a);
    }
}
