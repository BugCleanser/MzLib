package mz.mzlib.minecraft.datafixer;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1300)
@WrapMinecraftClass(@VersionName(name = "com.mojang.datafixers.DSL"))
public interface DSLV1300 extends WrapperObject
{
    WrapperFactory<DSLV1300> FACTORY = WrapperFactory.of(DSLV1300.class);
    @Deprecated
    @WrapperCreator
    static DSLV1300 create(Object wrapped)
    {
        return WrapperObject.create(DSLV1300.class, wrapped);
    }

    @WrapMinecraftInnerClass(outer = DSLV1300.class, name = @VersionName(name = "TypeReference"))
    interface TypeReference extends WrapperObject
    {
        WrapperFactory<TypeReference> FACTORY = WrapperFactory.of(TypeReference.class);
        @Deprecated
        @WrapperCreator
        static TypeReference create(Object wrapped)
        {
            return WrapperObject.create(TypeReference.class, wrapped);
        }
    }
}
