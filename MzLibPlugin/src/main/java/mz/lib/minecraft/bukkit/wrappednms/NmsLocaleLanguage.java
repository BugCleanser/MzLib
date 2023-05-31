package mz.lib.minecraft.bukkit.wrappednms;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedObject;

import java.util.Map;

@WrappedBukkitClass({@VersionName(value="nms.LocaleLanguage",maxVer=17),@VersionName(value="net.minecraft.locale.LocaleLanguage",minVer=17)})
public interface NmsLocaleLanguage extends WrappedBukkitObject
{
	static NmsLocaleLanguage newInstanceV_13(Map<String,String> map)
	{
		NmsLocaleLanguage r;
		try
		{
			r=WrappedObject.wrap(NmsLocaleLanguage.class,Root.getUnsafe().allocateInstance(WrappedObject.getRawClass(NmsLocaleLanguage.class)));
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
		return r.setMapV_13(map);
	}
	@WrappedBukkitFieldAccessor(@VersionName(maxVer=13, value="d"))
	Map<String,String> getMapV_13();
	@WrappedBukkitFieldAccessor(@VersionName(maxVer=13, value="d"))
	NmsLocaleLanguage setMapV_13(Map<String,String> map);
}
