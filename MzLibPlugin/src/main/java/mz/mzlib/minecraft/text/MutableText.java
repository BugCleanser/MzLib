package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                // TODO
                @VersionName(name = "net.minecraft.text.MutableText", end=1400)
        })
public interface MutableText extends WrapperObject
{
}
