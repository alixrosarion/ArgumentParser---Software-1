package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class XMLParserTest
{

	/*@Test
	public void testXMLParsingArguments()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("arguments.xml");
		try {
			tester.parse("2 2 2");
		} catch (Exception e) {
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
		} catch (Exception e) {
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
		e.printStackTrace();
		}
	}*/

}