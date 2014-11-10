package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class OptionalArgumentTest
{

	@Test
	public void testOptionalArgumentToString()
	{
		OptionalArgument tester = new OptionalArgument("--type");
		assertEquals("\r\n\t<optionalArgument>\r\n\t\t<name>--type</name>\r\n\t\t<numValues>0</numValues>\r\n\t\t<type></type>\r\n\t\t<description></description>"+	
		"\r\n\t\t<value>" + null + "</value>\r\n\t\t<shortName></shortName>\r\n\t</optionalArgument>", tester.toString());
	}

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
		assertEquals(1, tester.getNumberValues());
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