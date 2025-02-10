package mz.mzlib.minecraft.mappings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MappingsPipe extends Mappings<MappingsPipe>
{
    public List<Mappings<?>> mappings;
    public MappingsPipe(List<Mappings<?>> mappings)
    {
        this.mappings = mappings;
    }
    public MappingsPipe(Mappings<?>... mappings)
    {
        this(Arrays.asList(mappings));
    }
    
    @Override
    public String mapClass0(String from)
    {
        boolean found = false;
        String result = from;
        for(Mappings<?> i: mappings)
        {
            String s = i.mapClass0(result);
            if(s==null)
                continue;
            result = s;
            found = true;
        }
        return found ? result : null;
    }
    public String mapClass(String from)
    {
        String result = from;
        for(Mappings<?> i: mappings)
        {
            result = i.mapClass(result);
        }
        return result;
    }
    public String mapField0(String fromClass, String fromField)
    {
        String clz = fromClass;
        String field = fromField;
        for(Mappings<?> i: mappings)
        {
            field = i.mapField0(clz, field);
            if(field==null)
                return null;
            clz = i.mapClass(clz);
        }
        return field;
    }
    public String mapField(String fromClass, String fromField)
    {
        String clz = fromClass;
        String field = fromField;
        for(Mappings<?> i: mappings)
        {
            field = i.mapField(clz, field);
            clz = i.mapClass(clz);
        }
        return field;
    }
    public String mapMethod0(String fromClass, MappingMethod fromMethod)
    {
        String clz = fromClass;
        MappingMethod method = fromMethod;
        for(Mappings<?> i: mappings)
        {
            String name = i.mapMethod(clz, method);
            if(name==null)
                return null;
            method = new MappingMethod(name, Arrays.stream(method.parameterTypes).map(i::mapType).toArray(String[]::new));
            clz = i.mapClass(clz);
        }
        return method.name;
    }
    public String mapMethod(String fromClass, MappingMethod fromMethod)
    {
        String clz = fromClass;
        MappingMethod method = fromMethod;
        for(Mappings<?> i: mappings)
        {
            method = new MappingMethod(i.mapMethod(clz, method), Arrays.stream(method.parameterTypes).map(i::mapType).toArray(String[]::new));
            clz = i.mapClass(clz);
        }
        return method.name;
    }
    @Override
    protected MappingsPipe invert()
    {
        List<Mappings<?>> result = new ArrayList<>();
        for(int i = this.mappings.size()-1; i>=0; i--)
        {
            result.add(this.mappings.get(i).inverse());
        }
        return new MappingsPipe(result);
    }
}
