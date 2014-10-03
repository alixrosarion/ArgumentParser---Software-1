package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentParserTest {
	/*
	//@Test
	public void testAddSingleArgument () {
		ArgumentParser tester = new ArgumentParser();
		assertEquals(0, tester.getNumArguments());
		tester.addArgument("length");
		assertEquals(1, tester.getNumArguments());
	}
	
	//@Test
	public void testAddMultipleArguments () {
		ArgumentParser tester = new ArgumentParser();
		assertEquals(0, tester.getNumArguments());
		tester.addArgument("length");
		tester.addArgument("width");
		tester.addArgument("height");
		assertEquals(3, tester.getNumArguments());
	}
	
	//@Test
	public void testParseSingleArgument() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgumentValue("7");
		tester.parse("VolCalc 7");
		assertEquals("7", tester.getArgumentValue("length"));
	}
	
	//@Test
	public void testVolCalc() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgumentValue("7");
		tester.addArgument("width");
		tester.addArgumentValue("5");
		tester.addArgument("height");
		tester.addArgumentValue("2");
		tester.parse("VolCalc 7 5 2");
		assertEquals("7", tester.getArgumentValue("length"));
		assertEquals("5", tester.getArgumentValue("width"));
		assertEquals("2", tester.getArgumentValue("height"));
		//assertEquals("", tester.wrongArgument());
	}
	*/
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
		//assertEquals("Not Enough", tester.wrongArgument());
		try
		{
			tester.parse("VolCalc 7 5");
			assertTrue(false);
		}catch(NotEnoughArgsException  | TooManyArgsException e){
			//assertTrue(false);
		}
	}
	
	//@Test
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
	
	//@Test
	public void testgetArgumentType()
	{
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgumentValue("7");
		tester.addArgument("width");
		tester.addArgumentValue("5");
		//assertEquals("Integer", tester.getArgumentType("length"));
	}
	
}