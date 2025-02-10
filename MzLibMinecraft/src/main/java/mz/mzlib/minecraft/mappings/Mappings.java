package mz.mzlib.minecraft.mappings;

import mz.mzlib.util.Invertible;

public abstract class Mappings<U extends Mappings<? extends Mappings<U>>> extends Invertible<U>
{
    public abstract String mapClass0(String from);
    public String mapClass(String from)
    {
        String result=this.mapClass0(from);
        if(result!=null)
            return result;
        return from;
    }
    public abstract String mapField0(String fromClass,String fromField);
    public String mapField(String fromClass,String fromField)
    {
        String result=this.mapField0(fromClass,fromField);
        if(result!=null)
            return result;
        return fromField;
    }
    public abstract String mapMethod0(String fromClass, MappingMethod fromMethod);
    public String mapMethod(String fromClass, MappingMethod fromMethod)
    {
        String result=this.mapMethod0(fromClass,fromMethod);
        if(result!=null)
            return result;
        return fromMethod.name;
    }
    public String mapType(String desc)
    {
        if(desc.startsWith("["))
            return "[" + this.mapType(desc.substring(1));
        else if(desc.startsWith("L"))
            return "L" + this.mapClass(desc.substring(1, desc.length() - 1).replace('/','.')).replace('.','/') + ";";
        else
            return desc;
    }
}
