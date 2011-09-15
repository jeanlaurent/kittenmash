package net.gageot.kittenmash;

import net.gageot.test.JWebUnitTester;
import org.junit.Test;

public class HelloWorldTest extends JWebUnitTester<HelloWorld> {
	@Test
	public void canSayHello() throws Exception {
		beginAt("/");

		assertTextPresent("hello world");
	}
}
