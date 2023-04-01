package mz.lib.minecraft.mzlang;

import mz.lib.*;
import mz.lib.minecraft.*;
import mz.lib.mzlang.*;
import mz.lib.*;

import java.lang.reflect.*;

public interface VersionMzObject extends MzObject
{
	@Override
	default String[] getRefactorSignNames(Method method)
	{
		RefactorVersionSign a=ClassUtil.getSuperAnnotation(method,RefactorVersionSign.class);
		if(a!=null)
			return Server.instance.inVersion(a.value());
		return MzObject.super.getRefactorSignNames(method);
	}
}
