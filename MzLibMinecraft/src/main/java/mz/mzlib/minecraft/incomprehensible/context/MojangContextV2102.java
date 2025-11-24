package mz.mzlib.minecraft.incomprehensible.context;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2102)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.util.context.ContextParameterMap"))
public interface MojangContextV2102 extends WrapperObject
{
    WrapperFactory<MojangContextV2102> FACTORY = WrapperFactory.of(MojangContextV2102.class);
}
