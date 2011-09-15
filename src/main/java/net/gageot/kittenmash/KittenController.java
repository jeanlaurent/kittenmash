package net.gageot.kittenmash;

import static com.google.common.io.Files.*;
import static java.lang.String.*;
import java.io.*;
import org.simpleframework.http.Response;

public class KittenController {
	public void render(Response resp, int kittenId) throws IOException {
		resp.getOutputStream().write(toByteArray(new File(format("kitten/%d.jpg", kittenId))));
	}
}