package net.gageot.kittenmash;

import java.net.MalformedURLException;
import net.gageot.test.JWebUnitTester;
import org.junit.*;

public class KittenFaceMashTest extends JWebUnitTester<KittenFaceMash> {
	@Test
	public void canSayHello() {
		beginAt("/");

		assertTextPresent("Kitten FaceMash");
	}

	@Test
	public void canShowKitten() throws MalformedURLException {
		beginAt("kitten/1");

		assertDownloadedFileEquals("kitten/1.jpg");
	}

	@Test
	public void canShowAnotherKitten() throws MalformedURLException {
		beginAt("kitten/2");

		assertDownloadedFileEquals("kitten/2.jpg");
	}

	@Test
	@Ignore("Each time a test is ignored, a kitten is killed")
	public void canShowScore() {
		beginAt("/");

		assertTextInElement("scoreLeft", "Score : 0");
		assertTextInElement("scoreRight", "Score : 0");
	}

	@Test
	@Ignore("Each time a test is ignored, a kitten is killed")
	public void canVote() {
		beginAt("/");

		clickLink("voteLeft");

		assertTextInElement("scoreLeft", "Score : 1");
		assertTextInElement("scoreRight", "Score : 0");
	}
}
