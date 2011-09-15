package net.gageot.kittenmash;

import net.gageot.test.JWebUnitTester;
import org.junit.Test;

public class KittenFaceMashTest extends JWebUnitTester<KittenFaceMash> {
	@Test
	public void canSayHello() {
		beginAt("/");

		assertTextPresent("hello world");
	}
}
