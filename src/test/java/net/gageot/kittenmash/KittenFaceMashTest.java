package net.gageot.kittenmash;

import net.gageot.test.JWebUnitTester;
import org.junit.Test;

public class KittenFaceMashTest extends JWebUnitTester<KittenFaceMash> {
	@Test
	public void canShowKitten() throws Exception {
		beginAt("/kitten/1");

		assertDownloadedFileEquals("kitten/1.jpg");
	}

	@Test
	public void canAnotherKitten() throws Exception {
		beginAt("/kitten/2");

		assertDownloadedFileEquals("kitten/2.jpg");
	}

	@Test
	public void canShowScores() {
		beginAt("/");

		assertTextInElement("scoreLeft", "Score : 0");
		assertTextInElement("scoreRight", "Score : 0");
	}

	@Test
	public void canVote() {
		beginAt("/");

		clickLink("voteLeft");

		assertTextInElement("scoreLeft", "Score : 1");
		assertTextInElement("scoreRight", "Score : 0");
	}
}
