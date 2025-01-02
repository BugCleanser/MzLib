package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.window.ModuleWindow;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.nothing.LocalVar;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.basic.WrapperBoolean;

public class BukkitWindowModule extends MzModule
{
    public static BukkitWindowModule instance = new BukkitWindowModule();
    
    @Override
    public void onLoad()
    {
        this.register(NothingWindow.class);
    }
    
    @BukkitOnly
    @WrapSameClass(Window.class)
    public interface NothingWindow extends Window, Nothing
    {
        @WrapMinecraftMethod(@VersionName(name="moveItemStackTo", begin=1700))
        boolean placeInOrCheckV1700(ItemStack itemStack, int begin, int end, boolean inverted, boolean doCheck);
        
        @VersionRange(begin=1700)
        @NothingInject(wrapperMethod="placeInOrCheckV1700", type=NothingInjectType.INSERT_BEFORE, locateMethod="")
        default WrapperBoolean placeInOrCheckOverwriteV1700(@LocalVar(1) ItemStack itemStack, @LocalVar(2) int begin, @LocalVar(3) int end, @LocalVar(4) boolean inverted, @LocalVar(5) boolean doCheck)
        {
            return WrapperBoolean.create(this.castTo(ModuleWindow.NothingWindow::create).placeInOrCheck(itemStack, begin, end, inverted, doCheck));
        }
    }
}
