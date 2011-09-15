package net.gageot.kittenmash;

import java.util.List;
import org.simpleframework.http.Response;

public class VoteController {
	private final Scores scores;

	public VoteController(Scores scores) {
		this.scores = scores;
	}

	public void render(Response resp, List<String> path) {
		String kittenId = path.get(1);

		scores.win(kittenId);

		resp.setCode(307);
		resp.add("Location", "/");
	}
}