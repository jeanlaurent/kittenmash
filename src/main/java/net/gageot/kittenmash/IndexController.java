package net.gageot.kittenmash;

import static com.google.common.base.Charsets.*;
import java.io.*;
import org.simpleframework.http.Response;
import org.stringtemplate.v4.ST;
import com.google.common.io.Files;

public class IndexController {
	private final Scores scores;

	public IndexController(Scores scores) {
		this.scores = scores;
	}

	public void render(Response resp) throws IOException {
		String index = Files.toString(new File("index.html"), UTF_8);
		ST template = new ST(index, '$', '$');
		template.add("scoreLeft", scores.get("01"));
		template.add("scoreRight", scores.get("02"));

		resp.getPrintStream().append(template.render());
	}
}