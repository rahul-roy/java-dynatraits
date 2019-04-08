package com.technoroy.java.dynatraits.traits;
import com.technoroy.java.dynatraits.Trait;

public interface NamedEntity extends Document, Trait {
	final String NAME = "name";

	public default String getName() {
		return (String) get(NAME);
	}

	public default void setName(String name) {
		put(NAME, name);
	}
}