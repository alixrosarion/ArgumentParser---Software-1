package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class XMLParserTest
{

	@Test
	public void testXMLRequired()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("arguments.xml");
		try {
			tester.parse("2 2 5");
			assertTrue(false);
		} catch (Exception e) { e.printStackTrace();
		}
	}

	@Test
	public void testRestricted()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("arguments.xml");
		System.out.println(tester.checkRestricted("height"));
		// I AM NOT TESTING SHIT IN HERE JUST STRYING TO UNDERSTAND WHY ITS NOT WORKING
	}
	
	@Test
	public void testXMLParsingArguments()
	{
		ArgumentParser tester = XMLParser.createArgumentParser("arguments.xml");
		try {
			tester.parse("2 2 5 --req a");
		} catch (Exception e) { e.printStackTrace();
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
		} catch (Exception e) { e.printStackTrace();
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
	}

}