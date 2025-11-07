package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.window.ModuleWindow;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.nothing.LocalVar;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.basic.WrapperBoolean;

public class ModuleBukkitWindow extends MzModule
{
    public static ModuleBukkitWindow instance = new ModuleBukkitWindow();

    @Override
    public void onLoad()
    {
        if(ElementSwitcher.isEnabled(NothingWindow.class))
            this.register(NothingWindow.class);
    }

    @MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
    @MinecraftPlatform.Disabled(MinecraftPlatform.Tag.FABRIC)
    @WrapSameClass(Window.class)
    public interface NothingWindow extends Window, Nothing
    {
        @VersionRange(begin = 1701)
        @WrapMinecraftMethod(@VersionName(name = "moveItemStackTo"))
        boolean placeInOrCheckV1701(ItemStack itemStack, int begin, int end, boolean inverted, boolean doCheck);

        @VersionRange(begin = 1701)
        @NothingInject(wrapperMethodName = "placeInOrCheckV1701", wrapperMethodParams = {
            ItemStack.class,
            int.class,
            int.class,
            boolean.class,
            boolean.class
        }, type = NothingInjectType.INSERT_BEFORE, locateMethod = "")
        default WrapperBoolean placeInOrCheckOverwriteV1701(
            @LocalVar(1) ItemStack itemStack,
            @LocalVar(2) int begin,
            @LocalVar(3) int end,
            @LocalVar(4) boolean inverted,
            @LocalVar(5) boolean doCheck)
        {
            return WrapperBoolean.FACTORY.create(this.castTo(ModuleWindow.NothingWindow.FACTORY)
                .placeInOrCheck(itemStack, begin, end, inverted, doCheck));
        }
    }
}
