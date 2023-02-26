package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.LocaleI18n",maxVer=13)})
public interface NmsLocaleI18nV_13 extends VersionalWrappedObject
{
	static NmsLocaleLanguage getLang()
	{
		return WrappedObject.getStatic(NmsLocaleI18nV_13.class).staticGetLang();
	}
	static void setLang(NmsLocaleLanguage lang)
	{
		WrappedObject.getStatic(NmsLocaleI18nV_13.class).staticSetLang(lang);
	}
	@VersionalWrappedFieldAccessor(value=@VersionalName(maxVer=13, value="a"))
	NmsLocaleI18nV_13 staticSetLang(NmsLocaleLanguage lang);
	@VersionalWrappedFieldAccessor(value=@VersionalName(maxVer=13, value="a"))
	NmsLocaleLanguage staticGetLang();
}
