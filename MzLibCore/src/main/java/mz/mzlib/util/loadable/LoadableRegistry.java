package mz.mzlib.util.loadable;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface LoadableRegistry<T extends LoadRegistrable> {
    ConcurrentHashMap<Class<? extends LoadRegistrable>, ConventionalRegistry<?>> conventionalRegistries = new ConcurrentHashMap<>();
    boolean register(T registrable);
    T getByName(String name);
    Set<String> getKeys();
    Collection<T> getValues();

    static <T extends LoadRegistrable>LoadableRegistry<T> getConventionalRegistry(Class<T> clazz) {
        ConventionalRegistry<T> conventionalRegistry = (ConventionalRegistry<T>) conventionalRegistries.get(clazz);
        if(!conventionalRegistries.containsKey(clazz)){
            conventionalRegistry = new ConventionalRegistry<>();
            conventionalRegistries.put(clazz, conventionalRegistry);
        }
        return conventionalRegistry;
    }
}
