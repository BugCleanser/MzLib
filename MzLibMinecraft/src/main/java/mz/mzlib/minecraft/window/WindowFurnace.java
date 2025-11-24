package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.screen.FurnaceScreenHandler", end = 1400),
    @VersionName(name = "net.minecraft.class_3858", begin = 1400) // net.minecraft.screen.FurnaceScreenHandler
})
public interface WindowFurnace extends Window
{
    WrapperFactory<WindowFurnace> FACTORY = WrapperFactory.of(WindowFurnace.class);
}
