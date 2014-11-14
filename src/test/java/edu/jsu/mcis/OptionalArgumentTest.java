package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class OptionalArgumentTest
{

	@Test
	public void testOptionalArgumentToString()
	{
		OptionalArgument tester = new OptionalArgument("type");
		assertEquals("\r\n\t<optionalArgument>\r\n\t\t<name>type</name>\r\n\t\t<numValues>0</numValues>\r\n\t\t<type>String</type>\r\n\t\t<description></description>"+
		"\r\n\t\t<value>" + null + "</value>\r\n\t\t<shortName></shortName>\r\n\t</optionalArgument>", tester.toString());
	}

	@Test
	public void testSetOptionalArgument()
	{
		OptionalArgument tester = new OptionalArgument("type");
		assertEquals("type", tester.getTitle());
	}
	
	@Test
	public void testDataTypeSingleValue()
	{
		OptionalArgument tester = new OptionalArgument("type", CommandLineArgument.DataType.String);
		tester.setNumberValues(1);
		tester.setDescription("Shape of Volume");
		tester.setValue("Box");
		assertEquals("type", tester.getTitle());
		assertEquals(1, tester.getNumberValues());
		assertEquals(CommandLineArgument.DataType.String, tester.getDataType());
		assertEquals("Shape of Volume", tester.getDescription());
		assertEquals("Box", tester.getValue());
	}
	
	@Test
	public void testShortName()
	{
		OptionalArgument tester = new OptionalArgument("type");
		tester.setShort("t");
		tester.setValue("Box");
		assertEquals("t", tester.getShort());
		assertEquals(tester, new OptionalArgument("t"));

	}
	
	@Test
	public void testEquals()
	{
		OptionalArgument tester = new OptionalArgument("type");
		OptionalArgument tester1 = new OptionalArgument("type");
		assertEquals(tester, tester1);
	}
	
	@Test
	public void testRequired()
	{
		OptionalArgument tester = new OptionalArgument("type");
		tester.setRequired();
		assertTrue(tester.getRequired());
	}

}