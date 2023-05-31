package mz.lib.minecraft.bukkitlegacy.gui.inventory;

import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.mzlib.*;
import mz.lib.minecraft.bukkit.nms.NmsIInventory;
import mz.lib.minecraft.bukkit.nms.NmsITileInventory;
import mz.lib.minecraft.bukkit.obc.ObcChatMessage;
import mz.lib.minecraft.bukkit.obc.ObcInventory;
import mz.lib.minecraft.bukkit.obc.ObcInventoryCustom;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class InventoryUtil
{
	public @Deprecated
	InventoryUtil()
	{
	}
	
	public static String getTitle(Inventory inv)
	{
		if(Server.instance.v13)
		{
			NmsIInventory nms=WrappedObject.wrap(ObcInventory.class,inv).getNms();
			if(nms.is(ObcInventoryCustom.MinecraftInventory.class))
				return WrappedObject.wrap(ObcInventoryCustom.MinecraftInventory.class,nms.getRaw()).getTitle();
			else if(nms.is(NmsITileInventory.class))
				return ObcChatMessage.fromComponentV13(WrappedObject.wrap(NmsITileInventory.class,nms.getRaw()).getScoreboardDisplayNameV13());
			else
				return null;
		}
		else
			return inv.getTitle();
	}
	
	public static int getCapacity(Inventory inv,ItemStack is)
	{
		int r=0;
		for(int i=0;i<inv.getSize();i++)
		{
			ItemStack t=inv.getItem(i);
			if(ItemStackBuilder.isAir(t))
				r+=is.getType().getMaxStackSize();
			else if(is.isSimilar(t))
				r+=is.getType().getMaxStackSize()-t.getAmount();
		}
		return r;
	}
	
	public static int addItem(Inventory inv,ItemStack is)
	{
		return addItem(inv,is,is.getAmount());
	}
	/**
	 * 向物品栏添加物品堆
	 *
	 * @param inv   物品栏
	 * @param is    物品
	 * @param count 物品数量
	 * @return 不能添加的物品数量
	 */
	public static int addItem(Inventory inv,ItemStack is,int count)
	{
		if(count<=0)
			return count;
		int max=is.getType().getMaxStackSize();
		if(inv instanceof PlayerInventory)
		{
			for(int i=8;i>=0;i--)
			{
				ItemStack t=inv.getItem(i);
				if(!ItemStackBuilder.isAir(t))
				{
					if(is.isSimilar(t))
					{
						if(count+t.getAmount()>max)
						{
							count-=max-t.getAmount();
							t.setAmount(max);
						}
						else
						{
							t.setAmount(t.getAmount()+count);
							count=0;
							break;
						}
						inv.setItem(i,t);
					}
				}
			}
			if(count!=0)
			{
				for(int i=35;i>=9;i--)
				{
					ItemStack t=inv.getItem(i);
					if(!ItemStackBuilder.isAir(t))
					{
						if(is.isSimilar(t))
						{
							if(count+t.getAmount()>max)
							{
								count-=max-t.getAmount();
								t.setAmount(max);
							}
							else
							{
								t.setAmount(t.getAmount()+count);
								count=0;
								break;
							}
							inv.setItem(i,t);
						}
					}
				}
				if(count!=0)
				{
					for(int i=8;i>=0;i--)
					{
						ItemStack t=inv.getItem(i);
						if(ItemStackBuilder.isAir(t))
						{
							t=new ItemStack(is);
							if(count>max)
							{
								t.setAmount(max);
								count-=max;
								inv.setItem(i,t);
							}
							else
							{
								t.setAmount(count);
								count=0;
								inv.setItem(i,t);
								break;
							}
						}
					}
					if(count!=0)
					{
						for(int i=35;i>=9;i--)
						{
							ItemStack t=inv.getItem(i);
							if(ItemStackBuilder.isAir(t))
							{
								t=new ItemStack(is);
								if(count>max)
								{
									t.setAmount(max);
									count-=max;
									inv.setItem(i,t);
								}
								else
								{
									t.setAmount(count);
									count=0;
									inv.setItem(i,t);
									break;
								}
							}
						}
					}
				}
			}
		}
		else
		{
			for(int i=0;i<inv.getSize();i++)
			{
				ItemStack t=inv.getItem(i);
				if(!ItemStackBuilder.isAir(t))
				{
					if(is.isSimilar(t))
					{
						if(count+t.getAmount()>max)
						{
							count-=max-t.getAmount();
							t.setAmount(max);
							inv.setItem(i,t);
						}
						else
						{
							t.setAmount(t.getAmount()+count);
							count=0;
							inv.setItem(i,t);
							break;
						}
					}
				}
			}
			if(count!=0)
			{
				for(int i=0;i<inv.getSize();i++)
				{
					ItemStack t=inv.getItem(i);
					if(ItemStackBuilder.isAir(t))
					{
						t=new ItemStack(is);
						if(count>max)
						{
							t.setAmount(max);
							count-=max;
							inv.setItem(i,t);
						}
						else
						{
							t.setAmount(count);
							count=0;
							inv.setItem(i,t);
							break;
						}
					}
				}
			}
		}
		return count;
	}
}
