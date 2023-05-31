package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.*;
import mz.mzlib.wrapper.*;
import org.bukkit.NamespacedKey;

import java.util.*;

@VersionalWrappedClass({@VersionalName(value="nms.MinecraftKey",maxVer=17),@VersionalName(value="net.minecraft.resources.MinecraftKey",minVer=17)})
public interface NmsMinecraftKey extends VersionalWrappedObject, Identifier
{
	static NmsMinecraftKey newInstance(String namespace,String key)
	{
		return WrappedObject.getStatic(NmsMinecraftKey.class).staticNewInstance(namespace,key);
	}
	static NmsMinecraftKey newInstance(String s)
	{
		return WrappedObject.getStatic(NmsMinecraftKey.class).staticNewInstance(s);
	}
	static NmsMinecraftKey newInstance(NamespacedKey key)
	{
		return newInstance(key.getNamespace(),key.getKey());
	}
	@WrappedConstructor
	NmsMinecraftKey staticNewInstance(String namespace,String key);
	@WrappedConstructor
	NmsMinecraftKey staticNewInstance(String s);
	@VersionalWrappedFieldAccessor(@VersionalName({"namespace","@0"}))
	String getNamespace();
	@VersionalWrappedFieldAccessor(@VersionalName({"key","@1"}))
	String getKey();
	
	@SuppressWarnings("deprecation")
	default NamespacedKey toBukkit()
	{
		return new NamespacedKey(getNamespace(),getKey());
	}
	
	static NmsMinecraftKey random()
	{
		return NmsMinecraftKey.newInstance("mzlib",UUID.randomUUID().toString().replace("-",""));
	}
}
