package net.gageot.kittenmash;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.SocketConnection;
import com.google.common.util.concurrent.AbstractService;

public class HelloWorld extends AbstractService implements Container {
	private SocketConnection socketConnection;
	private final int port;

	public HelloWorld(int port) {
		this.port = port;
	}

	@Override
	public void handle(Request req, Response resp) {
		try {
			resp.getPrintStream().append("hello world").close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new HelloWorld(8080).startAndWait();
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
