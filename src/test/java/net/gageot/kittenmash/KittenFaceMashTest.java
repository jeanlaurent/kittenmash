package net.gageot.kittenmash;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Test;

public class KittenFaceMashTest {
	@Test
	public void canSayHello() throws Exception {
		KittenFaceMash.main(new String[0]);

		WebTester web = new WebTester();
		web.setBaseUrl("http://localhost:8080/");

		web.beginAt("/");

		web.assertTextPresent("hello world");
	}
}
