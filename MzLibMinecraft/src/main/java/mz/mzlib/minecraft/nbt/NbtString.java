package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({
    @VersionName(end = 1400, name = "net.minecraft.nbt.NbtString"),
    @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.StringTag"),
    @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtString")
})
public interface NbtString extends NbtElement
{
    WrapperFactory<NbtString> FACTORY = WrapperFactory.of(NbtString.class);
    @Deprecated
    @WrapperCreator
    static NbtString create(Object wrapped)
    {
        return WrapperObject.create(NbtString.class, wrapped);
    }

    @WrapConstructor
    NbtString static$newInstance(String value);
    static NbtString newInstance(String value)
    {
        return FACTORY.getStatic().static$newInstance(value);
    }

    @WrapMinecraftFieldAccessor({
        @VersionName(name = "value", end = 2105),
        @VersionName(name = "comp_3831", begin = 2105)
    })
    String getValue();

    @Deprecated
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "value", end = 2105),
        @VersionName(name = "comp_3831", begin = 2105)
    })
    void setValue(String value);
}
