package mz.lib.minecraft.bukkitlegacy;

import io.github.karlatemp.unsafeaccessor.*;
import mz.lib.*;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkit.paper.*;
import mz.lib.minecraft.bukkitlegacy.block.*;
import mz.lib.minecraft.bukkitlegacy.command.*;
import mz.lib.minecraft.bukkitlegacy.command.argparser.*;
import mz.lib.minecraft.bukkitlegacy.entity.*;
import mz.lib.minecraft.bukkitlegacy.event.*;
import mz.lib.minecraft.bukkitlegacy.gui.*;
import mz.lib.minecraft.bukkitlegacy.gui.inventory.*;
import mz.lib.minecraft.bukkitlegacy.item.*;
import mz.lib.minecraft.bukkitlegacy.item.map.*;
import mz.lib.minecraft.bukkitlegacy.module.*;
import mz.lib.minecraft.bukkitlegacy.mzlibcommand.*;
import mz.lib.minecraft.bukkitlegacy.mzlibcommand.debug.*;
import mz.lib.minecraft.bukkitlegacy.nothing.*;
import mz.lib.minecraft.bukkitlegacy.paper.*;
import mz.lib.minecraft.bukkitlegacy.permission.*;
import mz.lib.minecraft.bukkitlegacy.recipe.*;
import mz.lib.nothing.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.*;
import java.util.*;

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
		
		ArgParserRegistrar.instance.load();
		IMainCommand.Module.instance.load();
		
		ShowItemEvent.Module.instance.load();
		WindowOpenEvent.Module.instance.load();
		ShowInventoryItemEvent.Module.instance.load();
		PlayerReadyEvent.Module.instance.load();
		PlayerReceiveMsgEvent.Module.instance.load();
		SendEntityMetadataEvent.Module.instance.load();
		PlayerUseItemEvent.Module.instance.load();
		
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
		
		MzBlockProcessor.instance.load();
		
		QuickShopSupportModule.instance.load();
		
		reg(NmsEntity.class);
		reg(NmsEntityFishingHook.class);
		reg(NmsNetworkManager.class);
		reg(NmsRecipeItemStack.class);
		
		MzLibCommandModule.instance.load();
		new MainCommand(instance,new SundayCommand()).load();
		
		super.onEnable();
	}
}
