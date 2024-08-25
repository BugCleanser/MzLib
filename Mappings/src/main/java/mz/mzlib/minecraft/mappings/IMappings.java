package mz.mzlib.minecraft.mappings;

public interface IMappings
{
    String mapClass(String from);
    String mapField(String fromClass,String fromField);
    String mapMethod(String fromClass, MappingMethod fromMethod);
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
