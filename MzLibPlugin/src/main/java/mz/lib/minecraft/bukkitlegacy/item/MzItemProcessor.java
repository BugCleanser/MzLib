package mz.lib.minecraft.bukkitlegacy.item;

import mz.lib.Ref;
import mz.lib.StringUtil;
import mz.lib.minecraft.MinecraftLanguages;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.command.argparser.AbsArgParser;
import mz.lib.minecraft.command.argparser.ArgParserRegistrar;
import mz.lib.minecraft.bukkitlegacy.event.PlayerUseItemEvent;
import mz.lib.minecraft.bukkitlegacy.event.SetItemEvent;
import mz.lib.minecraft.bukkitlegacy.event.ShowItemEvent;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.minecraft.bukkit.nms.NmsMinecraftKey;
import mz.lib.mzlang.MzObject;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.List;
import java.util.stream.Collectors;

public class MzItemProcessor extends AbsModule
{
	public static MzItemProcessor instance=new MzItemProcessor();
	public MzItemProcessor()
	{
		super(MzLib.instance,ArgParserRegistrar.instance,OriginalItemFilterRegistrar.instance);
	}
	
	@Override
	public void onEnable()
	{
		reg(new OriginalItemFilterRegistrar.OriginalItemFilter(item->MzItem.getKey(item)==null));
		reg(new AbsArgParser<MzItem>(MzItem.class)
		{
			@Override
			public MzItem parse(CommandSender sender,String arg) throws Throwable
			{
				NamespacedKey key;
				if(arg.contains(":"))
					key=NmsMinecraftKey.newInstance(arg).toBukkit();
				else
					key=new NamespacedKey("mzlib",arg);
				return MzObject.newInstance(MzItem.mzItems.get(key));
			}
			@Override
			public boolean checkFront(CommandSender sender,String arg,double min,double max)
			{
				return StringUtil.startsWithAny(arg,MzItem.mzItems.keySet().stream().map(Object::toString).collect(Collectors.toList()));
			}
			@Override
			public List<String> getDefaultPreset(CommandSender player,double max,double min)
			{
				return MzItem.mzItems.keySet().stream().map(Object::toString).collect(Collectors.toList());
			}
			@Override
			public String getTypeName(CommandSender player,double max,double min)
			{
				return MinecraftLanguages.translate(player,"mzlib.command.default.type.mzitem");
			}
		});
	}
	
	@EventHandler(priority=EventPriority.LOW,ignoreCancelled=true)
	public void onPlayerUseItem(PlayerUseItemEvent event)
	{
		MzItem mi=MzItem.get(event.item);
		if(mi!=null)
		{
			Ref<Boolean> cancelled=new Ref<>(event.isCancelled());
			if(event.entity!=null)
				mi.onUse(event.getPlayer(),event.hand,cancelled,event.entity);
			else if(event.block!=null)
				mi.onUse(event.getPlayer(),event.hand,cancelled,event.block,event.blockFace);
			else
				mi.onUse(event.getPlayer(),event.hand,cancelled);
			event.setCancelled(cancelled.get());
		}
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void onShowItem(ShowItemEvent event)
	{
		MzItem mi=MzItem.get(event.item.get());
		if(mi!=null)
			mi.onShow(event.player);
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void onSetItem(SetItemEvent event)
	{
		MzItem mi=MzItem.get(event.item.get());
		if(mi!=null)
		{
			mi.onSet(event.player);
		}
	}
}
