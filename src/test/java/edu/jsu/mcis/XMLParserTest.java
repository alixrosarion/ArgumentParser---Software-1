package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class XMLParserTest
{

	@Test
	public void testXMLMultipleValues()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("argumentsMultipleValues.xml");
		try {
			tester.parse("2 2 3 4 5");
			assertTrue(false);
		} catch (Exception e) { //e.printStackTrace();
		}
	}
	
	@Test
	public void testXMLMutualGroups()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("arguments.xml");
		try {
			tester.parse("2 2 2 --type a --boss a --req l");
			assertTrue(false);
		} catch (Exception e) { 
		}
	}
	
	@Test
	public void testXMLRequired()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("arguments.xml");
		try {
			tester.parse("2 2 5");
			assertTrue(false);
		} catch (Exception e) { //e.printStackTrace();
		}
	}

	@Test
	public void testRestricted()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("arguments.xml");
		assertEquals("3.0 2.0 5.0",tester.checkRestricted("height"));
		try {
			tester.parse("2 2 5 --req a b");
		} catch (Exception e) { assertTrue(false);
		}
	}
	
	@Test
	public void testXMLParsingArguments()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("arguments.xml");
		try {
			tester.parse("2 2 5 --req a c");
		} catch (Exception e) { //e.printStackTrace();
			assertTrue(false);
		}
		assertEquals(2.0f, tester.getValue("length"));
	}
	
	@Test
	public void testXMLHelp()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("arguments.xml");
		try {
			tester.parse("--help");
		} catch (Exception e) {
			assertTrue(false);
		}
	}
		
	@Test
	public void testXMLParsingOptional()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("arguments.xml");
		try {
			tester.parse("2 2 2 -h -t sphere");
		} catch (Exception e) { //e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void testXMLParserTypes()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("argumentsType.xml");
		try {
			tester.parse("2 2 asd true");
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testXMLParserOptionalTypes()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("argumentsType.xml");
		try {
			tester.parse("2 2 asd true -h --type STRING --int 8 --float 2");
		} catch (Exception e) {
			assertTrue(false);
		}
		
		ArgumentParser testBad = XMLParser.createArgumentParser("badFormatXML.xml");
		ArgumentParser testNo = XMLParser.createArgumentParser("nonono.xml");
	}
	
	@Test
	public void testRequiredOptionalArguments()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("arguments.xml");
		try {
			tester.parse("2");
			assertTrue(false);
		} catch (NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e) {
		}
	}
}