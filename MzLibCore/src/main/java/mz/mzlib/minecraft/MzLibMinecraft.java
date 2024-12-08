package mz.mzlib.minecraft;

import mz.mzlib.event.RegistrarEventListener;
import mz.mzlib.minecraft.command.CommandBuilder;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.MinecraftEventModule;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.inventory.InventoryCustom;
import mz.mzlib.minecraft.network.packet.PacketListenerModule;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.window.WindowFactoryCustom;
import mz.mzlib.minecraft.window.WindowGeneric;
import mz.mzlib.minecraft.window.WindowTypeV1400;
import mz.mzlib.module.MzModule;
import mz.mzlib.tester.Tester;
import mz.mzlib.tester.TesterContext;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.TesterJarWrappers;
import net.minecraft.server.v1_12_R1.ContainerChest;

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
            this.register(UIStack.Module.instance);
            
            InventoryCustom test = InventoryCustom.newInstance(9*3);
            this.register(new CommandBuilder("mzlib", "mz").addChild(new CommandBuilder("test", "t").addExecutor((sender, command, args)->
            {
                if(sender.isInstanceOf(EntityPlayer::create))
                    sender.castTo(EntityPlayer::create).openWindow(WindowFactoryCustom.newInstance("minecraft:chest", Text.literal("Test"), (id, inventoryPlayer)->WindowGeneric.newInstance(MinecraftPlatform.instance.getVersion()<1400?null:WindowTypeV1400.generic_9x3(), id, inventoryPlayer, test, 3)));
                return Text.literal("Hello World!");
            }).build()).build());
            
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
