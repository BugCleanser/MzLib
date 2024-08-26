package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtString"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.StringTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtString")})
public interface NbtString extends NbtElement
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NbtString create(Object wrapped)
    {
        return WrapperObject.create(NbtString.class, wrapped);
    }

    @WrapConstructor
    NbtString staticNewInstance(String value);
    static NbtString newInstance(String value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    String getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(String value);
}
