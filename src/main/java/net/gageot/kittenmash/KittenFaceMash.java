package net.gageot.kittenmash;

import static com.google.common.collect.Iterables.*;
import static com.google.common.collect.Lists.*;
import static com.google.inject.Guice.*;
import static com.google.inject.name.Names.*;
import static net.gageot.test.Reflection.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.SocketConnection;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AbstractService;
import com.google.inject.*;

public class KittenFaceMash extends AbstractService implements Container {
	private SocketConnection socketConnection;
	private final int port;
	private final Injector injector;

	public KittenFaceMash(int port) {
		this.port = port;
		injector = createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(Key.get(Object.class, named("index"))).to(IndexController.class);
				bind(Key.get(Object.class, named("kitten"))).to(KittenController.class);
				bind(Key.get(Object.class, named("vote"))).to(VoteController.class);
			}
		});
	}

	@Override
	public void handle(Request req, Response resp) {
		try {
			List<String> path = newArrayList(req.getPath().getSegments());
			String action = getFirst(path, "index");

			invoke(controllerForAction(action), "render", arguments(resp, path));
		} finally {
			try {
				resp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ImmutableList<Object> arguments(Response resp, List<String> path) {
		return ImmutableList.builder().add(resp).addAll(skip(path, 1)).build();
	}

	public Object controllerForAction(String action) {
		return injector.getInstance(Key.get(Object.class, named(action)));
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
