package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentParserTest {
	
	//@Test
	public void testAddSingleArgument () {
		ArgumentParser tester = new ArgumentParser();
		assertEquals(0, tester.getNumArguments());
		tester.addArgument("length");
		assertEquals(1, tester.getNumArguments());
	}
	
	@Test
	public void testAddMultipleArguments () {
		ArgumentParser tester = new ArgumentParser();
		assertEquals(0, tester.getNumArguments());
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		assertEquals(3, tester.getNumArguments());
	}
	
	@Test
	public void testParseSingleArgument() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgumentValue("7");
		try
		{
			tester.parse("VolCalc 7");
			//assertTrue(false);
		}catch(TooManyArgsException | NotEnoughArgsException e){
			//assertTrue(false);
		}
	}
	
	@Test
	public void testVolCalc() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgumentValue("7");
		tester.addArgument("width");
		tester.addArgumentValue("5");
		tester.addArgument("height");
		tester.addArgumentValue("2");
		try
		{
			tester.parse("VolCalc 7 5 2");
			//assertTrue(false);
		}catch(NotEnoughArgsException  | TooManyArgsException e){
			//assertTrue(false);
		}
		assertEquals("7", tester.getArgumentValue("length"));
		assertEquals("5", tester.getArgumentValue("width"));
		assertEquals("2", tester.getArgumentValue("height"));
	}
	
	@Test
	public void testTooManyArguments() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgumentValue("7");
		tester.addArgument("width");
		tester.addArgumentValue("5");
		tester.addArgument("height");
		tester.addArgumentValue("2");
		tester.addArgumentValue("dog");
		try
		{
			tester.parse("VolCalc 7 5 2 dog");
			assertTrue(false);
		}catch(TooManyArgsException | NotEnoughArgsException e){
			//assertTrue(false);
		}
	}
	
	@Test
	public void testNotEnoughArguments() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgumentValue("7");
		tester.addArgument("width");
		tester.addArgumentValue("5");
		tester.addArgument("height");
		try
		{
			tester.parse("VolCalc 7 5");
			assertTrue(false);
		}catch(NotEnoughArgsException  | TooManyArgsException e){
			//assertTrue(false);
		}
	}
	
	@Test
	public void testHelpOptionReads(){
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("help");
		tester.addArgumentValue("-h");
		try
		{
		tester.parse("VolCalc -h");
		}catch(NotEnoughArgsException | TooManyArgsException e)
		{}
		assertEquals("-h", tester.getArgumentValue("help"));
	}
	
	@Test
	public void testgetArgumentType()
	{
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgumentValue("7");
		tester.addArgument("width");
		tester.addArgumentValue("5.2");
		tester.addArgument("height");
		tester.addArgumentValue("true");
		tester.addArgument("dog");
		tester.addArgumentValue("falafel");
		assertEquals("Integer", tester.getArgumentType("length"));
		assertEquals("Float", tester.getArgumentType("width"));
		assertEquals("Boolean", tester.getArgumentType("height"));
		assertEquals("String", tester.getArgumentType("dog"));
	}
	
}