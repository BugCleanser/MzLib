package mz.mzlib.minecraft.mappings;

public abstract class MappingsClasses<U extends Mappings<? extends MappingsClasses<U>>> extends Mappings<U>
{
    @Override
    public String mapField0(String fromClass, String fromField)
    {
        return fromField;
    }
    @Override
    public String mapMethod0(String fromClass, MappingMethod fromMethod)
    {
        return fromMethod.name;
    }
}
