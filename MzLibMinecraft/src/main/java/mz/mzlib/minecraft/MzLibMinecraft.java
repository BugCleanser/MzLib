package mz.mzlib.minecraft;

import mz.mzlib.event.RegistrarEventListener;
import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.commands.CommandGiveNbt;
import mz.mzlib.minecraft.commands.CommandMzLibItemInfo;
import mz.mzlib.minecraft.commands.CommandMzLibLang;
import mz.mzlib.minecraft.commands.CommandMzLibTest;
import mz.mzlib.minecraft.entity.display.DisplayEntityTracker;
import mz.mzlib.minecraft.event.MinecraftEventModule;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.mzitem.RegistrarMzItem;
import mz.mzlib.minecraft.network.packet.ModulePacketListener;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.book.UIWrittenBook;
import mz.mzlib.minecraft.ui.window.UIWindow;
import mz.mzlib.minecraft.ui.window.UIWindowAnvil;
import mz.mzlib.minecraft.window.ModuleWindow;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Config;
import mz.mzlib.util.IOUtil;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.TesterJarWrappers;

import java.io.File;
import java.io.InputStream;

public class MzLibMinecraft extends MzModule
{
    public static MzLibMinecraft instance = new MzLibMinecraft();
    
    public final String modId = "mzlib";
    
    public Config config;
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        try
        {
            try(InputStream is = IOUtil.openFileInZip(MinecraftPlatform.instance.getMzLibJar(), "config.json"))
            {
                this.config = Config.load(is, new File(MinecraftPlatform.instance.getMzLibDataFolder(), "config.json"));
            }
            
            this.register(ModuleMapStackTrace.instance);
            
            this.register(I18n.load(MinecraftPlatform.instance.getMzLibJar(), "lang", 0));
            
            this.register(I18nMinecraft.instance);
            
            this.register(new TesterJarWrappers(MinecraftPlatform.instance.getMzLibJar(), MzLibMinecraft.class.getClassLoader()));
            
            this.register(NothingMinecraftServer.class);
            
            this.register(MinecraftServer.instance.getCommandManager());
            
            this.register(this.command = new Command("mzlib", "mz").setNamespace(this.modId));
            
            this.register(RegistrarEventListener.instance);
            this.register(ModulePacketListener.instance);
            
            this.register(MinecraftEventModule.instance);
            
            this.register(DisplayEntityTracker.Module.instance);
            
            this.register(ModuleWindow.instance);
            this.register(UIStack.Module.instance);
            this.register(UIWrittenBook.Module.instance);
            this.register(UIWindow.Module.instance);
            this.register(UIWindowAnvil.Module.instance);
            
            this.register(CommandMzLibTest.instance);
            this.register(CommandMzLibLang.instance);
            this.register(CommandMzLibItemInfo.instance);
            this.register(CommandGiveNbt.instance);
            
            this.register(RegistrarMzItem.instance);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
