package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;

public class ArgumentParserTest {
	private ArgumentParser tester;
	
	@Before
	public void startUp()
	{
		tester = new ArgumentParser();
	}
	
	@Test
	public void testAddMultipleArguments()
	{
		assertEquals(0, tester.getSize());
		tester.addArgument("length");
		assertEquals(1, tester.getSize());
		tester.addArgument("width");
		assertEquals(2, tester.getSize());
	}
	
	@Test
	public void testParseSingleArgument() {
		tester.addArgument("length");
		tester.addArgument("width");
		try
		{
		tester.parse("7 2");
			assertEquals("7", tester.getArgumentValue("length"));
			assertEquals("2", tester.getArgumentValue("width"));
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){}
		
	}
	
	@Test
	public void testParseVolCalc() {
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		try
		{
			tester.parse("7 5 2");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
		assertEquals("7", tester.getArgumentValue("length"));
		assertEquals("5", tester.getArgumentValue("width"));
		assertEquals("2", tester.getArgumentValue("height"));
	}
	
	@Test
	public void testTooManyArgumentValues() {
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		try
		{
			tester.parse("7 5 2 10 2");
			assertTrue(false);
		}catch(TooManyArgValuesException | NotEnoughArgValuesException | IncorrectTypeException e){
			assertEquals("unrecognised arguments: 10 2", tester.getUnmatched());
		}
	}
	
	@Test
	public void testNotEnoughArgumentValues() {
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		try
		{
			tester.parse("7");
			assertTrue(false);
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertEquals("the following arguments are required: width height", tester.getUnmatched());
		}
	}
	
	@Test
	public void testAddArgTypes()
	{
		tester.addArgument("length", CommandLineArgument.Type.Integer);
		tester.addArgument("width", CommandLineArgument.Type.Float);
		assertEquals(CommandLineArgument.Type.Integer, tester.getArgumentType("length"));
		assertEquals(CommandLineArgument.Type.Float, tester.getArgumentType("width"));
	}
	
	@Test
	public void testAddArgDescriptions()
	{
		tester.addArgument("length", CommandLineArgument.Type.Integer);
		tester.addArgument("width", CommandLineArgument.Type.Float);
		assertEquals("the length of the box", tester.getArgumentDescription("length"));
		assertEquals("the width of the box", tester.getArgumentDescription("width"));
	}
	
	@Test
	public void testOptionText(){
		tester.addProgram("VolCalc","Calculate the volume of a box");
		tester.addArgument("length", CommandLineArgument.Type.Integer);
		tester.addArgument("width",CommandLineArgument.Type.Float);
		tester.addArgument("height",CommandLineArgument.Type.Float);
		tester.addDescription("length", "the length of the box");
		tester.addDescription("width", "the width of the box");
		tester.addDescription("height","the height of the box");
		tester.addOptionalArgument("-h", CommandLineArgument.Type.Boolean);
		try
		{
			tester.parse("-h");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);}
		assertEquals("usage: java VolCalc length width height \r\nCalculate the volume of a box\r\nPositional Arguments:\r\nlength integer\t\tthe length of the box\r\nwidth float\t\tthe width of the box\r\nheight float\t\tthe height of the box\r\n", tester.getHelpText());
	}
	
