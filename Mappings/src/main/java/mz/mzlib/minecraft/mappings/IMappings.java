package mz.mzlib.minecraft.mappings;

public interface IMappings
{
    String mapClass0(String from);
    default String mapClass(String from)
    {
        String result=this.mapClass0(from);
        if(result!=null)
            return result;
        return from;
    }
    String mapField0(String fromClass,String fromField);
    default String mapField(String fromClass,String fromField)
    {
        String result=this.mapField0(fromClass,fromField);
        if(result!=null)
            return result;
        return fromField;
    }
    String mapMethod0(String fromClass, MappingMethod fromMethod);
    default String mapMethod(String fromClass, MappingMethod fromMethod)
    {
        String result=this.mapMethod0(fromClass,fromMethod);
        if(result!=null)
            return result;
        return fromMethod.name;
    }
    default String mapType(String desc)
    {
        if(desc.startsWith("["))
            return "[" + this.mapType(desc.substring(1));
        else if(desc.startsWith("L"))
            return "L" + this.mapClass(desc.substring(1, desc.length() - 1).replace('/','.')).replace('.','/') + ";";
        else
            return desc;
    }
}
