package net.gageot.kittenmash;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.simpleframework.http.*;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.SocketConnection;

public class HelloWorld {
	public String sayHello() {
		return "hello world";
	}

	public static void main(String[] args) throws Exception {
		new SocketConnection(new Container() {
			@Override
			public void handle(Request req, Response resp) {
				try {
					resp.getPrintStream().append("hello world").close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).connect(new InetSocketAddress(8080));
	}
}
