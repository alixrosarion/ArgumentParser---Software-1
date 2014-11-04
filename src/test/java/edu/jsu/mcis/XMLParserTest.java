package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class XMLParserTest
{

	@Test
	public void testXMLParsingArguments()
	{
		XMLParser tester = new XMLParser("arguments.xml");
		try {
			tester.getArgumentParser().parse("2 2 2");
		} catch (Exception e) {
			assertTrue(false);
		}
		assertEquals(2.0f, tester.getArgumentValue("length"));
	}

	@Test
	public void testXMLParsingOptional()
	{
		XMLParser tester = new XMLParser("arguments.xml");
		try {
			tester.getArgumentParser().parse("2 2 2 -h -t sphere");
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testXMLParserTypes()
	{
		XMLParser tester = new XMLParser("argumentsType.xml");
		try {
			tester.getArgumentParser().parse("2 2 asd true");
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	
	@Test
	public void testXMLParserOptionalTypes()
	{
		XMLParser tester = new XMLParser("argumentsType.xml");
		try {
			tester.getArgumentParser().parse("2 2 asd true -h --type STRING --int 8 --float 2");
		} catch (Exception e) {
			assertTrue(false);
		}
		
		XMLParser testBad = new XMLParser("badFormatXML.xml");
		XMLParser testNo = new XMLParser("nonono.xml");
	}
}