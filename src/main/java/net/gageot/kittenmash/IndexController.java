package net.gageot.kittenmash;

import static com.google.common.base.Charsets.*;
import java.io.*;
import javax.inject.Inject;
import net.gageot.test.Scores;
import org.simpleframework.http.Response;
import org.stringtemplate.v4.ST;
import com.google.common.io.Files;

public final class IndexController {
	private final Scores scores;

	@Inject
	public IndexController(Scores scores) {
		this.scores = scores;
	}

	public void render(Response resp) throws IOException {
		String html = Files.toString(new File("index.html"), UTF_8);
		ST template = new ST(html, '$', '$');
		template.add("scoreLeft", scores.get(1));
		template.add("scoreRight", scores.get(2));
		resp.getPrintStream().append(template.render());
	}
}