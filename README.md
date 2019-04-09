# Java DynaTraits

Traits are reusable components representing a set of methods or behaviors that can be used to extend the functionality of multiple classes. Some programming languages like Groovy and Scala have been supporting Traits for a long time, and allow dynamic association of Traits to any object, at runtime.

Java being a strongly typed programming language, enforces a very strict discipline of type association, upfront whilst declaring any language element, e.g. variables, functions, etc; and the same is applicable for Classes too. Therefore when you implement a class in Java, the associative types for the class, must be specified as a part of the declaration of the class, which strongly binds the type interfaces to the class, and cannot be changed in runtime.

The concept of Traits is implemented in Java using Interfaces, which contain default methods and variables to allow maintaining an object's state. However, these interfaces then have to be tightly coupled with the class declaration, and as discussed above one cannot dynamically modify an interface association.

Java-DynaTraits is a very simple and minimalist library, that allows dynamic association of Traits/Interfaces; such that new interfaces could be added to your class at runtime, without really having to declare them, prior to the compilation of the class.

The core concept behind the code is to use a subscription model, which allows registering interfaces (or in other words Traits) with a class, in runtime, and then using an Invocation Handler to delegate function calls to the relevant subscriptions.

## An Example Code

Following is a sample code, that demonstrates an example usage -

```
var catalogWithTraits = (CatalogInterface & NamedEntity & PricedEntity) (new Catalog()).withTraits(new NamedEntity() {}, new PricedEntity() {});

// The setName function is rendered by the "NamedEntity" trait.
catalogWithTraits.setName("Dynamic Traits with Java");
System.out.println(catalogWithTraits.getName());

// The setPrice function is rendered by the "PricedEntity" trait
catalogWithTraits.setPrice(20.00);
System.out.println(catalogWithTraits.getPrice());
```

And, below is an example Trait implementation -

```
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
```

## Download
A binary distribution (jar) of the library could be found [here](dist/java-dynatraits-1.0.0.jar).
