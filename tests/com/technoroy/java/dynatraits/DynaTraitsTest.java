package com.technoroy.java.dynatraits;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.technoroy.java.dynatraits.traits.NamedEntity;
import com.technoroy.java.dynatraits.traits.PricedEntity;

class DynaTraitsTest {

	@Test
	void testTraitFunctionCalls() {
		try {
			var catalogWithTraits = (CatalogInterface & NamedEntity & PricedEntity) (new Catalog())
					.withTraits(
							new NamedEntity() {},
							new PricedEntity() {}
					);

			// Verify function call from the NamedEntity trait
			String name = "Dynamic Traits with Java";
			catalogWithTraits.setName(name);
			assert catalogWithTraits.getName().equals(name);

			// Verify function call from the PricedEntity trait
			double price = 20.00;
			catalogWithTraits.setPrice(price);
			assert catalogWithTraits.getPrice().equals(price);

			// Verify a function call from the Catalog class itself.
			assert catalogWithTraits.isConfidential() == true;
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void testFunctionCallsFromClass() {
		try {
			var catalogWithTraits = (CatalogInterface & NamedEntity & PricedEntity) (new Catalog())
					.withTraits(
							new NamedEntity() {},
							new PricedEntity() {}
					);

			// Verify a function call from the Catalog class itself.
			assert catalogWithTraits.isConfidential() == true;
		} catch (Exception e) {
			fail(e);
		}
	}

	/**
	 * An interface that declares the public methods for the implementation
	 */
	interface CatalogInterface {
		public boolean isConfidential();
	}

	/**
	 * A simple class that implements DynamicTraitSupport
	 */
	class Catalog implements CatalogInterface, DynamicTraitSupport {
		@Override
		public boolean isConfidential() {
			return true;
		}
	}
}
