package mz.lib.minecraft.bukkit.nms;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.TypeUtil;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;

import java.util.Map;

@VersionalWrappedClass({@VersionalName(value="nms.LocaleLanguage",maxVer=17),@VersionalName(value="net.minecraft.locale.LocaleLanguage",minVer=17)})
public interface NmsLocaleLanguage extends VersionalWrappedObject
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
	@VersionalWrappedFieldAccessor(@VersionalName(maxVer=13, value="d"))
	Map<String,String> getMapV_13();
	@VersionalWrappedFieldAccessor(@VersionalName(maxVer=13, value="d"))
	NmsLocaleLanguage setMapV_13(Map<String,String> map);
}
