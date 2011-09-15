package net.gageot.test;

import java.lang.reflect.Method;
import org.springframework.util.*;
import org.springframework.util.ReflectionUtils.MethodCallback;
import com.google.common.collect.Iterables;

/**
 * Should be into src/main or a dependency jar. I put it here to make the
 * demonstration easier to follow.
 */
public class Reflection {
	private Reflection() {
		// Static utility class
	}

	public static void invoke(final Object target, final String methodName, final Iterable<? extends Object> arguments) {
		ReflectionUtils.doWithMethods(target.getClass(), new MethodCallback() {
			@Override
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				if (method.getName().equals(methodName) && (method.getParameterTypes().length == Iterables.size(arguments))) {
					ReflectionUtils.invokeMethod(method, target, Iterables.toArray(arguments, Object.class));
				}
			}
		});
	}
}
