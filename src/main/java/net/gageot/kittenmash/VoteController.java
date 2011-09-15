package net.gageot.kittenmash;

import javax.inject.Inject;
import net.gageot.kittenmash.util.Elo;
import org.simpleframework.http.Response;

public class VoteController {
	private final Elo scores;

	@Inject
	public VoteController(Elo scores) {
		this.scores = scores;
	}

	public void render(Response resp, int winner, int loser) {
		scores.vote(winner, loser);

		resp.add("Location", "/");
		resp.setCode(307);
	}
}