package net.gageot.kittenmash;

import static com.google.common.base.Charsets.*;
import java.io.*;
import javax.inject.Inject;
import net.gageot.test.Elo;
import org.apache.commons.lang.math.RandomUtils;
import org.simpleframework.http.Response;
import org.stringtemplate.v4.ST;
import com.google.common.io.Files;

public final class IndexController {
	private final Elo scores;

	@Inject
	public IndexController(Elo scores) {
		this.scores = scores;
	}

	public void render(Response resp) throws IOException {
		int kittenLeft = RandomUtils.nextInt(10);
		int kittenRight;
		do {
			kittenRight = RandomUtils.nextInt(10);
		} while (kittenLeft == kittenRight);

		String html = Files.toString(new File("index.html"), UTF_8);
		ST template = new ST(html, '$', '$');
		template.add("kittenLeft", kittenLeft);
		template.add("kittenRight", kittenRight);
		template.add("scoreLeft", scores.get(kittenLeft));
		template.add("scoreRight", scores.get(kittenRight));
		resp.getPrintStream().append(template.render());
	}
}