package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentValuesTest{
	@Test
	public void testCorrectOrder(){
		ArgumentValues av = new ArgumentValues("t", "u", "b");
		assertEquals("t", av.getLength());
		assertEquals("u", av.getWidth());
		assertEquals("b", av.getHeight());
		
	}






}