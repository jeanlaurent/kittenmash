package net.gageot.kittenmash;

import static org.fest.assertions.Assertions.*;
import org.junit.Test;

public class KittenFaceMashTest {
	@Test
	public void canSayHello() {
		KittenFaceMash server = new KittenFaceMash();

		assertThat(server.sayHello()).isEqualTo("hello world");
	}
}
