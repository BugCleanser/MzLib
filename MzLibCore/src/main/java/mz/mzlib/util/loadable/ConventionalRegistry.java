package mz.mzlib.util.loadable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ConventionalRegistry<T extends LoadRegistrable> implements LoadableRegistry<T> {
    protected Map<String,T> registered = new HashMap<>();

    @Override
    public boolean register(T registrable) {
        if(registered.containsKey(registrable.getName())){
            return false;
        }
        registered.put(registrable.getName(), registrable);
        return true;
    }

    @Override
    public T getByName(String name) {
        return registered.get(name);
    }

    @Override
    public Set<String> getKeys() {
        return registered.keySet();
    }

    @Override
    public Collection<T> getValues() {
        return registered.values();
    }
}
