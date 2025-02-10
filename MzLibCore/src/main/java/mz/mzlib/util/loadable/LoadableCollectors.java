package mz.mzlib.util.loadable;


import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;

public class LoadableCollectors {
    public static <T extends Loadable>Map<String,T> collectLoadableMap(Class<T> clazz, LoadableSection configurationSection){
        //TODO
        return configurationSection.getKeys(false).stream().collect(Collectors.toMap(key -> key,name-> {
            try {
                return getLoadable(clazz,name).loadFromData(configurationSection);
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public static <T extends Loadable>T getLoadable(Class<T> clazz,String name) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clazz.getConstructor(String.class).newInstance(name);
    }

    public static <T extends LoadRegistrable>T getLoadRegistrable(Class<T> clazz, String name) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clazz.getConstructor(String.class).newInstance(name);
    }

    public static <T extends LoadRegistrable>void registerAll(Class<T> clazz, LoadableSection configurationSection){
        configurationSection.getKeys(false).stream().forEach(name-> {
            try {
                ((T)getLoadRegistrable(clazz,name).loadFromData(configurationSection)).register();
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //FIXME
    public static <T extends LoadRegistrable>void registerAll(Class<T> clazz,LoadableSection configurationSection, LoadableRegistry<T> registry){
        configurationSection.getKeys(false).stream().forEach(name-> {
            try {
                ((T)getLoadRegistrable(clazz,name).loadFromData(configurationSection)).register(registry);
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
