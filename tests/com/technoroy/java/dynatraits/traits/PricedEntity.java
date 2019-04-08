package com.technoroy.java.dynatraits.traits;
import com.technoroy.java.dynatraits.Trait;

public interface PricedEntity extends Document, Trait {
	final String PRICE = "price";

	public default Double getPrice() {
		return (Double) get(PRICE);
	}

	public default void setPrice(Double price) {
		put(PRICE, price);
	}
}