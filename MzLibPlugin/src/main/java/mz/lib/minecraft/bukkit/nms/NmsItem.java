package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.item.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedObject;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

@VersionalWrappedClass({@VersionalName(value="nms.Item",maxVer=17),@VersionalName(value="net.minecraft.world.item.Item",minVer=17)})
public interface NmsItem extends VersionalWrappedObject, Item
{
	@VersionalWrappedFieldAccessor(@VersionalName(maxVer=13,value="REGISTRY"))
	NmsRegistryMaterials staticGetRegistryV_13();
	static NmsRegistryMaterials getRegistryV_13()
	{
		return WrappedObject.getStatic(NmsItem.class).staticGetRegistryV_13();
	}
	
	static NmsItem fromId(NmsMinecraftKey key)
	{
		if(Server.instance.version>=14)
			return NmsIRegistry.getItemsV14().get(key,NmsItem.class);
		else if(Server.instance.v13)
			return NmsIRegistry.getItemsV13_14().get(key,NmsItem.class);
		else
			return getRegistryV_13().get(key,NmsItem.class);
	}
	static NmsItem fromStringId(String id)
	{
		return fromId(NmsMinecraftKey.newInstance(id));
	}
	default NmsMinecraftKey getId()
	{
		if(Server.instance.version>=14)
			return NmsIRegistry.getItemsV14().getKeyV13(this);
		else if(Server.instance.v13)
			return NmsIRegistry.getItemsV13_14().getKey(this);
		else
			return getRegistryV_13().getKey(this);
	}
	default String getStringId()
	{
		return getId().toString();
	}
	
	@VersionalWrappedMethod(@VersionalName(value="b",maxVer=13))
	String getNameV_13(NmsItemStack is);
	
	@VersionalWrappedFieldAccessor({@VersionalName("maxStackSize"),@VersionalName(value="c",minVer=17,maxVer=18.2f),@VersionalName(value="d",minVer=18.2f)})
	int getMaxStackSize();
	@VersionalWrappedFieldAccessor({@VersionalName("maxStackSize"),@VersionalName(value="c",minVer=17,maxVer=18.2f),@VersionalName(value="d",minVer=18.2f)})
	NmsItem setMaxStackSize(int count);
	
	@VersionalWrappedMethod(@VersionalName(value="a",maxVer=13))
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
	@VersionalWrappedMethod({@VersionalName(value="h",minVer=13,maxVer=14),@VersionalName(value="f",minVer=14,maxVer=17),@VersionalName(value="j",minVer=17)})
	String getTranslateKeyV13(NmsItemStack is);
	default String getTranslateKey(NmsItemStack is)
	{
		if(Server.instance.v13)
			return getTranslateKeyV13(is);
		else
			return getTranslateKeyV_13(is);
	}
}
