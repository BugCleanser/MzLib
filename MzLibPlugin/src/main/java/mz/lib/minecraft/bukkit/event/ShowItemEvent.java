package mz.lib.minecraft.bukkit.event;

import mz.lib.Ref;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.message.MessageComponent;
import mz.lib.minecraft.bukkit.message.TranslateMessageComponent;
import mz.lib.minecraft.bukkit.message.showonmouse.ShowItemOnMouse;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.nothing.NothingBukkit;
import mz.lib.minecraft.bukkit.nothing.NothingBukkitInject;
import mz.lib.minecraft.bukkit.nothing.NothingRegistrar;
import mz.lib.minecraft.bukkit.wrappednms.NmsItemStack;
import mz.lib.minecraft.bukkit.wrappednms.NmsMerchantRecipe;
import mz.lib.minecraft.bukkit.wrappednms.NmsMerchantRecipeList;
import mz.lib.minecraft.bukkit.wrappednms.NmsPacketDataSerializer;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.nothing.LocalVar;
import mz.lib.nothing.Nothing;
import mz.lib.nothing.NothingLocation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 向玩家显示一个物品的事件
 * 可以更改显示的物品
 * 一般需要在SetItemEvent中改回去
 *
 * @see ShowItemEvent
 */
public class ShowItemEvent extends Event
{
	public Player player;
	public ItemStack itemStack;
	public ItemStackBuilder item;
	
	public ShowItemEvent(Player player,ItemStack item)
	{
		super(!Bukkit.getServer().isPrimaryThread());
		this.player=player;
		this.itemStack=item.clone();
		this.item=new ItemStackBuilder(itemStack);
	}
	public static void decorate(MessageComponent msg,Player player)
	{
		if(msg.getShowOnMouse() instanceof ShowItemOnMouse)
		{
			ShowItemEvent event=new ShowItemEvent(player,((ShowItemOnMouse) msg.getShowOnMouse()).getItem());
			Bukkit.getPluginManager().callEvent(event);
			((ShowItemOnMouse) msg.getShowOnMouse()).setItem(event.itemStack);
		}
		if(msg.extra!=null)
			for(MessageComponent m:msg.extra)
				decorate(m,player);
		if(msg instanceof TranslateMessageComponent)
		{
			if(((TranslateMessageComponent)msg).with!=null)
				for(MessageComponent m:((TranslateMessageComponent)msg).with)
					decorate(m,player);
		}
	}
	public String getLocale()
	{
		return LangUtil.getLang(player);
	}
	
	private static final HandlerList handlers=new HandlerList();
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	public HandlerList getHandlers()
	{
		return handlers;
	}
	
	@WrappedBukkitClass({@VersionName(value="nms.MerchantRecipeList",maxVer=17),@VersionName(value="net.minecraft.world.item.trading.MerchantRecipeList",minVer=17)})
	public interface NothingMerchantRecipeList extends NmsMerchantRecipeList, NothingBukkit
	{
		List<NmsMerchantRecipeList> temps=Collections.synchronizedList(new ArrayList<>());
		Ref<Player> player=new Ref<>(null);
		static NmsItemStack callEvent(NmsItemStack item)
		{
			ShowItemEvent event=new ShowItemEvent(player.get(),ObcItemStack.asCraftCopy(ObcItemStack.asCraftMirror(item).getRaw()).getRaw());
			Bukkit.getPluginManager().callEvent(event);
			return event.item.getHandle();
		}
		@NothingBukkitInject(name = @VersionName("a"), args = {NmsPacketDataSerializer.class}, location = NothingLocation.FRONT)
		default Optional<Void> writeBefore(@LocalVar(1) NmsPacketDataSerializer data)
		{
			if(temps.contains(this))
				return Nothing.doContinue();
			else
			{
				NmsMerchantRecipeList t=NmsMerchantRecipeList.newInstance();
				for(NmsMerchantRecipe r: this)
				{
					NmsMerchantRecipe n=r.copy();
					n.setBuyingItem1(callEvent(n.getBuyingItem1()));
					n.setBuyingItem2(callEvent(n.getBuyingItem2()));
					n.setSellingItem(callEvent(n.getSellingItem()));
					t.add(n);
				}
				temps.add(t);
				t.write(data);
				temps.remove(t);
				return Nothing.doReturn(null);
			}
		}
	}
	
	public static class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance,NothingRegistrar.instance);
		}
		
		@Override
		public void onEnable()
		{
			reg(NothingMerchantRecipeList.class);
		}
		
		@EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
		public void onInventoryOpen(InventoryOpenEvent event)
		{
			if(event.getInventory().getType()==InventoryType.MERCHANT&&event.getPlayer() instanceof Player)
				NothingMerchantRecipeList.player.set((Player) event.getPlayer());
		}
		
		@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
		public void onPlayerReceiveMsg(PlayerReceiveMsgEvent event)
		{
			decorate(event.msg,event.player);
		}
	}
}
