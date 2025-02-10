package mz.mzlib.util.loadable;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface LoadableSerializer {
    <T> Object serialize(Field serialized,LoadableSection configurationSection, String path) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException;

    final class MundaneSerializer implements LoadableSerializer {

        @Override
        public <T> Object serialize(Field serialized, LoadableSection configurationSection, String path) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException {
            Class<?> clazz = serialized.getType();
            if(!Loadable.class.isAssignableFrom(clazz)) {
                return configurationSection.getValue(path);
            }
            Loadable o = (Loadable)clazz.getConstructor(String.class).newInstance(path);
            return o.loadFromData(configurationSection);
        }
    }

    final class ListSerializer implements LoadableSerializer {
        @Override
        public <T> Object serialize(Field serialized, LoadableSection configurationSection, String path) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
            Class<?> clazz = serialized.getType();
            if(!List.class.isAssignableFrom(clazz)) {
                return new MundaneSerializer().serialize(serialized,configurationSection,path);
            }
            Class<?> clazzz = Class.forName(serialized.getGenericType().getTypeName());
            if(!Loadable.class.isAssignableFrom(clazzz)) {
                return configurationSection.getKeys(false).stream().map(a->configurationSection.getValue(path+"."+a)).collect(Collectors.toList());
            }
            return configurationSection.getKeys(false).stream().map(a->{
                try {
                Loadable o = (Loadable)clazz.getConstructor(String.class).newInstance(a);
                    return o.loadFromData(configurationSection.getSection(path+"."+a));
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                         NoSuchMethodException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        }
    }

    final class StringMapSerializer implements LoadableSerializer {
        @Override
        public <T> Object serialize(Field serialized, LoadableSection configurationSection, String path) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
            Class<?> clazz = serialized.getType();
            if(!Map.class.isAssignableFrom(clazz)) {
                return new MundaneSerializer().serialize(serialized,configurationSection,path);
            }
            //FIXME generic class obsecury
            Class<?> clazzz = Class.forName(serialized.getGenericType().getTypeName().split(",")[1].replaceAll("<.*>| |>",""));
            if(!Loadable.class.isAssignableFrom(clazzz)) {
                return configurationSection.getSection(path).getKeys(false).stream().collect(Collectors.toMap(a->a,b->configurationSection.getValue(path+"."+b)));
            }
            return configurationSection.getSection(path).getKeys(false).stream()
                    .collect(Collectors.toMap(a->a,b->{
                        try {
                            Loadable o = (Loadable)clazz.getConstructor(String.class).newInstance(b);
                            return o.loadFromData(configurationSection.getSection(path+"."+b));
                        } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                                 NoSuchMethodException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }));
        }
    }

    static LoadableSerializer getSerializer(LoadableField field) throws InstantiationException, IllegalAccessException {
        Class<? extends LoadableSerializer> serializerClazz = field.loadableSerializer();
        LoadableSerializer o = serializerClazz.newInstance();
        return o;
    }
}
