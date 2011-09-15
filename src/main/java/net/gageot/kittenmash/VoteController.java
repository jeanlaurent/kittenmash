package net.gageot.kittenmash;

import javax.inject.Inject;
import org.simpleframework.http.Response;

public class VoteController {
	private final Scores scores;

	@Inject
	public VoteController(Scores scores) {
		this.scores = scores;
	}

	public void render(Response resp, String kittenId) {
		scores.win(kittenId);

		resp.setCode(307);
		resp.add("Location", "/");
	}
}