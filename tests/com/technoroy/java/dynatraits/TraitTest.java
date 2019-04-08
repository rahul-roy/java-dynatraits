package com.technoroy.java.dynatraits;
import com.technoroy.java.dynatraits.DynamicTraitSupport;
import com.technoroy.java.dynatraits.traits.NamedEntity;
import com.technoroy.java.dynatraits.traits.PricedEntity;

public class TraitTest {
	public static void main(String... arguments) throws Exception {
		var catalog = new Catalog();
		var catalogWithTraits = (CatalogInterface & NamedEntity & PricedEntity) catalog.withTraits(new NamedEntity() {}, new PricedEntity() {});
		
		catalogWithTraits.setName("Dynamic Traits with Java");
		System.out.println(catalogWithTraits.getName());
		
		catalogWithTraits.setPrice(20.00);
		System.out.println(catalogWithTraits.getPrice());
		
		catalogWithTraits.print();
	}
}

interface CatalogInterface {
	public void print();
}

class Catalog implements CatalogInterface, DynamicTraitSupport {

	@Override
	public void print() {
		System.out.println("Binder");
	}
}
