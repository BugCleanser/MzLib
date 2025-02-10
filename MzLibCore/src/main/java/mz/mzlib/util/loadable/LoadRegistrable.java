package mz.mzlib.util.loadable;

public interface LoadRegistrable extends Loadable {
    //TODO
    default boolean register(){
        return register(LoadableRegistry.getConventionalRegistry(this.getClass()));
    }

    default <K extends LoadRegistrable,T extends LoadableRegistry<K>>boolean register(T registry){
        return registry.register((K) this);
    }
}
