package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class OptionalArgumentTest
{
	@Test
	public void testSetOptionalArgument()
	{
		OptionalArgument tester = new OptionalArgument("--type");
		assertEquals("--type", tester.getTitle());
	}
	
	@Test
	public void testTypeSingleValue()
	{
		OptionalArgument tester = new OptionalArgument("--type", 1, CommandLineArgument.Type.String, "Shape of Volume", "Box");
		assertEquals("--type", tester.getTitle());
		assertEquals(1, tester.getNumValues());
		assertEquals(CommandLineArgument.Type.String, tester.getType());
		assertEquals("Shape of Volume", tester.getDescription());
		assertEquals("Box", tester.getValue());
	}
	
	@Test
	public void testShortName()
	{
		OptionalArgument tester = new OptionalArgument("--type");
		tester.setShort("-t");
		assertEquals("-t", tester.getShort());
	}
	
	@Test
	public void testEquals()
	{
		OptionalArgument tester = new OptionalArgument("--type");
		OptionalArgument tester1 = new OptionalArgument("--type");
		tester.setShort("-t");
		assertEquals(tester, tester1);
	}
}