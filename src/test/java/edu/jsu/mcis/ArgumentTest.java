package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentTest
{

	@Test
	public void testArgumentToString()
	{
		Argument tester = new Argument("length", CommandLineArgument.DataType.Integer);
		tester.setDescription("the length of the box");
		assertEquals("\r\n\t<argument>\r\n\t\t<name>length</name>\r\n\t\t<type>Integer</type>\r\n\t\t<description>the length of the box</description>\r\n\t</argument>", tester.toString());
	}
	
	@Test
	public void testOverloadedConstructor()
	{
		Argument tester = new Argument("length", CommandLineArgument.DataType.Integer);
		tester.setDescription("the height of the box");
		assertEquals("length", tester.getTitle());
		assertEquals(CommandLineArgument.DataType.Integer, tester.getDataType());
		assertEquals("the height of the box", tester.getDescription());
	}
	
	@Test
	public void testEquals()
	{
		Argument tester = new Argument("length");
		Argument tester1 = new Argument("length");
		assertEquals(tester, tester1);
	}
	
	@Test
	public void testHashCode()
	{
		Argument tester = new Argument("length");
		Argument tester1 = new Argument("length");
		assertEquals(tester.hashCode(), tester1.hashCode());
	}
	
	@Test
	public void testGetShort()
	{
		Argument tester = new Argument("length");
		assertEquals("", tester.getShort());
	}
	
	@Test
	public void testGetNumValues()
	{
		Argument tester = new Argument("length");
		assertEquals(0, tester.getNumberValues());
	}
}