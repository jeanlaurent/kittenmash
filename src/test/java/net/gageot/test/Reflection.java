package net.gageot.test;

import java.lang.reflect.Method;
import java.util.List;
import org.springframework.util.*;
import org.springframework.util.ReflectionUtils.MethodCallback;

/**
 * Should be into src/main or a dependency jar. I put it here to make the
 * demonstration easier to follow.
 */
public class Reflection {
	private Reflection() {
		// Static utility class
	}

	public static void invoke(final Object target, final String methodName, final List<Object> arguments) {
		ReflectionUtils.doWithMethods(target.getClass(), new MethodCallback() {
			@Override
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				if (method.getName().equals(methodName) && (method.getParameterTypes().length == arguments.size())) {
					ReflectionUtils.invokeMethod(method, target, arguments.toArray());
				}
			}
		});
	}
}
