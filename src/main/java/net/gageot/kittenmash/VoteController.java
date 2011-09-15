package net.gageot.kittenmash;

import javax.inject.Inject;
import net.gageot.test.Scores;
import org.simpleframework.http.Response;

public final class VoteController {
	private final Scores scores;

	@Inject
	public VoteController(Scores scores) {
		this.scores = scores;
	}

	public void render(Response resp, String kittenId) {
		scores.win(Integer.parseInt(kittenId));

		resp.add("Location", "/");
		resp.setCode(307);
	}
}