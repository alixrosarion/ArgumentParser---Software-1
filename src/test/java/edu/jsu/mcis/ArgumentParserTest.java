package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

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
