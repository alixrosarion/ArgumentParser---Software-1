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
			assertEquals("7", tester.getValue("length"));
			assertEquals("2", tester.getValue("width"));
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
		assertEquals("7", tester.getValue("length"));
		assertEquals("5", tester.getValue("width"));
		assertEquals("2", tester.getValue("height"));
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
	public void testAddArgDataTypes()
	{
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.addArgument("width", CommandLineArgument.DataType.Float);
		assertEquals(CommandLineArgument.DataType.Integer, tester.getArgumentDataType("length"));
		assertEquals(CommandLineArgument.DataType.Float, tester.getArgumentDataType("width"));

	}
	
	@Test
	public void testAddArgDescriptions()
	{
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.addArgument("width", CommandLineArgument.DataType.Float);
		tester.setDescription("length", "the length of the box");
		tester.setDescription("width", "the width of the box");
		assertEquals("the length of the box", tester.getDescription("length"));
		assertEquals("the width of the box", tester.getDescription("width"));

	}
	
	@Test
	public void testOptionText(){
		tester.addProgram("VolCalc","Calculate the volume of a box");
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.addArgument("width",CommandLineArgument.DataType.Float);
		tester.addArgument("height",CommandLineArgument.DataType.Float);
		tester.setDescription("length", "the length of the box");
		tester.setDescription("width", "the width of the box");
		tester.setDescription("height","the height of the box");

		tester.addOptionalArgument("-h", CommandLineArgument.DataType.Boolean);
		try
		{
			tester.parse("-h");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);}
		assertEquals("usage: java VolCalc length width height \r\nCalculate the volume of a box\r\nPositional Arguments:\r\nlength integer\t\tthe length of the box\r\nwidth float\t\tthe width of the box\r\nheight float\t\tthe height of the box\r\n\r\nOptional Arguments:\r\n-h boolean\t\t\r\n", tester.getHelpText());
	}
	
	@Test
	public void testDataTypeParsing()
	{
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.addArgument("width",CommandLineArgument.DataType.Float);
		tester.addArgument("rainy", CommandLineArgument.DataType.Boolean);
		tester.addArgument("dog", CommandLineArgument.DataType.String);
		try
		{
			tester.parse("7 5.2 true someString");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
		assertEquals(7, tester.getValue("length"));
		assertEquals(5.2f, tester.getValue("width"));
		assertEquals(true, tester.getValue("rainy"));
		assertEquals("someString", tester.getValue("dog"));
	}
	
	@Test
	public void testCatchWrongDataTypeForInteger()
	{
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		try
		{
			tester.parse("something");
			assertTrue(false);
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
		}
	}
	
	@Test
	public void testCatchWrongDataTypeForFloat()
	{
		tester.addArgument("length", CommandLineArgument.DataType.Float);
		try
		{
			tester.parse("something");
			assertTrue(false);
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
		}
	}
	
	@Test
	public void testCatchWrongDataTypeForBoolean()
	{
		tester.addArgument("length", CommandLineArgument.DataType.Boolean);
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
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.addOptionalArgument("--type", CommandLineArgument.DataType.String);
		assertEquals("--type", tester.getOptionalArgument("--type"));
		tester.setNumberValues("--type", 1);
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
		tester.addOptionalArgument("--type", CommandLineArgument.DataType.String);
		tester.setNumberValues("--type", 1);
		try
		{
			tester.parse("7 --type sphere 5 2");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
		assertEquals("sphere", tester.getValue("--type"));
	}
	
	@Test
	public void testReadingMoreOptionalArguments(){
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addOptionalArgument("--type", CommandLineArgument.DataType.String);
		tester.addOptionalArgument("-h", CommandLineArgument.DataType.Boolean);
		tester.addOptionalArgument("--actor", CommandLineArgument.DataType.String);
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
		tester.addOptionalArgument("--type", CommandLineArgument.DataType.String);
		tester.addOptionalValue("--type", "Box");
		try
		{
			tester.parse("7 5 2");
			assertEquals("Box", tester.getValue("--type"));
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentAsFlag() {
		tester.addOptionalArgument("--type", CommandLineArgument.DataType.String);
		boolean flag = true;
		try {
			tester.parse("--type");
			assertEquals(true, tester.getValue("--type"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentOverrideDefaultValue() {
		tester.addOptionalArgument("--type", CommandLineArgument.DataType.String);
		tester.setNumberValues("--type", 1);
		try {
			tester.parse("--type shape");
			assertEquals("shape", tester.getValue("--type"));
		} catch (Exception e) {
			assertTrue(false);
		}

	}
	
	@Test
	public void testOptionalArgumentDescription() {
		tester.addOptionalArgument("--type", CommandLineArgument.DataType.String);
		tester.setNumberValues("--type", 1);
		tester.setDescription("--type", "The shape the user defines");
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
		tester.addOptionalArgument("--type", CommandLineArgument.DataType.String);

		tester.setNumberValues("--type", 1);
		tester.setShortOption("--type", "-t");
		assertEquals("-t", tester.getShortOption("--type"));
		assertTrue(tester.getArgumentList().contains(new OptionalArgument("-t")));
		assertEquals(0, tester.getArgumentList().indexOf((new OptionalArgument("-t"))));
		try {
			tester.parse("-t shape");
		} catch (NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e) {
			e.printStackTrace();
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
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.setDescription("length", "the length of the box");
		tester.addOptionalArgument("--type", CommandLineArgument.DataType.String);
		assertEquals("<?xml version=\"1.0\" encoding=\""+ "UTF-8" + "\"?>\r\n<arguments>\r\n\t<argument>\r\n\t\t<name>length</name>"+
		"\r\n\t\t<type>Integer</type>\r\n\t\t<description>the length of the box</description>\r\n\t</argument>\r\n\t<optionalArgument>\r\n\t\t"+
		"<name>--type</name>\r\n\t\t<numValues>0</numValues>\r\n\t\t<type>String</type>\r\n\t\t<description></description>"+
		"\r\n\t\t<value>" + null + "</value>\r\n\t\t<shortName></shortName>\r\n\t</optionalArgument>\r\n</arguments>",tester.getOutput());
	}

	@Test
	public void testXMLFileWriting()
	{
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.setDescription("length", "the length of the box");
		tester.addOptionalArgument("--type", CommandLineArgument.DataType.String);
		tester.writeToXMLFile("test.xml");
		assertTrue(new File("test.xml").exists());
		
		ArgumentParser testNo = XMLParser.createArgumentParser("test.xml");
		assertEquals("<?xml version=\"1.0\" encoding=\""+ "UTF-8" + "\"?>\r\n<arguments>\r\n\t<argument>\r\n\t\t<name>length</name>"+
		"\r\n\t\t<type>Integer</type>\r\n\t\t<description>the length of the box</description>\r\n\t</argument>\r\n\t<optionalArgument>\r\n\t\t"+
		"<name>--type</name>\r\n\t\t<numValues>0</numValues>\r\n\t\t<type>String</type>\r\n\t\t<description></description>"+	
		"\r\n\t\t<value>" + null + "</value>\r\n\t\t<shortName></shortName>\r\n\t</optionalArgument>\r\n</arguments>",tester.getOutput());
	}
    
    @Test
    public void testGetNumberValues()
    {
        tester.addOptionalArgument("type");
        tester.setNumberValues("type", 5);
        assertEquals(5, tester.getNumberValues("type"));
    }
    
    @Test
    public void testGetOptionalArgumentDataType()
    {
        tester.addOptionalArgument("type", CommandLineArgument.DataType.String);
        assertEquals(CommandLineArgument.DataType.String, tester.getDataType("type"));
    }
}