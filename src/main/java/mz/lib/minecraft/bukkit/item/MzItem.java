package mz.lib.minecraft.bukkit.item;

import mz.lib.ListUtil;
import mz.lib.Ref;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagByte;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagCompound;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.mzlang.CallEach;
import mz.lib.mzlang.FinalProp;
import mz.lib.mzlang.MzObject;
import mz.lib.mzlang.PropAccessor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface MzItem extends MzObject
{
	Map<NamespacedKey,Class<? extends MzItem>> mzItems=new ConcurrentHashMap<>();
	
	@FinalProp
	@PropAccessor("itemStack")
	MzItem setItemStack(ObcItemStack is);
	@FinalProp
	default MzItem setItemStack(ItemStack is)
	{
		return setItemStack(ObcItemStack.ensure(is));
	}
	@PropAccessor("itemStack")
	ObcItemStack getItemStack();
	
	NamespacedKey getKey();
	String getRawIdV_13();
	default int getChildIdV_13()
	{
		return 0;
	}
	default String getRawIdV13()
	{
		return getRawIdV_13();
	}
	default String getTranslatedKey()
	{
		return null;
	}
	
	@CallEach
	default void onUse(Player user,EquipmentSlot hand,Ref<Boolean> cancelled)
	{
	}
	@CallEach
	default void onUse(Player user,EquipmentSlot hand,Ref<Boolean> cancelled,Block target,BlockFace face)
	{
		onUse(user,hand,cancelled);
	}
	@CallEach
	default void onUse(Player user,EquipmentSlot hand,Ref<Boolean> cancelled,Entity target)
	{
		onUse(user,hand,cancelled);
	}
	
	@Override
	default void init()
	{
		setItemStack(setKey(ItemStackBuilder.forFlattening(getRawIdV_13(),getChildIdV_13(),getRawIdV13()).get(),getKey()));
	}
	@CallEach
	default void onShow(Player player)
	{
		if(getTranslatedKey()!=null)
		{
			ItemStackBuilder isb=new ItemStackBuilder(getItemStack());
			if(!isb.hasName())
			{
				isb.setName(LangUtil.getTranslated(player,getTranslatedKey()));
				mzTag().set("namefix",NmsNBTTagByte.newInstance((byte)1));
			}
			String[] extra=StringUtil.split(LangUtil.getTranslated(player,getTranslatedKey()+".lore"),"\n");
			if(extra.length>0)
			{
				mzTag().set("lorefixoffset",NmsNBTTagByte.newInstance((byte) isb.getLore().size()));
				isb.addLore(extra);
				mzTag().set("lorefix",NmsNBTTagByte.newInstance((byte) extra.length));
			}
		}
	}
	@CallEach
	default void onSet(Player player)
	{
		ItemStackBuilder isb=new ItemStackBuilder(getItemStack());
		if(mzTag().containsKey("namefix"))
		{
			isb.display().remove("Name");
			mzTag().remove("namefix");
		}
		if(mzTag().containsKey("lorefix"))
		{
			List<String> lore=isb.getLore();
			byte offset=mzTag().getByte("lorefixoffset");
			isb.setLore(ListUtil.mergeLists(lore.subList(0,offset),lore.subList(offset+mzTag().getByte("lorefix"),lore.size())));
			mzTag().remove("lorefixoffset");
			mzTag().remove("lorefix");
		}
	}
	
	default NmsNBTTagCompound mzTag()
	{
		return new ItemStackBuilder(getItemStack()).tag().getCompound("mz");
	}
	
	static NamespacedKey getKey(ItemStack item)
	{
		ItemStackBuilder isb=new ItemStackBuilder(item);
		if(isb.hasTag()&&isb.tag().containsKey("mz"))
			return isb.tag().getCompound("mz").getNamespacedKey("key");
		return null;
	}
	static ItemStack setKey(ItemStack item,NamespacedKey key)
	{
		ItemStackBuilder isb=new ItemStackBuilder(item);
		if(!isb.tag().containsKey("mz"))
			isb.tag().set("mz",NmsNBTTagCompound.newInstance());
		isb.tag().getCompound("mz").set("key",key);
		return isb.get();
	}
	static MzItem get(ItemStack item)
	{
		NamespacedKey key=MzItem.getKey(item);
		if(key!=null)
		{
			Class<? extends MzItem> c=MzItem.mzItems.get(key);
			if(c==null)
				c=UnknownMzItem.class;
			return MzObject.newUninitializedInstance(c).setItemStack(item);
		}
		return null;
	}
}
