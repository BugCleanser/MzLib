package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.entity.PlayerUtil;
import mz.lib.minecraft.bukkit.obc.ObcEntity;
import mz.lib.minecraft.bukkit.obc.ObcInventory;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import mz.lib.minecraft.mzlang.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.module.MzModule;
import mz.lib.mzlang.*;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

@VersionalWrappedClass({@VersionalName(value="nms.Slot",maxVer=17),@VersionalName(value="net.minecraft.world.inventory.Slot",minVer=17)})
public interface NmsSlot extends VersionalWrappedObject
{
	static class Module extends MzModule
	{
		public static Module instance=new Module();
		{
			this.load();
		}
		@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
		public void onInventoryClick(InventoryClickEvent event)
		{
			if(event.getWhoClicked() instanceof Player)
			{
				NmsContainer c=PlayerUtil.getOpenContainer((Player) event.getWhoClicked());
				if(event.getRawSlot()>=0&&event.getRawSlot()<c.getSlots().size()&&!c.getSlot(event.getRawSlot()).isAllowed(event.getWhoClicked()))
				{
					for(HumanEntity p:event.getInventory().getViewers())
						if(p instanceof Player)
							((Player)p).updateInventory();
				}
			}
		}
	}
	
	@Extends(NmsSlot.class)
	interface CustomSlot extends VersionMzObject
	{
		@PropAccessor("itemFilter")
		Predicate<ItemStack> getItemFilter();
		@PropAccessor("itemFilter")
		CustomSlot setItemFilter(Predicate<ItemStack> itemFilter);
		
		@PropAccessor("playerFilter")
		Predicate<HumanEntity> getPlayerFilter();
		@PropAccessor("playerFilter")
		CustomSlot setPlayerFilter(Predicate<HumanEntity> playerFilter);
		
		@RefactorVersionSign({@VersionalName(maxVer=18,value="isAllowed"),@VersionalName(minVer=18,value="a")})
		default boolean isAllowed(NmsItemStack item)
		{
			return getItemFilter().test(ObcItemStack.asCraftMirror(item).getRaw());
		}
		
		@RefactorVersionSign({@VersionalName(maxVer=18,value="isAllowed"),@VersionalName(minVer=18,value="a")})
		default boolean isAllowed(NmsEntityHuman player)
		{
			return getPlayerFilter().test((HumanEntity) player.getBukkitEntity());
		}
	}
	
	static NmsSlot newInstance(NmsIInventory inv,int index,int x,int y,Predicate<ItemStack> itemFilter,Predicate<HumanEntity> playerFilter)
	{
		NmsSlot r=WrappedObject.wrap(NmsSlot.class,MzObject.newInstance(CustomSlot.class).setItemFilter(itemFilter).setPlayerFilter(playerFilter));
		r.setInventory(inv);
		r.setIndex(index);
		r.setX(x);
		r.setY(y);
		return r;
	}
	static NmsSlot newInstance(Inventory inv,int index,int x,int y,Predicate<ItemStack> itemFilter,Predicate<HumanEntity> playerFilter)
	{
		return newInstance(WrappedObject.wrap(ObcInventory.class,inv).cast(NmsIInventory.class),index,x,y,itemFilter,playerFilter);
	}
	static NmsSlot newInstance(NmsSlot raw,Predicate<ItemStack> itemFilter,Predicate<HumanEntity> playerFilter)
	{
		return newInstance(raw.getInventory(),raw.getIndex(),raw.getX(),raw.getY(),itemFilter,playerFilter).setRawSlot(raw.getRawSlot());
	}
	static NmsSlot readWrite(NmsSlot raw)
	{
		return newInstance(raw,is->true,p->true);
	}
	static NmsSlot readOnly(NmsSlot raw)
	{
		return newInstance(raw,is->false,p->true);
	}
	static NmsSlot writeOnly(NmsSlot raw)
	{
		return newInstance(raw,is->true,p->false);
	}
	static NmsSlot showOnly(NmsSlot raw)
	{
		return newInstance(raw,is->false,p->false);
	}
	static NmsSlot get(HumanEntity player,int rawSlot)
	{
		if(rawSlot<0)
			return null;
		return NmsEntityHuman.fromBukkit(player).getOpenContainer().getSlot(rawSlot);
	}
	static NmsSlot getClickedSlot(InventoryClickEvent event)
	{
		return get(event.getWhoClicked(),event.getRawSlot());
	}
	@VersionalWrappedMethod({@VersionalName("isAllowed"),@VersionalName(minVer=18, value="a")})
	boolean isAllowed(NmsItemStack item);
	default boolean isAllowed(ItemStack item)
	{
		return isAllowed(ObcItemStack.asNMSCopy(item));
	}
	@VersionalWrappedMethod({@VersionalName("isAllowed"),@VersionalName(minVer=18, value="a")})
	boolean isAllowed(NmsEntityHuman player);
	default boolean isAllowed(HumanEntity player)
	{
		return isAllowed(WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityHuman.class));
	}
	@VersionalWrappedMethod(@VersionalName("a"))
	void onVisit(NmsEntityHuman player,NmsItemStack item);
	default void onVisit(HumanEntity player,ItemStack item)
	{
		onVisit(WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityHuman.class),ObcItemStack.asNMSCopy(item));
	}
	@VersionalWrappedFieldAccessor({@VersionalName("index"),@VersionalName(minVer=17, value="a")})
	int getIndex();
	@VersionalWrappedFieldAccessor({@VersionalName("index"),@VersionalName(minVer=17, value="a")})
	NmsSlot setIndex(int index);
	@VersionalWrappedFieldAccessor({@VersionalName("rawSlotIndex"),@VersionalName(minVer=17,maxVer=19, value="d"),@VersionalName(minVer=19, value="e")})
	int getRawSlot();
	@VersionalWrappedFieldAccessor({@VersionalName("rawSlotIndex"),@VersionalName(minVer=17,maxVer=19, value="d"),@VersionalName(minVer=19, value="e")})
	NmsSlot setRawSlot(int index);
	@VersionalWrappedFieldAccessor({@VersionalName("inventory"),@VersionalName(minVer=17,maxVer=19, value="c"),@VersionalName(value="d",minVer=19)})
	NmsIInventory getInventory();
	@VersionalWrappedFieldAccessor({@VersionalName("inventory"),@VersionalName(minVer=17,maxVer=19, value="c"),@VersionalName(value="d",minVer=19)})
	NmsSlot setInventory(NmsIInventory inv);
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=14, value="f"),@VersionalName(minVer=13,maxVer=19, value="e"),@VersionalName(minVer=19, value="f")})
	int getX();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=14, value="f"),@VersionalName(minVer=13,maxVer=19, value="e"),@VersionalName(minVer=19, value="f")})
	NmsSlot setX(int x);
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=14, value="g"),@VersionalName(minVer=13,maxVer=19, value="f"),@VersionalName(minVer=19, value="g")})
	int getY();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=14, value="g"),@VersionalName(minVer=13,maxVer=19, value="f"),@VersionalName(minVer=19, value="g")})
	NmsSlot setY(int y);
	@VersionalWrappedMethod(value={@VersionalName("getItem"),@VersionalName(minVer=18, value="e")})
	NmsItemStack getItem();
	default void setItem(ItemStack item)
	{
		set(ObcItemStack.asNMSCopy(item));
	}
	default ItemStack getBukkitItem()
	{
		return ObcItemStack.asBukkitCopy(getItem());
	}
	@VersionalWrappedMethod({@VersionalName("set"),@VersionalName(minVer=18, value="d")})
	void set(NmsItemStack item);
}
