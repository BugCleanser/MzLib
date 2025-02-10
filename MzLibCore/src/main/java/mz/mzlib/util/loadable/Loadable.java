package mz.mzlib.util.loadable;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public interface Loadable {
    default  <T extends Loadable>T loadFromData(LoadableSection configurationSection,boolean withName) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        for(Field field:this.getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(LoadableField.class)){
                LoadableField loadableField = field.getAnnotation(LoadableField.class);
                field.setAccessible(true);
                String name = loadableField.name().equalsIgnoreCase("")?field.getName():loadableField.name();
                String path = withName?getName()+".":"";
                if(!loadableField.path().equalsIgnoreCase("")) {
                    path = path + loadableField.path();
                }
//                Object object = configurationSection.get((withName?(getName()+"."):"")+loadableField.path()+field.getName());
                LoadableSerializer loadableSerializer = LoadableSerializer.getSerializer(loadableField);
                Object object = loadableSerializer.serialize(field, configurationSection.getSection(path),name);
                field.set(this,object);
            }
        }
        return (T)this;
    }

    default <T extends Loadable>T loadFromData(LoadableSection configurationSection) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        return loadFromData(configurationSection,true);
    }

    String getName();
    default String getPath(){
        return getName();
    }
}
