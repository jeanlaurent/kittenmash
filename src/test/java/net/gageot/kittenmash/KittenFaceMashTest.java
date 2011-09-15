package net.gageot.kittenmash;

import java.net.MalformedURLException;
import net.gageot.test.JWebUnitTester;
import org.junit.Test;

public class KittenFaceMashTest extends JWebUnitTester<KittenFaceMash> {
	@Test
	public void canSayHello() {
		beginAt("/");

		assertTextPresent("hello world");
	}

	@Test
	public void canShowKitten() throws MalformedURLException {
		beginAt("kitten/1");

		assertDownloadedFileEquals("kitten/1.jpg");
	}
}
