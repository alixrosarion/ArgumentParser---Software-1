package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArguementParserTest {

	@Test
	public void testEmptyStrings () {
		ArguementParser tester = new ArguementParser("", "", "");
		assertEquals("", tester.getArg1());
		assertEquals("", tester.getArg2());
		assertEquals("", tester.getArg3());
	}
	
	@Test
	public void testCommandLine () {
		ArguementParser tester = new ArguementParser("7", "5","8");
		assertEquals("7", tester.getArg1());
		assertEquals("5", tester.getArg2());
		assertEquals("2", tester.getArg3());
	}
}
