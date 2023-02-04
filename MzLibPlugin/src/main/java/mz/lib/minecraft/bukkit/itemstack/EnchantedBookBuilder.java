package mz.lib.minecraft.bukkit.itemstack;

import mz.lib.minecraft.bukkit.EnchantUtil;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagCompound;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagList;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagShort;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagString;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantedBookBuilder extends ItemStackBuilder
{
	public static final String id="minecraft:enchanted_book";
	public EnchantedBookBuilder()
	{
		super(id);
	}
	public EnchantedBookBuilder(ItemStackBuilder is)
	{
		super(is);
	}
	public EnchantedBookBuilder(NmsNBTTagCompound nbt)
	{
		super(nbt);
	}
	public EnchantedBookBuilder(ItemStack is)
	{
		super(is);
	}
	
	@SuppressWarnings("deprecation")
	public ItemStackBuilder setStoredEnchants(Map<Enchantment,Short> enchants)
	{
		if(BukkitWrapper.v13)
		{
			NmsNBTTagList t=NmsNBTTagList.newInstance();
			enchants.forEach((e,l)->
			{
				t.add(NmsNBTTagCompound.newInstance().set("StoredEnchantments",NmsNBTTagString.newInstance(EnchantUtil.getEnchantId(e))).set("lvl",NmsNBTTagShort.newInstance(l)));
			});
			tag().set("Enchantments",t);
		}
		else
		{
			NmsNBTTagList t=NmsNBTTagList.newInstance();
			enchants.forEach((e,l)->
			{
				t.add(NmsNBTTagCompound.newInstance().set("StoredEnchantments",NmsNBTTagShort.newInstance((short) e.getId())).set("lvl",NmsNBTTagShort.newInstance(l)));
			});
			tag().set("ench",t);
		}
		return this;
	}
	public boolean hasStoredEnchant()
	{
		return hasTag()&&tag().containsKey("StoredEnchantments");
	}
	@SuppressWarnings("deprecation")
	public Map<Enchantment,Short> getStoredEnchants()
	{
		Map<Enchantment,Short> r=new HashMap<>();
		if(BukkitWrapper.v13)
		{
			List<NmsNBTTagCompound> t=tag().getList("StoredEnchantments").values(NmsNBTTagCompound.class);
			if(t==null)
				return null;
			t.forEach(n->
			{
				String[] key=n.getString("id").split(":");
				r.put(EnchantUtil.getEnchant(key.length>1?new NamespacedKey(key[0],key[1]):new NamespacedKey("minecraft",key[0])),n.getShort("lvl"));
			});
		}
		else
		{
			List<NmsNBTTagCompound> t=tag().getList("StoredEnchantments").values(NmsNBTTagCompound.class);
			if(t==null)
				return null;
			t.forEach(n->
			{
				r.put(Enchantment.getById(n.getShort("id")),n.getShort("lvl"));
			});
		}
		return r;
	}
	@SuppressWarnings("deprecation")
	public ItemStackBuilder addStoredEnchant(Enchantment enchant,short lvl)
	{
		NmsNBTTagCompound tag=tag();
		if(BukkitWrapper.v13)
		{
			if(!tag.containsKey("StoredEnchantments"))
				tag.set("StoredEnchantments",NmsNBTTagList.newInstance());
			tag.getList("StoredEnchantments").add(NmsNBTTagCompound.newInstance().set("id",NmsNBTTagString.newInstance(EnchantUtil.getEnchantId(enchant))).set("lvl",NmsNBTTagShort.newInstance(lvl)));
		}
		else
		{
			if(!tag.containsKey("StoredEnchantments"))
				tag.set("StoredEnchantments",NmsNBTTagList.newInstance());
			tag.getList("StoredEnchantments").add(NmsNBTTagCompound.newInstance().set("id",NmsNBTTagShort.newInstance((short) enchant.getId())).set("lvl",NmsNBTTagShort.newInstance(lvl)));
		}
		return this;
	}
	public boolean isStoredEnchantsHide()
	{
		return (getHideFlags()&32)!=0;
	}
	public ItemStackBuilder setHideStoredEnchants(boolean hideEnchants)
	{
		if(hideEnchants)
			return setHideFlags((byte) (getHideFlags()|32));
		else
			return setHideFlags((byte) (getHideFlags()&~32));
	}
}
