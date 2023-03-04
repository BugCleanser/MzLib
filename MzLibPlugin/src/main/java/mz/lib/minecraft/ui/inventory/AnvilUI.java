package mz.lib.minecraft.ui.inventory;

import com.google.common.collect.Lists;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.bukkitlegacy.ProtocolUtil;
import mz.lib.minecraft.ui.UIStack;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.minecraft.bukkitlegacy.module.IModule;
import mz.lib.minecraft.bukkit.obc.ObcEntity;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import mz.mzlib.*;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import java.util.stream.Collectors;

/**
 * AnvilUI
 * 只能由一个玩家打开
 * 只能被打开一次
 * 玩家不可更改其中的物品
 */
public abstract class AnvilUI extends InventoryUI
{
	public int id;
	public String name;
	public AnvilUI(IModule module,String title)
	{
		super(module,3);
		this.inv=Bukkit.createInventory(this,InventoryType.ANVIL,title);
	}
	public void refreshAfter()
	{
		for(HumanEntity player:this.inv.getViewers())
		{
			if(player instanceof Player)
				ProtocolUtil.sendPacket((Player) player,NmsPacketPlayOutWindowItems.newInstance().setWindowId(WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityPlayer.class).getOpenContainer().getWindowId()));
		}
	}
	public void refresh(int index)
	{
		for(HumanEntity player: this.inv.getViewers())
		{
			if(player instanceof Player)
			{
				NmsPacketPlayOutSetSlot packet=NmsPacketPlayOutSetSlot.newInstance().setSlot(index).setItem(ObcItemStack.asNMSCopy(getInventory().getItem(index))).setWindowId(WrappedObject.wrap(ObcEntity.class,player).getHandle().cast(NmsEntityPlayer.class).getOpenContainer().getWindowId());
//				if(BukkitWrapper.v17)
//				{
//					packet.setRevisionV17(new Random().nextInt());
//				}
				ProtocolUtil.sendPacket((Player) player,packet);
			}
		}
	}
	public void onEdit(Player player,String lastName)
	{
	}
	public String getName()
	{
		return name;
	}
	@Override
	public void onClick(ClickType type,HumanEntity player,int rawSlot)
	{
		super.onClick(type,player,rawSlot);
		if(rawSlot<3&&player instanceof Player)
			onClick((Player) player,rawSlot);
	}
	public void onClick(Player player,int slot)
	{
	}

	long lastTime=0;
	int renamedCount=0;
	public static class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance,ProtocolUtil.instance,UIStack.Module.instance);
			reg(new ProtocolUtil.SendListener<>(EventPriority.LOW,NmsPacketPlayOutWindowItems.class,(pl,pa,c)->
			{
				if(c.get())
					return;
				if(pa.getWindowId()!=0 && pl.getOpenInventory().getTopInventory().getHolder() instanceof AnvilUI)
				{
					pa.setItems(Lists.newArrayList(pl.getOpenInventory().getTopInventory().getHolder().getInventory().getContents()).stream().map(i->ObcItemStack.asNMSCopy(i).getRaw()).collect(Collectors.toList()));
				}
			}));
			reg(new ProtocolUtil.SendListener<>(EventPriority.LOW,NmsPacketPlayOutSetSlot.class,(pl,pa,c)->
			{
				if(c.get())
					return;
				if(pa.getWindowId()!=0 && pa.getSlot()>=0 && pa.getSlot()<3 && pl.getOpenInventory().getTopInventory().getHolder() instanceof AnvilUI)
				{
					pa.setItem(ObcItemStack.asNMSCopy(pl.getOpenInventory().getTopInventory().getHolder().getInventory().getItem(pa.getSlot())));
				}
			}));
			if(Server.instance.v13)
			{
				reg(new ProtocolUtil.ReceiveListener<>(EventPriority.HIGHEST,NmsPacketPlayInItemNameV13.class,(pl,pa,c)->
				{
					if(c.get())
						return;
					if(pl.getOpenInventory().getTopInventory().getHolder() instanceof AnvilUI)
					{
						AnvilUI i=(AnvilUI) pl.getOpenInventory().getTopInventory().getHolder();
						if(System.currentTimeMillis()-i.lastTime>100)
						{
							i.renamedCount=0;
							i.lastTime=System.currentTimeMillis();
						}
						if(i.renamedCount<10)
						{
							i.renamedCount++;
							Bukkit.getScheduler().runTask(i.module.getPlugin(), () ->
							{
								String lastName=i.getName();
								i.name=pa.getName();
								if(!i.getName().equals(lastName))
								{
									i.onEdit(pl, lastName);
									i.refresh(2);
								}
								else
								{
									i.refreshAfter();
									i.refreshAfter();
								}
							});
						}
						c.set(true);
					}
				}));
			}
			else
			{
				reg(new ProtocolUtil.ReceiveListener<>(EventPriority.HIGHEST,NmsPacketPlayInCustomPayload.class,(pl,pa,c)->
				{
					if(c.get())
						return;
					if(pa.getChannelNameV_13().equals("MC|ItemName"))
					{
						if(pl.getOpenInventory().getTopInventory().getHolder() instanceof AnvilUI)
						{
							AnvilUI i=(AnvilUI) pl.getOpenInventory().getTopInventory().getHolder();
							if(System.currentTimeMillis()-i.lastTime>100)
							{
								i.renamedCount=0;
								i.lastTime=System.currentTimeMillis();
							}
							if(i.renamedCount<10)
							{
								i.renamedCount++;
								Bukkit.getScheduler().runTask(i.module.getPlugin(), () ->
								{
									String lastName=i.getName();
									i.name=pa.getDataV_13().readString(32767);
									if(!i.getName().equals(lastName))
									{
										i.onEdit(pl, lastName);
										i.refresh(2);
									}
								});
							}
							c.set(true);
						}
					}
				}));
			}
		}
	}
}
