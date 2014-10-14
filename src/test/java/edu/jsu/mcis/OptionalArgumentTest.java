package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class OptionalArgumentTest
{
	@Test
	public void testSetOptionalArgument()
	{
		OptionalArgument tester = new OptionalArgument("--type", 0);
		assertEquals("--type", tester.getTitle());
	}
	
	@Test
	public void testTypeSingleValue()
	{
		OptionalArgument tester = new OptionalArgument("--type", 1, "String", "Shape of Volume", "Box");
		assertEquals("--type", tester.getTitle());
		assertEquals(1, tester.getNumValues());
		assertEquals("String", tester.getType());
		assertEquals("Shape of Volume", tester.getDescription());
		assertEquals("Box", tester.getDefaultValue());
	}
}