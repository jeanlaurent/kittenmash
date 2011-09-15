package net.gageot.kittenmash;

import net.gageot.test.JWebUnitTester;
import org.junit.Test;

public class KittenFaceMashTest extends JWebUnitTester<KittenFaceMash> {
	@Test
	public void canSayHello() throws Exception {
		beginAt("/");

		assertTextPresent("hello world");
	}

	@Test
	public void canShowKitten() throws Exception {
		beginAt("/kitten/01");

		assertDownloadedFileEquals("kitten/01.jpg");
	}

	@Test
	public void canAnotherKitten() throws Exception {
		beginAt("/kitten/02");

		assertDownloadedFileEquals("kitten/02.jpg");
	}
}
