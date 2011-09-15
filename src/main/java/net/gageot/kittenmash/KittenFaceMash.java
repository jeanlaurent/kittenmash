package net.gageot.kittenmash;

import static com.google.common.collect.Iterables.*;
import static com.google.common.collect.Lists.*;
import static com.google.inject.Guice.*;
import static net.gageot.test.Reflection.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.SocketConnection;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AbstractService;
import com.google.inject.Injector;

public class KittenFaceMash extends AbstractService implements Container {
	private SocketConnection socketConnection;
	private final int port;
	private final Injector injector;

	public KittenFaceMash(int port) {
		this.port = port;
		injector = createInjector();
	}

	@Override
	public void handle(Request req, Response resp) {
		List<String> path = newArrayList(req.getPath().getSegments());
		String action = getFirst(path, "index");

		try {
			Object controller;
			if ("kitten".equals(action)) {
				controller = injector.getInstance(KittenController.class);
			} else if ("vote".equals(action)) {
				controller = injector.getInstance(VoteController.class);
			} else {
				controller = injector.getInstance(IndexController.class);
			}

			invoke(controller, "render", ImmutableList.builder().add(resp).addAll(skip(path, 1)).build());
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
