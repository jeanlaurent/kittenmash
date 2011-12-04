package net.gageot.kittenmash;

import static com.google.common.collect.Iterables.*;
import static com.google.inject.Guice.createInjector;
import static com.google.inject.name.Names.named;
import static java.util.Arrays.asList;
import static net.gageot.kittenmash.util.Reflection.invoke;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import net.gageot.kittenmash.util.GuiceModule;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.SocketConnection;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AbstractService;
import com.google.inject.Injector;
import com.google.inject.Key;

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
		List<String> path = asList(req.getPath().getSegments());
		String action = getFirst(path, "index");

		try {
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

	public static void main(String[] args) {
    new KittenFaceMash(findAPort()).startAndWait();
  }

  private static int findAPort() {
    String systemPort = System.getenv("PORT");
    if (systemPort != null) {
      return Integer.valueOf(systemPort);
    }
    return 8080;
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
