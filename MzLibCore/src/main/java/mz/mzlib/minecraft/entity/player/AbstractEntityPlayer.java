package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.EntityLiving;
import mz.mzlib.minecraft.window.WindowFactory;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.OptionalInt;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.player.PlayerEntity"))
public interface AbstractEntityPlayer extends WrapperObject, EntityLiving
{
    @WrapperCreator
    static AbstractEntityPlayer create(Object wrapped)
    {
        return WrapperObject.create(AbstractEntityPlayer.class, wrapped);
    }
    
    @WrapMinecraftMethod({@VersionName(name="openHandledScreen", end=1400), @VersionName(name="openContainer", begin=1400, end=1600), @VersionName(name="openHandledScreen", begin=1600)})
    OptionalInt openWindow(WindowFactory windowFactory);
}
