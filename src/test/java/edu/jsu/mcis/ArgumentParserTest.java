package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

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
		tester.addArgument("length", CommandLineArgument.Type.Integer,"the length of the box");
		tester.addArgument("width", CommandLineArgument.Type.Float,"the width of the box");
		assertEquals("the length of the box", tester.getArgumentDescription("length"));
		assertEquals("the width of the box", tester.getArgumentDescription("width"));
	}
	
	//@Test
	public void testOptionText(){
		tester.addProgram("Volcalc","Calculate the volume of a box");
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addDescription("length", "the length of the box");
		tester.addDescription("width", "the width of the box");
		tester.addDescription("height","the height of the box");
		tester.addOptArg("-h", 0);
		try
		{
			tester.parse("7 5 2 -h");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);}
		assertEquals("usage: java VolCalc length width height \nCalculate the volume of a box\nPositional Arguments:\nlength\t\tthe length of the box\nwidth\t\tthe width of the box\nheight\t\tthe height of the box\n", tester.getHelpText());
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
	public void testReadingOptionalArgumentAtEnd ()
	{
		tester.addArgument("length", CommandLineArgument.Type.Integer);
		tester.addOptArg("--type", 1);
		assertEquals("--type", tester.getOptArg("--type", 1));
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
		tester.addOptArg("--type", 1);
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
		tester.addOptArg("--type", 1);
		tester.addOptArg("-h", 0);
		tester.addOptArg("--actor", 2);
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
		tester.addOptArg("--type", 1, CommandLineArgument.Type.String, "Type of shape", "Box");
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
		tester.addOptArg("--type", 0);
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
		tester.addOptArg("--type", 1);
		try {
			tester.parse("--type shape");
			assertEquals("shape", tester.getArgumentValue("--type"));
		} catch (Exception e) {
			assertTrue(false);
		}

	}
	
	@Test
	public void testOptionalArgumentDescription() {
		tester.addOptArg("--type", 1, CommandLineArgument.Type.String, "The shape the user defines", "Box");
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
		tester.addOptArg("--type", 1, CommandLineArgument.Type.String, "The shape the user defines", "Box");
		tester.addShortOpt("--type", "-t");
		assertEquals("-t", tester.getShortOpt("--type"));
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
	public void testXMLParsing()
	{
		XMLParser tester = new XMLParser("arguments.xml");
		try {
			tester.argParsReturn().parse("2 2 2");
		} catch (Exception e) {
			assertTrue(false);
		}
	}	
}