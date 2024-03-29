package mz.lib.minecraft.bukkit;

import io.github.karlatemp.unsafeaccessor.ModuleAccess;
import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.ClassUtil;
import mz.lib.FileUtil;
import mz.lib.minecraft.bukkit.command.IMainCommand;
import mz.lib.minecraft.bukkit.command.MainCommand;
import mz.lib.minecraft.bukkit.command.argparser.ArgParserRegistrar;
import mz.lib.minecraft.bukkit.entity.EntityViewWatcher;
import mz.lib.minecraft.bukkit.event.*;
import mz.lib.minecraft.bukkit.gui.ViewList;
import mz.lib.minecraft.bukkit.gui.inventory.AnvilUI;
import mz.lib.minecraft.bukkit.gui.inventory.InventoryUI;
import mz.lib.minecraft.bukkit.item.MzItemProcessor;
import mz.lib.minecraft.bukkit.item.MzItemRegistrar;
import mz.lib.minecraft.bukkit.item.OriginalItemFilterRegistrar;
import mz.lib.minecraft.bukkit.item.map.MzMapProcessor;
import mz.lib.minecraft.bukkit.module.IModule;
import mz.lib.minecraft.bukkit.module.RegistrarRegistrar;
import mz.lib.minecraft.bukkit.mzlibcommand.MzLibCommandModule;
import mz.lib.minecraft.bukkit.mzlibcommand.SundayCommand;
import mz.lib.minecraft.bukkit.mzlibcommand.debug.DebugSlotCommand;
import mz.lib.minecraft.bukkit.nothing.NothingRegistrar;
import mz.lib.minecraft.bukkit.paper.PaperModule;
import mz.lib.minecraft.bukkit.permission.PermissionRegistrar;
import mz.lib.minecraft.bukkit.recipe.RecipeEditorRegistrar;
import mz.lib.minecraft.bukkit.wrappednms.NmsSlot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Random;

public class MzLib extends MzPlugin
{
	public static MzLib instance;
	{
		instance=this;
	}
	public static Random rand=new Random();
	
	static
	{
		try
		{
			Root.getUnsafe().ensureClassInitialized(Class.forName("sun.misc.Unsafe"));
			Lookup trusted=Root.getTrusted();
			Class<?> reflection=Class.forName("jdk.internal.reflect.Reflection");
			Object[] tmparr=new Object[]{null};
			trusted.findStaticSetter(reflection,"ALL_MEMBERS",java.util.Set.class).invokeWithArguments(tmparr);
			trusted.findStaticSetter(reflection,"WILDCARD",String.class).invokeWithArguments(tmparr);
			trusted.findStaticSetter(reflection,"methodFilterMap",java.util.Map.class).invokeWithArguments(tmparr);
			trusted.findStaticSetter(reflection,"fieldFilterMap",java.util.Map.class).invokeWithArguments(tmparr);
			ModuleAccess modacc=Root.getModuleAccess();
			Class<?> module=Class.forName("java.lang.Module");
			try
			{
				MethodHandle ena=trusted.findSetter(module,"enableNativeAccess",java.lang.Boolean.TYPE);
				tmparr=new Object[]{modacc.getALL_UNNAMED_MODULE(),true};
				try
				{
					ena.invokeWithArguments(tmparr);
				}
				catch(Throwable ignored)
				{
				}
				tmparr[0]=modacc.getEVERYONE_MODULE();
				try
				{
					ena.invokeWithArguments(tmparr);
				}
				catch(Throwable ignored)
				{
				}
			}
			catch(Throwable e)
			{
			}
			try
			{
				Root.openAccess(Class.forName("jdk.internal.module.IllegalAccessLogger").getDeclaredField("logger")).set(null,null);
			}
			catch(Throwable t)
			{
			}
		}
		catch(Throwable t)
		{
		}
	}
	
	public void onEnable()
	{
		this.saveDefaultConfig();
		
		LangUtil.instance.load();
		
		if(getConfig().getBoolean("hotLoadingTips",true))
			for(Player p: Bukkit.getOnlinePlayers())
				sendPluginMessage(p,LangUtil.getTranslated(p,"mzlib.hotloading"));
		
		if(ClassUtil.findLoadedClass(Event.class.getClassLoader(),"org.bukkit.event.entity.EntityDropItemEvent")==null)
			ClassUtil.loadClass("org.bukkit.event.entity.EntityDropItemEvent",FileUtil.readInputStream(this.getResource("org/bukkit/event/entity/EntityDropItemEvent.class")),Event.class.getClassLoader());
		
		if(PaperModule.instance.isPaper)
			PaperModule.instance.load();
		
		RegistrarRegistrar.instance.load();
		ListenerRegistrar.instance.load();
		IModule.ModuleModule.instance.load();
		
		NothingRegistrar.instance.load();
		
		ArgParserRegistrar.instance.load();
		IMainCommand.Module.instance.load();
		
		ShowItemEvent.Module.instance.load();
		WindowOpenEvent.Module.instance.load();
		ShowInventoryItemEvent.Module.instance.load();
		PlayerReadyEvent.Module.instance.load();
		PlayerReceiveMsgEvent.Module.instance.load();
		SendEntityMetadataEvent.Module.instance.load();
		PlayerUseItemEvent.Module.instance.load();
		PermissionRegistrar.instance.load();
		
		OriginalItemFilterRegistrar.instance.load();
		ShowItemOnHandModule.instance.load();
		DebugSlotCommand.instance.load();
		SetItemEvent.Module.instance.load();
		ShowDropNameModule.instance.load();
		ViewList.Module.instance.load();
		InventoryUI.Module.instance.load();
		AnvilUI.Module.instance.load();
		RecipeEditorRegistrar.instance.load();
		EnchantUtil.init();
		NmsSlot.Module.instance.load();
		EntityViewWatcher.Module.instance.load();
		
		MzItemRegistrar.instance.load();
		MzItemProcessor.instance.load();
		MzMapProcessor.instance.load();
		
		QuickShopSupportModule.instance.load();
		
		MzLibCommandModule.instance.load();
		new MainCommand(instance,new SundayCommand()).load();
		
		super.onEnable();
	}
}