	@Test
	public void testTypeParsing()
	{
		tester.addArgument("length", CommandLineArgument.Type.Integer);
		tester.addArgument("width",CommandLineArgument.Type.Float);
		tester.addArgument("rainy", CommandLineArgument.Type.Boolean);
		tester.addArgument("dog", CommandLineArgument.Type.String);
		try
		{
			tester.parse("7 5.2 true someString");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
		assertEquals(7, tester.getArgumentValue("length"));
		assertEquals(5.2f, tester.getArgumentValue("width"));
		assertEquals(true, tester.getArgumentValue("rainy"));
		assertEquals("someString", tester.getArgumentValue("dog"));
	}
	
	@Test
	public void testCatchWrongTypeForInteger()
	{
		tester.addArgument("length", CommandLineArgument.Type.Integer);
		try
		{
			tester.parse("something");
			assertTrue(false);
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
		}
	}
	
	@Test
	public void testCatchWrongTypeForFloat()
	{
		tester.addArgument("length", CommandLineArgument.Type.Float);
		try
		{
			tester.parse("something");
			assertTrue(false);
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
		}
	}
	
	@Test
	public void testCatchWrongTypeForBoolean()
	{
		tester.addArgument("length", CommandLineArgument.Type.Boolean);
		try
		{
			tester.parse("something");
			assertTrue(false);
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
		}
	}
	
	@Test
	public void testReadingOptionalArgumentAtEnd ()
	{
		tester.addArgument("length", CommandLineArgument.Type.Integer);
		tester.addOptionalArgument("--type", CommandLineArgument.Type.String);
		assertEquals("--type", tester.getOptionalArgument("--type"));
		try
		{
			tester.parse("7 --type sphere");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentValues(){
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addOptionalArgument("--type", CommandLineArgument.Type.String);
		try
		{
			tester.parse("7 --type sphere 5 2");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
		assertEquals("sphere", tester.getArgumentValue("--type"));
	}
	
	@Test
	public void testReadingMoreOptionalArguments(){
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addOptionalArgument("--type", CommandLineArgument.Type.String);
		tester.addOptionalArgument("-h", CommandLineArgument.Type.Boolean);
		tester.addOptionalArgument("--actor", CommandLineArgument.Type.String);
		try
		{
			tester.parse("7 --type sphere 5 -h 2 --actor will smith");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentsWithDefaults(){
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addOptionalArgument("--type", CommandLineArgument.Type.String);
		try
		{
			tester.parse("7 5 2");
			assertEquals("Box", tester.getArgumentValue("--type"));
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentAsFlag() {
		tester.addOptionalArgument("--type", CommandLineArgument.Type.String);
		boolean flag = true;
		try {
			tester.parse("--type");
			assertEquals(true, tester.getArgumentValue("--type"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentOverrideDefaultValue() {
		tester.addOptionalArgument("--type", CommandLineArgument.Type.String);
		//tester.setNumberValues(1);
		try {
			tester.parse("--type shape");
			assertEquals("shape", tester.getArgumentValue("--type"));
		} catch (Exception e) {
			assertTrue(false);
		}

	}
	
	@Test
	public void testOptionalArgumentDescription() {
		tester.addOptionalArgument("--type", CommandLineArgument.Type.String);
		try {
			tester.parse("--type shape");
			assertEquals("The shape the user defines", tester.getDescription("--type"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testShortName()
	{
		tester.addOptionalArgument("--type", CommandLineArgument.Type.String);
		tester.addShortOption("--type", "-t");
		assertEquals("-t", tester.getShortOption("--type"));
		try {
			tester.parse("-t shape");
			assertEquals("The shape the user defines", tester.getDescription("-t"));
			assertEquals("The shape the user defines", tester.getDescription("--type"));
		} catch (Exception e) {
			assertTrue(false);
		}

	}
	
	@Test
	public void testAddProgram()
	{
		tester.addProgram("Volcalc", "Calculates volume of an object");
		assertEquals("Volcalc", tester.getProgramName());
		assertEquals("Calculates volume of an object", tester.getProgramDescription());
	}
	
	@Test
	public void testOutputXML()
	{
		tester.addArgument("length", CommandLineArgument.Type.Integer);
		tester.addDescription("length", "the length of the box");
		tester.addOptionalArgument("--type", CommandLineArgument.Type.String);
		assertEquals("<?xml version=\"1.0\" encoding=\""+ "UTF-8" + "\"?>\r\n<arguments>\r\n\t<argument>\r\n\t\t<name>length</name>"+
		"\r\n\t\t<type>Integer</type>\r\n\t\t<description>the length of the box</description>\r\n\t</argument>\r\n\t<optionalArgument>\r\n\t\t"+
		"<name>--type</name>\r\n\t\t<numValues>0</numValues>\r\n\t\t<type></type>\r\n\t\t<description></description>"+	
		"\r\n\t\t<value>" + null + "</value>\r\n\t\t<shortName></shortName>\r\n\t</optionalArgument>\r\n</arguments>",tester.getOutput());
	}

	@Test
	public void testXMLFileWriting()
	{
		tester.addArgument("length", CommandLineArgument.Type.Integer);
		tester.addDescription("length", "the length of the box");
		tester.addOptionalArgument("--type", CommandLineArgument.Type.String);
		tester.writeToXMLFile("test.xml");
		assertTrue(new File("test.xml").exists());
		
		ArgumentParser testNo = XMLParser.createArgumentParser("test.xml");
		assertEquals("<?xml version=\"1.0\" encoding=\""+ "UTF-8" + "\"?>\r\n<arguments>\r\n\t<argument>\r\n\t\t<name>length</name>"+
		"\r\n\t\t<type>Integer</type>\r\n\t\t<description>the length of the box</description>\r\n\t</argument>\r\n\t<optionalArgument>\r\n\t\t"+
		"<name>--type</name>\r\n\t\t<numValues>0</numValues>\r\n\t\t<type></type>\r\n\t\t<description></description>"+	
		"\r\n\t\t<value>" + null + "</value>\r\n\t\t<shortName></shortName>\r\n\t</optionalArgument>\r\n</arguments>",tester.getOutput());
	}
}