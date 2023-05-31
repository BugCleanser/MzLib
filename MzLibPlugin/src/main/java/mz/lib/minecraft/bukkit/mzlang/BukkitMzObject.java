package mz.lib.minecraft.bukkit.mzlang;

import mz.lib.ClassUtil;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.mzlang.MzObject;

import java.lang.reflect.Method;

public interface BukkitMzObject extends MzObject
{
	@Override
	default String[] getRefactorSignNames(Method method)
	{
		RefactorBukkitSign a=ClassUtil.getSuperAnnotation(method,RefactorBukkitSign.class);
		if(a!=null)
			return BukkitWrapper.inVersion(a.value());
		return MzObject.super.getRefactorSignNames(method);
	}
}
