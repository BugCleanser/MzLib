package mz.mzlib.minecraft;

import mz.mzlib.event.RegistrarEventListener;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.commands.CommandGiveNbt;
import mz.mzlib.minecraft.event.MinecraftEventModule;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.network.packet.PacketListenerModule;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.book.UIWrittenBook;
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
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        try
        {
            this.register(new TesterJarWrappers(MinecraftPlatform.instance.getMzLibJar(), MzLibMinecraft.class.getClassLoader()));
            
            this.register(I18nMinecraft.instance);

            this.register(NothingMinecraftServer.class);
            
            this.register(this.command=new Command("mzlib", "mz"));

            this.register(RegistrarEventListener.instance);
            this.register(PacketListenerModule.instance);

            this.register(MinecraftEventModule.instance);
            
            this.register(ModuleMapStackTrace.instance);
            
            this.register(ModuleWindow.instance);
            this.register(UIStack.Module.instance);;
            this.register(UIWrittenBook.Module.instance);
            
            this.register(CommandGiveNbt.instance);

            MinecraftPlatform.instance.getMzLibLogger().info("开始进行基本测试"); // TODO: i18n
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
                if(r.isEmpty())
                    MinecraftPlatform.instance.getMzLibLogger().info("测试结束，未发现明显异常"); // TODO: i18n
                else
                    MinecraftPlatform.instance.getMzLibLogger().warning("测试结束，共"+r.size()+"个异常，可能无法在此服务器上正确运行"); // TODO: i18n
            });
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
