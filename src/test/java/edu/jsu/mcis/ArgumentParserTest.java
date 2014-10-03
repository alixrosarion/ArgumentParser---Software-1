package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentParserTest {

	@Test
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
		tester.parse("VolCalc 7");
		assertEquals("7", tester.getArgumentValue("length"));
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
		tester.parse("VolCalc 7 5 2");
		assertEquals("7", tester.getArgumentValue("length"));
		assertEquals("5", tester.getArgumentValue("width"));
		assertEquals("2", tester.getArgumentValue("height"));
		assertEquals("", tester.wrongArgument());
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
		tester.parse("VolCalc 7 5 2 dog");	
		assertEquals("Too Many Arguments", tester.wrongArgument());
	}
	
	@Test
	public void testNotEnoughArguments() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.addArgumentValue("7");
		tester.addArgument("width");
		tester.addArgumentValue("5");
		tester.addArgument("height");
		tester.parse("VolCalc 7 5");
		assertEquals("Not Enough", tester.wrongArgument());
	}
	
	@Test
	public void testHelpOptionReads(){
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("help");
		tester.addArgumentValue("-h");
		tester.parse("VolCalc -h");
		assertEquals("-h", tester.getArgumentValue("help"));
	}
}