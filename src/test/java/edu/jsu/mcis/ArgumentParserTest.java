package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

<<<<<<< HEAD
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
}
=======
public class ArguemntParserTest {

	@Test
	public void testEmptyStrings () {
		ArgumentParser tester = new ArgumentParser("", "", "");
		assertEquals("", tester.getArg1());
		assertEquals("", tester.getArg2());
		assertEquals("", tester.getArg3());
	}
	
	@Test
	public void testCommandLine () {
		ArgumentParser tester = new ArgumentParser("7", "5","2");
		assertEquals("7", tester.getArg1());
		assertEquals("5", tester.getArg2());
		assertEquals("2", tester.getArg3());
	}
}
>>>>>>> 5b4b847ed55764bfa5a357a99b1a400b84276090
