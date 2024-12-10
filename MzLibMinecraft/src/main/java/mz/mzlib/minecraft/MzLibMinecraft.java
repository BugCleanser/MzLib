package mz.mzlib.minecraft;

import mz.mzlib.event.RegistrarEventListener;
import mz.mzlib.minecraft.command.CommandBuilder;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.MinecraftEventModule;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventoryCustom;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.PacketListenerModule;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.window.*;
import mz.mzlib.module.MzModule;
import mz.mzlib.tester.Tester;
import mz.mzlib.tester.TesterContext;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.wrapper.TesterJarWrappers;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.concurrent.ForkJoinPool;

public class MzLibMinecraft extends MzModule
{
    public static MzLibMinecraft instance = new MzLibMinecraft();
    
    @Override
    public void onLoad()
    {
        try
        {
            this.register(new TesterJarWrappers(MinecraftPlatform.instance.getMzLibJar(), MzLibMinecraft.class.getClassLoader()));
            
            this.register(I18nMinecraft.instance);

            this.register(NothingMinecraftServer.class);

            this.register(RegistrarEventListener.instance);
            this.register(PacketListenerModule.instance);

            this.register(MinecraftEventModule.instance);
            
            this.register(ModuleMapStackTrace.instance);
            
            this.register(ModuleWindow.instance);
            this.register(UIStack.Module.instance);

            Tester.testAll(new TesterContext(), ForkJoinPool.commonPool()).whenComplete((r, e) ->
            {
                if(e != null)
                {
                    e.printStackTrace(System.err);
                    return;
                }
                for(Throwable t : r)
                {
                    t.printStackTrace(System.err);
                }
            });
            
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
