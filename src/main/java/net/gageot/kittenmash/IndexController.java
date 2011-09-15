package net.gageot.kittenmash;

import static org.apache.commons.io.FileUtils.*;
import java.io.*;
import javax.inject.Inject;
import net.gageot.kittenmash.util.Elo;
import org.apache.commons.lang.math.RandomUtils;
import org.simpleframework.http.Response;
import org.stringtemplate.v4.ST;

public class IndexController {
	private final Elo scores;

	@Inject
	public IndexController(Elo scores) {
		this.scores = scores;
	}

	public void render(Response resp) throws IOException {
		int left = RandomUtils.nextInt(10);
		int right;
		do {
			right = RandomUtils.nextInt(10);
		} while (left == right);

		String html = readFileToString(new File("index.html"));

		ST template = new ST(html, '$', '$') //
				.add("left", left) //
				.add("right", right) //
				.add("scoreLeft", scores.get(left)) //
				.add("scoreRight", scores.get(right));

		resp.getPrintStream().append(template.render());
	}
}