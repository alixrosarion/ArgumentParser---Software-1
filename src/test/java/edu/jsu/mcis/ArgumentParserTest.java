package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentParserTest {
	
	@Test
	public void testAddMultipleArguments()
	{
		ArgumentParser tester = new ArgumentParser();
		assertEquals(0, tester.getSize());
		tester.addArgument("length");
		assertEquals(1, tester.getSize());
		tester.addArgument("width");
		assertEquals(2, tester.getSize());
	}
	
	@Test
	public void testParseSingleArgument() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgument("width");
		try
		{
		tester.parse("VolCalc 7 2");
			assertEquals("7", tester.getArgumentValue("length"));
			assertEquals("2", tester.getArgumentValue("width"));
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){}
		
	}
	
	@Test
	public void testParseVolCalc() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		try
		{
			tester.parse("VolCalc 7 5 2");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
		assertEquals("7", tester.getArgumentValue("length"));
		assertEquals("5", tester.getArgumentValue("width"));
		assertEquals("2", tester.getArgumentValue("height"));
	}
	
	@Test
	public void testTooManyArgumentValues() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		try
		{
			tester.parse("VolCalc 7 5 2 10 2");
			assertTrue(false);
		}catch(TooManyArgValuesException | NotEnoughArgValuesException | IncorrectTypeException e){
			assertEquals("unrecognised arguments: 10 2", tester.getUnmatched());
		}
	}
	
	@Test
	public void testNotEnoughArgumentValues() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		try
		{
			tester.parse("VolCalc 7");
			assertTrue(false);
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertEquals("the following arguments are required: width height", tester.getUnmatched());
		}
	}
	
	@Test
	public void testAddArgTypes()
	{
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length", "Integer");
		tester.addArgument("width", "Float");
		assertEquals("Integer", tester.getArgumentType("length"));
		assertEquals("Float", tester.getArgumentType("width"));
	}
	
	@Test
	public void testAddArgDescriptions()
	{
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length", "Integer","the length of the box");
		tester.addArgument("width", "Float","the width of the box");
		assertEquals("the length of the box", tester.getArgumentDescription("length"));
		assertEquals("the width of the box", tester.getArgumentDescription("width"));
	}
	@Test
	public void testOptionText(){
		ArgumentParser tester = new ArgumentParser();
		tester.addProgram("Volcalc", "Calculate the volume of a box");
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addDescription("length", "the length of the box");
		tester.addDescription("width", "the width of the box");
		tester.addDescription("height","the height of the box");
		tester.addOptArg("-h", 0);
		try
		{
			tester.parse("VolCalc 7 5 2 -h");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);}
		assertEquals("usage: java VolCalc length width height \nCalculate the volume of a box\nPositional Arguments:\nlength\t\tthe length of the box\nwidth\t\tthe width of the box\nheight\t\tthe height of the box\n", tester.getHelpText());
	}
	
	@Test
	public void testTypeParsing()
	{
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length", "Integer");
		tester.addArgument("width","Float");
		tester.addArgument("rainy","Boolean");
		tester.addArgument("dog","String");
		try
		{
			tester.parse("VolCalc 7 5.2 true someString");
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
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length", "Integer");
		tester.addOptArg("--type", 1);
		assertEquals("--type", tester.getOptArg("--type", 1));
		try
		{
			tester.parse("VolCalc 7 --type sphere");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentValues(){
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addOptArg("--type", 1);
		try
		{
			tester.parse("VolCalc 7 --type sphere 5 2");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
		assertEquals("sphere", tester.getOptionalValue("--type"));
	}
	
	@Test
	public void testReadingMoreOptionalArguments(){
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addOptArg("--type", 1);
		tester.addOptArg("-h", 0);
		tester.addOptArg("--actor", 2);
		try
		{
			tester.parse("VolCalc 7 --type sphere 5 -h 2 --actor will smith");
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentsWithDefaults(){
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		tester.addOptArg("--type", 1, "String", "Type of shape", "Box");
		try
		{
			tester.parse("VolCalc 7 5 2");
			assertEquals("Box", tester.getOptionalValue("--type"));
		}catch(NotEnoughArgValuesException  | TooManyArgValuesException | IncorrectTypeException e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentAsFlag() {
		ArgumentParser tester = new ArgumentParser();
		tester.addOptArg("--type", 0);
		boolean flag = true;
		try {
			tester.parse("VolCalc --type");
			assertEquals(true, tester.getOptionalValue("--type"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentOverrideDefaultValue() {
		ArgumentParser tester = new ArgumentParser();
		tester.addOptArg("--type", 1);
		try {
			tester.parse("VolCalc --type shape");
			assertEquals("shape", tester.getOptionalValue("--type"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testOptionalArgumentDescription() {
		ArgumentParser tester = new ArgumentParser();
		tester.addOptArg("--type", 1, "String", "The shape the user defines", "Box");
		try {
			tester.parse("VolCalc --type shape");
			assertEquals("The shape the user defines", tester.getDescription("--type"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testShortName()
	{
		ArgumentParser tester = new ArgumentParser();
		tester.addOptArg("--type", 1, "String", "The shape the user defines", "Box");
		tester.addShortOpt("--type", "-t");
		assertEquals("-t", tester.getShortOpt("--type"));
		try {
			tester.parse("VolCalc -t shape");
			assertEquals("The shape the user defines", tester.getDescription("-t"));
			assertEquals("The shape the user defines", tester.getDescription("--type"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
}