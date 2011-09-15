package net.gageot.kittenmash;

import static com.google.common.base.Charsets.*;
import static com.google.common.collect.Iterables.*;
import static com.google.common.collect.Lists.*;
import static com.google.common.io.Files.*;
import static com.google.inject.Guice.*;
import static com.google.inject.name.Names.*;
import static java.lang.String.*;
import static net.gageot.test.Reflection.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;
import javax.inject.Inject;
import net.gageot.test.*;
import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.SocketConnection;
import org.stringtemplate.v4.ST;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.google.common.util.concurrent.AbstractService;
import com.google.inject.*;

public class KittenFaceMash extends AbstractService implements Container {
	private SocketConnection socketConnection;
	private final int port;
	private final Injector injector;

	public KittenFaceMash(int port) {
		this.port = port;
		injector = createInjector(new GuiceModule() {
			@Override
			protected void configure() {
				bind(Object.class, named("index")).to(IndexController.class);
				bind(Object.class, named("kitten")).to(KittenController.class);
				bind(Object.class, named("vote")).to(VoteController.class);
			}
		});
	}

	@Override
	public void handle(Request req, Response resp) {
		try {
			List<String> path = newArrayList(req.getPath().getSegments());
			String action = getFirst(path, "index");

			invoke(controller(action), "render", arguments(resp, path));
		} finally {
			try {
				resp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Object> arguments(Response resp, List<String> segments) {
		return ImmutableList.builder().add(resp).addAll(skip(segments, 1)).build();
	}

	public Object controller(String action) {
		return injector.getInstance(Key.get(Object.class, named(action)));
	}

	public final static class IndexController {
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

	public final static class VoteController {
		private final Scores scores;

		@Inject
		public VoteController(Scores scores) {
			this.scores = scores;
		}

		public void render(Response resp, String kittenId) {
			scores.win(Integer.parseInt(kittenId));

			resp.add("Location", "/");
			resp.setCode(307);
		}
	}

	public final static class KittenController {
		public void render(Response resp, String kittenId) throws IOException {
			resp.getOutputStream().write(toByteArray(new File(format("kitten/%s.jpg", kittenId))));
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
