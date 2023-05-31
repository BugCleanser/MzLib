package mz.lib.minecraft.bukkit.nms;

import com.google.common.collect.BiMap;
import mz.lib.Optional;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.obc.ObcObject2IntMapV13_18;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.*;
import mz.mzlib.wrapper.*;

import java.util.Map;

@VersionalWrappedClass({@VersionalName(value="nms.RegistryMaterials",maxVer=17),@VersionalName(value="net.minecraft.core.RegistryMaterials",minVer=17)})
public interface NmsRegistryMaterials extends VersionalWrappedObject
{
	@VersionalWrappedMethod(@VersionalName(value="b",maxVer=13))
	WrappedObject getKeyV_13(WrappedObject value);
	@VersionalWrappedMethod({@VersionalName(value="getKey",minVer=13),@VersionalName(minVer=18,value="b")})
	NmsMinecraftKey getKeyV13(WrappedObject value);
	default NmsMinecraftKey getKey(WrappedObject value)
	{
		if(Server.instance.v13)
			return getKeyV13(value);
		else
			return getKeyV_13(value).cast(NmsMinecraftKey.class);
	}
	
	@VersionalWrappedMethod(@VersionalName(value="get",maxVer=13))
	WrappedObject getV_13(WrappedObject key);
	@VersionalWrappedMethod({@VersionalName(value="get",minVer=13),@VersionalName(value="a",minVer=18)})
	WrappedObject getV13(NmsMinecraftKey key);
	default WrappedObject get(NmsMinecraftKey key)
	{
		if(Server.instance.v13)
			return getV13(key);
		else
			return getV_13(key);
	}
	default <T extends WrappedObject> T get(NmsMinecraftKey key,Class<T> wrapper)
	{
		return get(key).cast(wrapper);
	}
	
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",minVer=13,maxVer=18.2f))
	BiMap<Object,Object> getKeyMapV13_182();
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",minVer=18.2f))
	Map<Object,Object> getKeyMapV182();
	default Map<Object,Object> getKeyMapV13()
	{
		if(Server.instance.version>=18.2)
			return getKeyMapV182();
		else
			return getKeyMapV13_182();
	}
	
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=13, value="a"),@VersionalName(minVer=13, maxVer=16, value="b")})
	NmsRegistryID getRegIDV_15();
	
	@Optional
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=16,value="@0",maxVer=18))
	ObcObject2IntMapV13_18 getIdMapV16_18_Paper();
	@Optional
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=18,value="@0"))
	WrappedObject2IntMapV18 getIdMapV18_Paper();
	@Optional
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=16,value="@0"))
	WrappedReference2IntOpenHashMapV13 getIdMapV16Paper();
	default Map<Object,Integer> getIdMapV16()
	{
		try
		{
			return getIdMapV16Paper().getRaw();
		}
		catch(Throwable e)
		{
			if(Server.instance.version>=18)
				return getIdMapV18_Paper().getRaw();
			else
				return getIdMapV16_18_Paper().getRaw();
		}
	}
	
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=16,value="@1",maxVer=18.2f))
	BiMap<Object,Object> getResourceKeyMapV16_182();
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=18.2f,value="@1"))
	Map<Object,Object> getResourceKeyMapV182();
	default Map<Object,Object> getResourceKeyMapV16()
	{
		if(Server.instance.version>=18.2f)
			return getResourceKeyMapV182();
		else
			return getResourceKeyMapV16_182();
	}
	
	@VersionalWrappedFieldAccessor({@VersionalName(minVer=16,value="@0",maxVer=18.2f),@VersionalName(minVer=18.2f,value="@3")})
	Map<Object,Object> getLifecycleMapV16();
	
	@VersionalWrappedMethod(@VersionalName("@0"))
	int getId(WrappedObject obj);
	
	@VersionalWrappedMethod(@VersionalName({"fromId","@0"}))
	WrappedObject fromId(int id);
	default <T extends WrappedObject> T fromId(int id,Class<T> wrapper)
	{
		return fromId(id).cast(wrapper);
	}
}
