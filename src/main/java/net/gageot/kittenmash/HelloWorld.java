package net.gageot.kittenmash;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.SocketConnection;

public class HelloWorld implements Container {
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

	public static void main(String[] args) throws Exception {
		new HelloWorld(8080).run();
	}

	public void run() throws IOException {
		socketConnection = new SocketConnection(this);
		socketConnection.connect(new InetSocketAddress(port));
	}
}
