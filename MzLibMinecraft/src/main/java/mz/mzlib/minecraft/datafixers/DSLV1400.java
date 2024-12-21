package mz.mzlib.minecraft.datafixers;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.datafixers.DSL", begin = 1400))
public interface DSLV1400 extends WrapperObject
{
    @WrapperCreator
    static DSLV1400 create(Object wrapped)
    {
        return WrapperObject.create(DSLV1400.class, wrapped);
    }
    
    @WrapMinecraftInnerClass(outer=DSLV1400.class, name=@VersionName(name="TypeReference"))
    interface TypeReference extends WrapperObject
    {
        @WrapperCreator
        static TypeReference create(Object wrapped)
        {
            return WrapperObject.create(TypeReference.class, wrapped);
        }
    }
}
