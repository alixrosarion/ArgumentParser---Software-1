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
		tester.parse("VolCalc 7");
		assertEquals("7", tester.getArgumentValue("length"));
	}
	
	@Test
	public void testVolCalc() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.parse("VolCalc 7");
		assertEquals("7", tester.getArgumentValue("length"));
		tester.addArgument("width");
		tester.parse("VolCalc 5");
		assertEquals("5", tester.getArgumentValue("width"));
		tester.addArgument("height");
		tester.parse("VolCalc 2");
		assertEquals("2", tester.getArgumentValue("height"));
	}
	
	@Test
	public void testRightNumberOfArguments() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.parse("VolCalc 7");
		tester.addArgument("width");
		tester.parse("VolCalc 5");
		tester.addArgument("height");
		tester.parse("VolCalc 2");
		assertEquals("", tester.wrongArgument());
	}
	
	@Test
	public void testTooManyArguments() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.parse("VolCalc 7");
		tester.addArgument("width");
		tester.parse("VolCalc 5");
		tester.addArgument("height");
		tester.parse("VolCalc 2");
		tester.parse("VolCalc dog");		
		assertEquals("Too Many Arguments! Extra one is dog", tester.wrongArgument());
	}
	
	//@Test
	public void testNotEnoughArguments() {
		ArgumentParser tester = new ArgumentParser();
		tester.addArgument("length");
		tester.parse("VolCalc 7");
		tester.addArgument("width");
		tester.parse("VolCalc 5");
		tester.addArgument("height");
		assertEquals("Not Enough Arguments!", tester.wrongArgument());
	}
}