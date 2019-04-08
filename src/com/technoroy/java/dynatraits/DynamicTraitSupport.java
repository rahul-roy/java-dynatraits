package com.technoroy.java.dynatraits;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DynamicTraitSupport {
	Map<String, Object> traitsRegistry = new HashMap<>();

	// Default invocation handler implementation
	class DynamicInvocationHandler implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// System.out.println("Invoked method: {} " +
			// method.getDeclaringClass().getSimpleName() + " -> " + method.getName());
			Object targetClassName = method.getDeclaringClass().getName();
			Object targetObject = traitsRegistry.get(targetClassName);

			return method.invoke(targetObject, args);
		}
	}

	public default Object withTraits(Trait... traits) throws Exception {
		List<Class<?>> classes = getRelevantClasses(traits);

		return Proxy.newProxyInstance(Trait.class.getClassLoader(), classes.toArray(new Class[classes.size()]),
		        new DynamicInvocationHandler());
	}

	public default List<Class<?>> getRelevantClasses(Trait... traits) {
		List<Class<?>> classes = new ArrayList<>();

		// Get Interfaces from the current class
		for (Class<?> interfaceRef : this.getClass().getInterfaces()) {
			classes.add(interfaceRef);
			traitsRegistry.put(interfaceRef.getName(), this);
		}

		// Get Interfaces from the arguments
		for (Trait trait : traits) {
			for (Class<?> interfaceRef : trait.getClass().getInterfaces()) {
				classes.add(interfaceRef);
				traitsRegistry.put(interfaceRef.getName(), trait);
			}
		}

		return classes;
	}
}
