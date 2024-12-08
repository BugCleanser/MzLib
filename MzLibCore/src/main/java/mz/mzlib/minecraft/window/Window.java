package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.screen.ScreenHandler", end=1400), @VersionName(name="net.minecraft.container.Container",begin=1400, end=1600), @VersionName(name="net.minecraft.screen.ScreenHandler", begin=1600)})
public interface Window extends WrapperObject
{
    @WrapperCreator
    static Window create(Object wrapped)
    {
        return WrapperObject.create(Window.class, wrapped);
    }
    
    
}
