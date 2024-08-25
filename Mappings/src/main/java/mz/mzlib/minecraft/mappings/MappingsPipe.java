package mz.mzlib.minecraft.mappings;

import java.util.Arrays;
import java.util.List;

public class MappingsPipe implements IMappings
{
    public List<IMappings> mappings;
    public MappingsPipe(List<IMappings> mappings)
    {
        this.mappings=mappings;
    }
    public MappingsPipe(IMappings ...mappings)
    {
        this(Arrays.asList(mappings));
    }

    public String mapClass(String from)
    {
        String result=from;
        for(IMappings i:mappings)
        {
            result = i.mapClass(result);
        }
        return result;
    }
    public String mapField(String fromClass,String fromField)
    {
        String clz=fromClass;
        String field=fromField;
        for(IMappings i:mappings)
        {
            field=i.mapField(clz,field);
            clz=i.mapClass(clz);
        }
        return field;
    }
    public String mapMethod(String fromClass, MappingMethod fromMethod)
    {
        String clz=fromClass;
        MappingMethod method=fromMethod;
        for(IMappings i:mappings)
        {
            method=new MappingMethod(i.mapMethod(clz,method),Arrays.stream(method.parameterTypes).map(i::mapType).toArray(String[]::new));
            clz=i.mapClass(clz);
        }
        return method.name;
    }
}
