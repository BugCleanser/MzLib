package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.LocaleI18n",maxVer=13)})
public interface NmsLocaleI18nV_13 extends WrappedBukkitObject
{
	static NmsLocaleLanguage getLang()
	{
		return WrappedObject.getStatic(NmsLocaleI18nV_13.class).staticGetLang();
	}
	static void setLang(NmsLocaleLanguage lang)
	{
		WrappedObject.getStatic(NmsLocaleI18nV_13.class).staticSetLang(lang);
	}
	@WrappedBukkitFieldAccessor(value=@VersionName(maxVer=13, value="a"))
	NmsLocaleI18nV_13 staticSetLang(NmsLocaleLanguage lang);
	@WrappedBukkitFieldAccessor(value=@VersionName(maxVer=13, value="a"))
	NmsLocaleLanguage staticGetLang();
}
