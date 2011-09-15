package net.gageot.kittenmash;

import static org.fest.assertions.Assertions.*;
import org.junit.Test;

public class HelloWorldTest {
	@Test
	public void canSayHello() {
		HelloWorld helloWorld = new HelloWorld();

		assertThat(helloWorld.sayHello()).isEqualTo("hello world");
	}
}
