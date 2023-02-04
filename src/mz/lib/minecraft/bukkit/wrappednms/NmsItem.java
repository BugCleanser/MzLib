package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

@WrappedBukkitClass({@VersionName(value="nms.Item",maxVer=17),@VersionName(value="net.minecraft.world.item.Item",minVer=17)})
public interface NmsItem extends WrappedBukkitObject
{
	@WrappedBukkitFieldAccessor(@VersionName(maxVer=13,value="REGISTRY"))
	NmsRegistryMaterials staticGetRegistryV_13();
	static NmsRegistryMaterials getRegistryV_13()
	{
		return WrappedObject.getStatic(NmsItem.class).staticGetRegistryV_13();
	}
	
	static NmsItem fromKey(NmsMinecraftKey key)
	{
		if(BukkitWrapper.version>=14)
			return NmsIRegistry.getItemsV14().get(key,NmsItem.class);
		else if(BukkitWrapper.v13)
			return NmsIRegistry.getItemsV13_14().get(key,NmsItem.class);
		else
			return getRegistryV_13().get(key,NmsItem.class);
	}
	static NmsItem fromId(String id)
	{
		return fromKey(NmsMinecraftKey.newInstance(id));
	}
	default NmsMinecraftKey getKey()
	{
		if(BukkitWrapper.version>=14)
			return NmsIRegistry.getItemsV14().getKeyV13(this);
		else if(BukkitWrapper.v13)
			return NmsIRegistry.getItemsV13_14().getKey(this);
		else
			return getRegistryV_13().getKey(this);
	}
	default String getId()
	{
		return getKey().toString();
	}
	
	@WrappedBukkitMethod(@VersionName(value="b",maxVer=13))
	String getNameV_13(NmsItemStack is);
	
	@WrappedBukkitFieldAccessor({@VersionName("maxStackSize"),@VersionName(value="c",minVer=17,maxVer=18.2f),@VersionName(value="d",minVer=18.2f)})
	int getMaxStackSize();
	@WrappedBukkitFieldAccessor({@VersionName("maxStackSize"),@VersionName(value="c",minVer=17,maxVer=18.2f),@VersionName(value="d",minVer=18.2f)})
	NmsItem setMaxStackSize(int count);
	
	@WrappedBukkitMethod(@VersionName(value="a",maxVer=13))
	String getTranslateKey0V_13(NmsItemStack is);
	default String getTranslateKeyV_13(NmsItemStack is)
	{
		//return getTranslateKey0V_13(is)+".name";
		Map<String,String> last=NmsLocaleI18nV_13.getLang().getMapV_13();
		try
		{
			String[] key={null};
			NmsLocaleI18nV_13.getLang().setMapV_13(new AbstractMap<String,String>()
			{
				@Override
				public Set<Entry<String,String>> entrySet()
				{
					return null;
				}
				@Override
				public String get(Object k)
				{
					return key[0]=(String) k;
				}
			});
			getNameV_13(is);
			return key[0];
		}
		finally
		{
			NmsLocaleI18nV_13.getLang().setMapV_13(last);
		}
	}
	@WrappedBukkitMethod({@VersionName(value="h",minVer=13,maxVer=14),@VersionName(value="f",minVer=14,maxVer=17),@VersionName(value="j",minVer=17)})
	String getTranslateKeyV13(NmsItemStack is);
	default String getTranslateKey(NmsItemStack is)
	{
		if(BukkitWrapper.v13)
			return getTranslateKeyV13(is);
		else
			return getTranslateKeyV_13(is);
	}
}
