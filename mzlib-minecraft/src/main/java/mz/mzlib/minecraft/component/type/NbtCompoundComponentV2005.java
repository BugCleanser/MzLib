package mz.mzlib.minecraft.component.type;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.component.type.NbtComponent", begin = 2005))
public interface NbtCompoundComponentV2005 extends WrapperObject
{
    WrapperFactory<NbtCompoundComponentV2005> FACTORY = WrapperFactory.of(NbtCompoundComponentV2005.class);
    @Deprecated
    @WrapperCreator
    static NbtCompoundComponentV2005 create(Object wrapped)
    {
        return WrapperObject.create(NbtCompoundComponentV2005.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "nbt"))
    NbtCompound getNbtCompound();

    @WrapConstructor
    NbtCompoundComponentV2005 static$newInstance(NbtCompound nbtCompound);
    static NbtCompoundComponentV2005 newInstance(NbtCompound nbtCompound)
    {
        return FACTORY.getStatic().static$newInstance(nbtCompound);
    }
}
