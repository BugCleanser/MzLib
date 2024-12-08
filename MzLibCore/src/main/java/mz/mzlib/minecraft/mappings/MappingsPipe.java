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
    
    @Override
    public String mapClass0(String from)
    {
        String result=from;
        for(IMappings i:mappings)
        {
            result = i.mapClass0(result);
            if(result==null)
                return null;
        }
        return result;
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
    public String mapField0(String fromClass,String fromField)
    {
        String clz=fromClass;
        String field=fromField;
        for(IMappings i:mappings)
        {
            field=i.mapField0(clz,field);
            if(field==null)
                return null;
            clz=i.mapClass(clz);
        }
        return field;
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
    public String mapMethod0(String fromClass, MappingMethod fromMethod)
    {
        String clz=fromClass;
        MappingMethod method=fromMethod;
        for(IMappings i:mappings)
        {
            String name=i.mapMethod(clz,method);
            if(name==null)
                return null;
            method=new MappingMethod(name,Arrays.stream(method.parameterTypes).map(i::mapType).toArray(String[]::new));
            clz=i.mapClass(clz);
        }
        return method.name;
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
