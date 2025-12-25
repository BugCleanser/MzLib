package mz.mzlib.minecraft.mappings;

public class MappingsMerged extends Mappings<MappingsMerged>
{
    Mappings<?> first, second;

    public MappingsMerged(Mappings<?> first, Mappings<?> second)
    {
        this.first = first;
        this.second = second;
    }

    @Override
    public String mapClass0(String from)
    {
        String result = this.first.mapClass0(from);
        if(result != null)
            return result;
        return this.second.mapClass0(from);
    }
    @Override
    public String mapField0(String fromClass, String fromField)
    {
        String result = this.first.mapField0(fromClass, fromField);
        if(result != null)
            return result;
        result = this.second.mapField0(fromClass, fromField);
        if(result != null)
            return result;
        result = this.first.mapClass(fromClass);
        if(result == null)
            return null;
        result = this.second.inverse().mapClass(result);
        if(result == null)
            return null;
        return this.second.mapField0(result, fromField);
    }
    @Override
    public String mapMethod0(String fromClass, MappingMethod fromMethod)
    {
        String result = this.first.mapMethod0(fromClass, fromMethod);
        if(result != null)
            return result;
        result = this.second.mapMethod0(fromClass, fromMethod);
        if(result != null)
            return result;
        result = this.first.mapClass(fromClass);
        if(result == null)
            return null;
        result = this.second.inverse().mapClass(result);
        if(result == null)
            return null;
        return this.second.mapMethod0(result, fromMethod);
    }

    @Override
    protected MappingsMerged invert()
    {
        return new MappingsMerged(this.first.inverse(), this.second.inverse());
    }
}
