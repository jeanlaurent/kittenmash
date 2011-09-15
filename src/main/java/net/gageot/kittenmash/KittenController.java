package net.gageot.kittenmash;

import static com.google.common.io.Files.*;
import static java.lang.String.*;
import java.io.*;
import java.util.List;
import org.simpleframework.http.Response;

public class KittenController {
	public void render(Response resp, List<String> path) throws IOException {
		String kittenId = path.get(1);
		resp.getOutputStream().write(toByteArray(new File(format("kitten/%s.jpg", kittenId))));
	}
}