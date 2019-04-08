package com.technoroy.java.dynatraits.traits;


import java.util.HashMap;
import java.util.Map;

public interface Document {
	final Map<String, Object> attributes = new HashMap<>();

	default void put(String attributeName, Object value) {
		attributes.put(attributeName, value);
	}

	default Object get(String attributeName) {
		return attributes.get(attributeName);
	}
}
