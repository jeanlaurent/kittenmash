package net.gageot.kittenmash;

import javax.inject.Inject;
import net.gageot.test.Elo;
import org.simpleframework.http.Response;

public final class VoteController {
	private final Elo scores;

	@Inject
	public VoteController(Elo scores) {
		this.scores = scores;
	}

	public void render(Response resp, String winner, String loser) {
		scores.vote(Integer.parseInt(winner), Integer.parseInt(loser));

		resp.add("Location", "/");
		resp.setCode(307);
	}
}