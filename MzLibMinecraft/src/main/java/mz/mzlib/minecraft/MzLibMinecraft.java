package mz.mzlib.minecraft;

import mz.mzlib.event.RegistrarEventListener;
import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.commands.CommandGiveNbt;
import mz.mzlib.minecraft.commands.CommandMzLibItemInfo;
import mz.mzlib.minecraft.commands.CommandMzLibLang;
import mz.mzlib.minecraft.event.MinecraftEventModule;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.network.packet.PacketListenerModule;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.book.UIWrittenBook;
import mz.mzlib.minecraft.ui.window.UIWindow;
import mz.mzlib.minecraft.ui.window.UIWindowAnvil;
import mz.mzlib.minecraft.window.ModuleWindow;
import mz.mzlib.module.MzModule;
import mz.mzlib.tester.Tester;
import mz.mzlib.tester.TesterContext;
import mz.mzlib.util.Config;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.TesterJarWrappers;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class MzLibMinecraft extends MzModule
{
    public static MzLibMinecraft instance = new MzLibMinecraft();
    
    public Config config;
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        try
        {
            this.config = Config.load(this.getClass().getResourceAsStream("/config.json"), new File(MinecraftPlatform.instance.getMzLibDataFolder(), "config.json"));
            
            this.register(I18nMinecraft.instance);
            
            this.register(new TesterJarWrappers(MinecraftPlatform.instance.getMzLibJar(), MzLibMinecraft.class.getClassLoader()));
            
            this.register(I18n.load(MinecraftPlatform.instance.getMzLibJar(), "lang", 0));
            
            this.register(NothingMinecraftServer.class);
            
            this.register(this.command = new Command("mzlib", "mz"));
            
            this.register(RegistrarEventListener.instance);
            this.register(PacketListenerModule.instance);
            
            this.register(MinecraftEventModule.instance);
            
            this.register(ModuleMapStackTrace.instance);
            
            this.register(ModuleWindow.instance);
            this.register(UIStack.Module.instance);
            this.register(UIWrittenBook.Module.instance);
            this.register(UIWindow.Module.instance);
            this.register(UIWindowAnvil.Module.instance);
            
            this.register(CommandGiveNbt.instance);
            this.register(CommandMzLibLang.instance);
            this.register(CommandMzLibItemInfo.instance);
            
            MinecraftPlatform.instance.getMzLibLogger().info(I18nMinecraft.getTranslation(MinecraftServer.instance, "mzlib.test.basic.begin"));
            Tester.testAll(new TesterContext(), ForkJoinPool.commonPool()).whenComplete((r, e)->
            {
                if(e!=null)
                {
                    e.printStackTrace(System.err);
                    return;
                }
                for(Throwable t: r)
                {
                    t.printStackTrace(System.err);
                }
                if(r.isEmpty())
                    MinecraftPlatform.instance.getMzLibLogger().info(I18nMinecraft.getTranslation(MinecraftServer.instance, "mzlib.test.basic.success"));
                else
                    MinecraftPlatform.instance.getMzLibLogger().warning(String.format(I18nMinecraft.getTranslation(MinecraftServer.instance, "mzlib.test.basic.fail"), r.size()));
            });
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
