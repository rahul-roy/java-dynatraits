package com.technoroy.java.dynatraits;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DynamicTraitSupport {

    /**
     * A custom InvocationHandler that delegates method calls to the
     * backing objects (this class or provided traits) based on the
     * method's declaring interface.
     */
    class DynamicInvocationHandler implements InvocationHandler {

        private final Map<Class<?>, Object> traitMapping;

        public DynamicInvocationHandler(Map<Class<?>, Object> traitMapping) {
            this.traitMapping = traitMapping;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Class<?> declaringClass = method.getDeclaringClass();
            Object target = traitMapping.get(declaringClass);

            // If we can't find an object handling this interface,
            // need to decide how you want to handle this case:
            if (target == null) {
                // for now throwing an exception
                throw new UnsupportedOperationException(
                    "No trait found for " + declaringClass.getName() + 
                    " to invoke method " + method.getName()
                );
            }

            return method.invoke(target, args);
        }
    }

    /**
     * Create a dynamic proxy that merges the interfaces of 'this' object
     * and any number of Trait instances. The returned Object implements all
     * those interfaces, and delegates method calls appropriately.
     *
     * @param traits One or more Trait instances
     * @return A dynamic proxy that implements this object's and the traits' interfaces
     */
    default Object withTraits(Trait... traits) {
        // Collect interface -> instance mappings
        Map<Class<?>, Object> traitMapping = new HashMap<>();
        List<Class<?>> interfaces = new ArrayList<>();

        // 1. Add interfaces from 'this' object
        for (Class<?> iface : this.getClass().getInterfaces()) {
            interfaces.add(iface);
            traitMapping.put(iface, this);
        }

        // 2. Add interfaces from all traits
        for (Trait trait : traits) {
            for (Class<?> iface : trait.getClass().getInterfaces()) {
                interfaces.add(iface);
                traitMapping.put(iface, trait);
            }
        }

        // Create the proxy
        return Proxy.newProxyInstance(
            this.getClass().getClassLoader(),
            interfaces.toArray(new Class[0]),
            new DynamicInvocationHandler(traitMapping)
        );
    }
}
