package mz.mzlib.minecraft.registry.entry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.registry.RegistryWrapper", begin=1903))
public interface RegistryEntryLookupV1903 extends WrapperObject
{
    @WrapperCreator
    static RegistryEntryLookupV1903 create(Object wrapped)
    {
        return WrapperObject.create(RegistryEntryLookupV1903.class, wrapped);
    }
    
    @WrapMinecraftInnerClass(outer=RegistryEntryLookupV1903.class, name=@VersionName(name="WrapperLookup"))
    interface class_7874 extends WrapperObject
    {
        @WrapperCreator
        static class_7874 create(Object wrapped)
        {
            return WrapperObject.create(class_7874.class, wrapped);
        }
    }
}
