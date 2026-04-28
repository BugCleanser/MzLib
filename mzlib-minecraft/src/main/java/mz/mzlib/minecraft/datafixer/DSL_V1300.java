package mz.mzlib.minecraft.datafixer;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.ApiStatus;

@VersionRange(begin = 1300)
@WrapMinecraftClass(@VersionName(name = "com.mojang.datafixers.DSL"))
@ApiStatus.Experimental
public interface DSL_V1300 extends WrapperObject
{
    WrapperFactory<DSL_V1300> FACTORY = WrapperFactory.of(DSL_V1300.class);
    @WrapMinecraftInnerClass(outer = DSL_V1300.class, name = @VersionName(name = "TypeReference"))
    interface TypeReference extends WrapperObject
    {
        WrapperFactory<TypeReference> FACTORY = WrapperFactory.of(TypeReference.class);
    }
}
