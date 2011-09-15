package net.gageot.kittenmash;

import javax.inject.Inject;
import net.gageot.test.Elo;
import org.simpleframework.http.Response;

public class VoteController {
	private final Elo elo;

	@Inject
	public VoteController(Elo elo) {
		this.elo = elo;
	}

	public void render(Response resp, String kittenWinner, String kittenLoser) {
		elo.vote(Integer.parseInt(kittenWinner), Integer.parseInt(kittenLoser));

		resp.add("Location", "/");
		resp.setCode(307);
	}
}