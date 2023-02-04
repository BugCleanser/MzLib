package mz.lib.minecraft.bukkit.mzlang;

import mz.lib.*;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.mzlang.*;

import java.lang.reflect.*;

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
