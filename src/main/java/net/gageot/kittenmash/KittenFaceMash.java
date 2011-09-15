package net.gageot.kittenmash;

import static com.google.common.base.Charsets.*;
import static com.google.common.collect.Iterables.*;
import static com.google.common.collect.Lists.*;
import static com.google.common.io.Files.*;
import static java.lang.String.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;
import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.SocketConnection;
import org.stringtemplate.v4.ST;
import com.google.common.io.Files;
import com.google.common.util.concurrent.AbstractService;

public class KittenFaceMash extends AbstractService implements Container {
	private SocketConnection socketConnection;
	private final int port;
	private final Scores scores = new Scores();

	public KittenFaceMash(int port) {
		this.port = port;
	}

	@Override
	public void handle(Request req, Response resp) {
		List<String> path = newArrayList(req.getPath().getSegments());
		String action = getFirst(path, "index");

		try {
			if ("kitten".equals(action)) {
				String kittenId = path.get(1);
				resp.getOutputStream().write(toByteArray(new File(format("kitten/%s.jpg", kittenId))));
			} else if ("vote".equals(action)) {
				String kittenId = path.get(1);

				scores.win(kittenId);

				resp.setCode(307);
				resp.add("Location", "/");
			} else {
				String index = Files.toString(new File("index.html"), UTF_8);
				ST template = new ST(index, '$', '$');
				template.add("scoreLeft", scores.get("01"));
				template.add("scoreRight", scores.get("02"));

				resp.getPrintStream().append(template.render());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				resp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new KittenFaceMash(8080).startAndWait();
	}

	@Override
	protected void doStart() {
		try {
			socketConnection = new SocketConnection(this);
			socketConnection.connect(new InetSocketAddress(port));
			notifyStarted();
		} catch (IOException e) {
			notifyFailed(e);
		}
	}

	@Override
	protected void doStop() {
		try {
			socketConnection.close();
			notifyStopped();
		} catch (IOException e) {
			notifyFailed(e);
		}
	}
}
