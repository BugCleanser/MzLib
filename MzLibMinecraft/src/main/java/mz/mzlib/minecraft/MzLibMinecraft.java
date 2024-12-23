package mz.mzlib.minecraft;

import mz.mzlib.event.RegistrarEventListener;
import mz.mzlib.minecraft.commands.CommandGiveNbt;
import mz.mzlib.minecraft.event.MinecraftEventModule;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.network.packet.PacketListenerModule;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.window.ModuleWindow;
import mz.mzlib.module.MzModule;
import mz.mzlib.tester.Tester;
import mz.mzlib.tester.TesterContext;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.TesterJarWrappers;

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
            
            this.register(CommandGiveNbt.instance);

            System.out.println("开始进行基本测试"); // TODO: i18n
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
                System.out.println("测试结束"); // TODO: i18n
            });
            
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
