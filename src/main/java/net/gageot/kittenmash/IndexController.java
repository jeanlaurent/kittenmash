package net.gageot.kittenmash;

import static com.google.common.base.Charsets.*;
import java.io.*;
import javax.inject.Inject;
import net.gageot.test.Elo;
import org.apache.commons.lang.math.RandomUtils;
import org.simpleframework.http.Response;
import org.stringtemplate.v4.ST;
import com.google.common.io.Files;

public class IndexController {
	private final Elo elo;

	@Inject
	public IndexController(Elo elo) {
		this.elo = elo;
	}

	public void render(Response resp) throws IOException {
		int kittenLeft = RandomUtils.nextInt(10);
		int kittenRight;
		do {
			kittenRight = RandomUtils.nextInt(10);
		} while (kittenLeft == kittenRight);

		String index = Files.toString(new File("index.html"), UTF_8);
		ST template = new ST(index, '$', '$');
		template.add("kittenLeft", kittenLeft);
		template.add("kittenRight", kittenRight);
		template.add("scoreLeft", elo.get(kittenLeft));
		template.add("scoreRight", elo.get(kittenRight));

		resp.getPrintStream().append(template.render());
	}
}