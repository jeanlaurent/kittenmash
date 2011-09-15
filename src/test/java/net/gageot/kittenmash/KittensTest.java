package net.gageot.kittenmash;

import static org.fest.assertions.Assertions.*;
import org.junit.Test;

public class KittensTest {
	@Test
	public void canSayHello() {
		Kittens kittens = new Kittens();

		assertThat(kittens.sayHello()).isEqualTo("hello kittens");
	}
}
