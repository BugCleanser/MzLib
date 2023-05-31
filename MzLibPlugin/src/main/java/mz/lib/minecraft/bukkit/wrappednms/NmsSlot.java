package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.entity.PlayerUtil;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.mzlang.BukkitMzObject;
import mz.lib.minecraft.bukkit.mzlang.RefactorBukkitSign;
import mz.lib.minecraft.bukkit.wrappedobc.ObcEntity;
import mz.lib.minecraft.bukkit.wrappedobc.ObcInventory;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.mzlang.Extends;
import mz.lib.mzlang.MzObject;
import mz.lib.mzlang.PropAccessor;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

@WrappedBukkitClass({@VersionName(value="nms.Slot",maxVer=17),@VersionName(value="net.minecraft.world.inventory.Slot",minVer=17)})
public interface NmsSlot extends WrappedBukkitObject
{
	static class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance);
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
	interface CustomSlot extends BukkitMzObject
	{
		@PropAccessor("itemFilter")
		Predicate<ItemStack> getItemFilter();
		@PropAccessor("itemFilter")
		CustomSlot setItemFilter(Predicate<ItemStack> itemFilter);
		
		@PropAccessor("playerFilter")
		Predicate<HumanEntity> getPlayerFilter();
		@PropAccessor("playerFilter")
		CustomSlot setPlayerFilter(Predicate<HumanEntity> playerFilter);
		
		@RefactorBukkitSign({@VersionName(maxVer=18,value="isAllowed"),@VersionName(minVer=18,value="a")})
		default boolean isAllowed(NmsItemStack item)
		{
			return getItemFilter().test(ObcItemStack.asCraftMirror(item).getRaw());
		}
		
		@RefactorBukkitSign({@VersionName(maxVer=18,value="isAllowed"),@VersionName(minVer=18,value="a")})
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
	@WrappedBukkitMethod({@VersionName("isAllowed"),@VersionName(minVer=18, value="a")})
	boolean isAllowed(NmsItemStack item);
	default boolean isAllowed(ItemStack item)
	{
		return isAllowed(ObcItemStack.asNMSCopy(item));
	}
	@WrappedBukkitMethod({@VersionName("isAllowed"),@VersionName(minVer=18, value="a")})
	boolean isAllowed(NmsEntityHuman player);
	default boolean isAllowed(HumanEntity player)
	{
		return isAllowed(WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityHuman.class));
	}
	@WrappedBukkitMethod(@VersionName("a"))
	void onVisit(NmsEntityHuman player,NmsItemStack item);
	default void onVisit(HumanEntity player,ItemStack item)
	{
		onVisit(WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityHuman.class),ObcItemStack.asNMSCopy(item));
	}
	@WrappedBukkitFieldAccessor({@VersionName("index"),@VersionName(minVer=17, value="a")})
	int getIndex();
	@WrappedBukkitFieldAccessor({@VersionName("index"),@VersionName(minVer=17, value="a")})
	NmsSlot setIndex(int index);
	@WrappedBukkitFieldAccessor({@VersionName("rawSlotIndex"),@VersionName(minVer=17,maxVer=19, value="d"),@VersionName(minVer=19, value="e")})
	int getRawSlot();
	@WrappedBukkitFieldAccessor({@VersionName("rawSlotIndex"),@VersionName(minVer=17,maxVer=19, value="d"),@VersionName(minVer=19, value="e")})
	NmsSlot setRawSlot(int index);
	@WrappedBukkitFieldAccessor({@VersionName("inventory"),@VersionName(minVer=17,maxVer=19, value="c"),@VersionName(value="d",minVer=19)})
	NmsIInventory getInventory();
	@WrappedBukkitFieldAccessor({@VersionName("inventory"),@VersionName(minVer=17,maxVer=19, value="c"),@VersionName(value="d",minVer=19)})
	NmsSlot setInventory(NmsIInventory inv);
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=14, value="f"),@VersionName(minVer=13,maxVer=19, value="e"),@VersionName(minVer=19, value="f")})
	int getX();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=14, value="f"),@VersionName(minVer=13,maxVer=19, value="e"),@VersionName(minVer=19, value="f")})
	NmsSlot setX(int x);
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=14, value="g"),@VersionName(minVer=13,maxVer=19, value="f"),@VersionName(minVer=19, value="g")})
	int getY();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=14, value="g"),@VersionName(minVer=13,maxVer=19, value="f"),@VersionName(minVer=19, value="g")})
	NmsSlot setY(int y);
	@WrappedBukkitMethod(value={@VersionName("getItem"),@VersionName(minVer=18, value="e")})
	NmsItemStack getItem();
	default void setItem(ItemStack item)
	{
		set(ObcItemStack.asNMSCopy(item));
	}
	default ItemStack getBukkitItem()
	{
		return ObcItemStack.asBukkitCopy(getItem());
	}
	@WrappedBukkitMethod({@VersionName("set"),@VersionName(minVer=18, value="d")})
	void set(NmsItemStack item);
}
