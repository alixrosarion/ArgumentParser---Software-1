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
		tester.addProgram("Volcalc", "Calculates some volume");
	}

	@Test
	public void testMutualGroupsWithNotMutuals()
	{
		tester.setMutualGroup(1, "type", "al");
		tester.setMutualGroup(2, "bo", "jo");
		tester.addOptionalArgument("verbose");
		tester.addOptionalArgument("type");
		tester.addOptionalArgument("al");
		tester.addOptionalArgument("bo");
		tester.addOptionalArgument("jo");
		try {
			tester.parse("--type --verbose");
		} catch (Exception e) {
			assertTrue(false);
		}
		
	}
	
	@Test
	public void testMutualGroups()
	{
		tester.setMutualGroup(1, "type", "al");
		tester.addOptionalArgument("type");
		tester.setNumberValues("type", 1);
		assertEquals("[type, al]", tester.getMutualGroup(1));
		try {
			tester.parse("--type a");
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testSetInvalidGroupNumber()
	{
		try {
			tester.setMutualGroup(5, "here", "there");
		} catch (Exception e) {
			assertEquals("edu.jsu.mcis.InvalidGroupException: Invalid group number: 5", e.toString());
		}
	}
	
	@Test
	public void testGetInvalidGroupNumber()
	{
		try {
			tester.getMutualGroup(5);
		} catch (Exception e) {
			assertEquals("edu.jsu.mcis.InvalidGroupException: Invalid group number: 5", e.toString());
		}
	}
	
	@Test
	public void testMultipleValues()
	{
		tester.addProgram("Volcalc", "Calculates some volume");
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.addArgument("width", CommandLineArgument.DataType.Integer);
		tester.setNumberValues("length", 3);
		tester.setNumberValues("width", 2);
		try {
			tester.parse("1 2 4 7 4");
			assertEquals(3, tester.getNumberValues("length"));
			assertEquals("[1, 2, 4]", tester.getValue("length"));
			assertEquals("[7, 4]", tester.getValue("width"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testaddRestrictedArgumentValues()
	{
		tester.addProgram("Volcalc", "Calculates some volume");
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.addRestricted("length", 7, 5, 2);
		assertEquals("7 5 2", tester.checkRestricted("length"));
	}
	
	@Test 
	public void testaddRestrictedArgumentValuesWithInvalidType()
	{
		tester.addProgram("Volcalc", "Calculates some volume");
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		try {
			tester.addRestricted("length", 7, 5, "box");
		} catch (Exception e) {
			assertEquals("edu.jsu.mcis.IncorrectTypeException: Volcalc.java: error: argument length invalid integer value: box", e.toString());
		}
	}
	
	@Test
	public void testParseRestrictedArgumentValues()
	{
		tester.addProgram("Volcalc", "Calculates some volume");
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.addRestricted("length", 7, 5, 2);
		try
		{
			tester.parse("4");
		}
		catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e)
		{
			assertEquals("edu.jsu.mcis.IncorrectValueException: Volcalc.java: error: argument length invalid integer value: 4", e.toString());
		}
	}
	
	@Test
	public void testaddRestrictedOptionalArgumentValues()
	{
		tester.addProgram("Volcalc", "Calculates some volume");
		tester.addOptionalArgument("type");
		tester.setNumberValues("type", 1);
		tester.addRestricted("type", "sphere", "pyramid", "box");
		assertEquals("sphere pyramid box", tester.checkRestricted("type"));
	}
	
	@Test 
	public void testaddRestrictedOptionalArgumentValuesWithInvalidType()
	{
		tester.addProgram("Volcalc", "Calculates some volume");
		tester.addOptionalArgument("type");
		tester.setNumberValues("type", 1);
		try {
			tester.addRestricted("type", "sphere", "pyramid");
			tester.parse("--type 5");
		} catch (Exception e) {
			assertEquals("edu.jsu.mcis.IncorrectValueException: Volcalc.java: error: argument type invalid string value: 5", e.toString());
		}
	}
	
	@Test
	public void testParseRestrictedOptionalArgumentValues()
	{
		tester.addProgram("Volcalc", "Calculates some volume");
		tester.addOptionalArgument("type");
		tester.setNumberValues("type", 1);
		tester.addRestricted("type", "sphere", "pyramid", "box");
		try {
			tester.parse("--type spher");
		} catch (Exception e) {
			assertEquals("edu.jsu.mcis.IncorrectValueException: Volcalc.java: error: argument type invalid string value: spher", e.toString());
		}
	}
	
	@Test
	public void testParsingRestricted()
	{
		try{
			tester.addProgram("Volcalc", "Calculates some volume");
			tester.addArgument("length", CommandLineArgument.DataType.Integer);
			tester.addOptionalArgument("type");
			tester.addRestricted("length", 7, 5, 2);
			tester.addRestricted("type", "sphere", "pyramid", "box");
			tester.setNumberValues("type", 1);
			
			tester.parse("7 --type jon");
			assertTrue(false);
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e)
		{
			assertEquals("edu.jsu.mcis.IncorrectValueException: Volcalc.java: error: argument type invalid string value: jon", e.toString());
		}
	}
	
	@Test
	public void testAddMultipleArguments()
	{
		assertEquals(1, tester.getSize());
		tester.addArgument("length");
		assertEquals(2, tester.getSize());
		tester.addArgument("width");
		assertEquals(3, tester.getSize());
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
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){}
		
	}
	
	@Test
	public void testParseVolCalc() {
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		try
		{
			tester.parse("7 5 2");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){
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
		}catch(TooManyArgValuesException | NotEnoughArgValuesException | IncorrectValueException | IncorrectTypeException e){
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
			tester.parse("2");
			assertTrue(false);
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){
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
		try
		{
			tester.parse("-h");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){
			assertTrue(false);}
		assertEquals("usage: java VolCalc length width height \r\nCalculate the volume of a box\r\nPositional Arguments:"+
		"\r\nlength\tinteger\tthe length of the box\r\nwidth\tfloat\tthe width of the box\r\nheight\tfloat\tthe height of the box"+
		"\r\n\r\nOptional Arguments:\r\n--help, -h\tboolean\r\n", tester.getHelpText());
	}
	
	@Test
	public void testOptionTextWithRestrictedAndRequired(){
		tester.addProgram("VolCalc","Calculate the volume of a box");
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.addArgument("width",CommandLineArgument.DataType.Float);
		tester.addArgument("height",CommandLineArgument.DataType.Float);
		tester.setDescription("length", "the length of the box");
		tester.setDescription("width", "the width of the box");
		tester.setDescription("height","the height of the box");
		tester.addOptionalArgument("type",CommandLineArgument.DataType.String);
		tester.addOptionalArgument("what",CommandLineArgument.DataType.String);
		tester.addOptionalArgument("verbose",CommandLineArgument.DataType.Boolean);
		tester.addOptionalArgument("quiet",CommandLineArgument.DataType.Boolean);
		tester.setShortOption("type", "t");
		tester.addRestricted("type","box","pyramid");
		tester.setRequired("type");
		tester.addRestricted("length", 3, 4, 5);
		
		try
		{
			tester.setMutualGroup(1, "type", "verbose");
			tester.setMutualGroup(2, "what", "quiet");
			tester.parse("-h");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException| InvalidGroupException e){
			assertTrue(false);}
		assertEquals("usage: java VolCalc length width height \r\nCalculate the volume of a box\r\nPositional Arguments:"+
		"\r\nlength\tinteger\tthe length of the box\trestricted to: 3 4 5\r\nwidth\tfloat\tthe width of the box\r\nheight\tfloat\tthe height of the box"+
		"\r\n\r\nOptional Arguments:\r\n--help, -h\tboolean\r\n--type, -t\tstring\trequired\trestricted to: box pyramid mutual group: 1\r\n"+
		"--what\tstring mutual group: 2\r\n--verbose\tboolean mutual group: 1\r\n--quiet\tboolean mutual group: 2\r\n", tester.getHelpText());
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
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){
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
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){
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
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){
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
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){
		}
	}
	
	@Test
	public void testReadingOptionalArgumentAtEnd ()
	{
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.addOptionalArgument("type", CommandLineArgument.DataType.String);
		assertEquals("type", tester.getOptionalArgument("type"));
		tester.setNumberValues("type", 1);
		try
		{
			tester.parse("7 --type sphere");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentValues(){
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addOptionalArgument("type", CommandLineArgument.DataType.String);
		tester.setNumberValues("type", 1);
		try
		{
			tester.parse("7 --type sphere 5 2");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){
			assertTrue(false);
		}
		assertEquals("sphere", tester.getValue("type"));
	}
	
	@Test
	public void testReadingMoreOptionalArguments(){
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addOptionalArgument("type", CommandLineArgument.DataType.String);
		tester.addOptionalArgument("actor", CommandLineArgument.DataType.String);
		try
		{
			tester.parse("7 --type sphere 5 -h 2 --actor will smith");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){
			assertTrue(false);
		}
	}
	
	@Test 
	public void testOptionalArgumentsWithDefaults(){
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addOptionalArgument("type", CommandLineArgument.DataType.String);
		try
		{
			tester.setValue("type", "Box");
			tester.parse("7 5 2");
			assertEquals("Box", tester.getValue("type"));
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentAsFlag() {
		tester.addOptionalArgument("type", CommandLineArgument.DataType.String);
		boolean flag = true;
		try {
			tester.parse("--type");
			assertEquals(true, tester.getValue("type"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentOverrideDefaultValue() {
		tester.addOptionalArgument("type", CommandLineArgument.DataType.String);
		tester.setNumberValues("type", 1);
		try {
			tester.parse("--type shape");
			assertEquals("shape", tester.getValue("type"));
		} catch (Exception e) {
			assertTrue(false);
		}

	}
	
	@Test
	public void testOptionalArgumentDescription() {
		tester.addOptionalArgument("type", CommandLineArgument.DataType.String);
		tester.setNumberValues("type", 1);
		tester.setDescription("type", "The shape the user defines");
		try {
			tester.parse("--type shape");
			assertEquals("The shape the user defines", tester.getDescription("type"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testShortName()
	{
		tester.addOptionalArgument("type", CommandLineArgument.DataType.String);

		tester.setNumberValues("type", 1);
		tester.setShortOption("type", "t");
		assertEquals("t", tester.getShortOption("type"));
		assertTrue(tester.getArgumentList().contains(new OptionalArgument("t")));
		assertEquals(1, tester.getArgumentList().indexOf((new OptionalArgument("t"))));
		try {
			tester.parse("-t shape");
		} catch (NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e) {
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
		tester.addOptionalArgument("type", CommandLineArgument.DataType.String);
		assertEquals("<?xml version=\"1.0\" encoding=\""+ "UTF-8" + "\"?>\r\n<arguments>\r\n\t<optionalArgument>\r\n\t\t"+
		"<name>help</name>\r\n\t\t<numValues>0</numValues>\r\n\t\t<type>Boolean</type>\r\n\t\t<description></description>"+
		"\r\n\t\t<value>" + null + "</value>\r\n\t\t<shortName>h</shortName>\r\n\t</optionalArgument>\r\n\t<argument>\r\n\t\t<name>length</name>"+
		"\r\n\t\t<type>Integer</type>\r\n\t\t<description>the length of the box</description>\r\n\t</argument>\r\n\t<optionalArgument>\r\n\t\t"+
		"<name>type</name>\r\n\t\t<numValues>0</numValues>\r\n\t\t<type>String</type>\r\n\t\t<description></description>"+
		"\r\n\t\t<value>" + null + "</value>\r\n\t\t<shortName></shortName>\r\n\t</optionalArgument>\r\n</arguments>",tester.getOutput());
	}

	@Test
	public void testXMLFileWriting()
	{
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.setDescription("length", "the length of the box");
		tester.addOptionalArgument("type", CommandLineArgument.DataType.String);
		XMLParser.saveXMLFile(tester, "test.xml");
		assertTrue(new File("test.xml").exists());
		
		ArgumentParser testNo = XMLParser.createArgumentParser("test.xml");
		assertEquals("<?xml version=\"1.0\" encoding=\""+ "UTF-8" + "\"?>\r\n<arguments>\r\n\t<optionalArgument>\r\n\t\t"+
		"<name>help</name>\r\n\t\t<numValues>0</numValues>\r\n\t\t<type>Boolean</type>\r\n\t\t<description></description>"+
		"\r\n\t\t<value>" + null + "</value>\r\n\t\t<shortName>h</shortName>\r\n\t</optionalArgument>\r\n\t<argument>\r\n\t\t<name>length</name>"+
		"\r\n\t\t<type>Integer</type>\r\n\t\t<description>the length of the box</description>\r\n\t</argument>\r\n\t<optionalArgument>\r\n\t\t"+
		"<name>type</name>\r\n\t\t<numValues>0</numValues>\r\n\t\t<type>String</type>\r\n\t\t<description></description>"+
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
        assertEquals(CommandLineArgument.DataType.String, tester.getArgumentDataType("type"));
    }

	@Test
	public void testRequiredOptionalArguments()
	{
		tester.addArgument("length");
		tester.addOptionalArgument("type");
		tester.setRequired("type");
		try {
			tester.parse("2");
			assertTrue(false);
		} catch (NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException e) {
			assertEquals("edu.jsu.mcis.NotEnoughArgValuesException: the following arguments are required: type", e.toString());
			
		}
	}
	
	@Test
	public void testSetRequiredOptionalArguments()
	{
		tester.addOptionalArgument("type");
		tester.setRequired("type");
		assertTrue(tester.getRequired("type"));
	}
	
	@Test 
	public void testSetDefaultValue()
	{
		tester.addOptionalArgument("type");
		tester.setDefaultValue("type", "box");
		assertEquals("box", tester.getValue("type"));
	}
	
	@Test
	public void testMutuallyExclusiveOptionalArguments()
	{
		try{
		tester.addOptionalArgument("verbose");
		tester.addOptionalArgument("quiet");
		tester.addOptionalArgument("type");
		tester.addOptionalArgument("what");
		
		tester.setMutualGroup(1, "type", "verbose");
		tester.setMutualGroup(2, "what", "quiet");
				
		tester.parse("--quiet --verbose");
		assertTrue(false);
		} catch (NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException | InvalidGroupException e) {
			assertEquals("edu.jsu.mcis.InvalidGroupException: The following argument is part of the excluded group verbose"  ,e.toString());
			
		}
	}
	
	@Test
	public void testMutallyExclusiveAndNotMutallyExclusiveOptionalArguments()
	{
		try{
		tester.setMutualGroup(1, "type", "verbose");
		tester.setMutualGroup(2, "what", "quiet");
		
		tester.addOptionalArgument("verbose");
		tester.addOptionalArgument("quiet");
		tester.addOptionalArgument("type");
		tester.addOptionalArgument("what");	
		tester.parse("--quiet --help");
		} catch (NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException | InvalidGroupException e) {
			assertTrue(false);		
		}
	}
	
	@Test 
	public void testAllArgumentStylesCombined()
	{
		try{
		tester.setMutualGroup(1, "type");
		tester.setMutualGroup(2, "what");
		
		tester.addArgument("length", CommandLineArgument.DataType.Integer);
		tester.addOptionalArgument("type");
		tester.addOptionalArgument("what");	
		tester.parse("--quiet 7");
		assertTrue(false);
		} catch (NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException | InvalidGroupException e) {
			assertEquals("edu.jsu.mcis.IncorrectValueException: quiet is not a valid argument.", e.toString());
		}
	}
	
	@Test
	public void testAddingMutallyExclusiveAtDifferentTimes()
	{
		try{
		tester.setMutualGroup(1, "type");
		tester.setMutualGroup(2, "what", "quiet");
		tester.setMutualGroup(1, "verbose");
		
		tester.addOptionalArgument("verbose");
		tester.addOptionalArgument("quiet");
		tester.addOptionalArgument("type");
		tester.addOptionalArgument("what");
		tester.parse("--quiet");
		assertEquals("[type, verbose]", tester.getMutualGroup(1));
		} catch (NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectValueException | IncorrectTypeException | InvalidGroupException e) {
			assertTrue(false);		
		}
	}
	
}