package mz.lib.minecraft.nothing;

import com.google.common.collect.Lists;
import mz.lib.minecraft.*;
import mz.mzlib.*;
import mz.lib.nothing.Nothing;
import mz.lib.nothing.NothingByteCode;
import mz.lib.nothing.NothingInject;
import mz.lib.nothing.NothingLocation;
import mz.lib.nothing.NothingPriority;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface VersionalNothing extends Nothing
{
	@Override
	default NothingInject[] getInjects(Method method)
	{
		List<NothingInject> r=Lists.newArrayList(Nothing.super.getInjects(method));
		for(VersionalNothingInject inject:method.getDeclaredAnnotationsByType(VersionalNothingInject.class))
		{
			String[] name=Server.instance.inVersion(inject.name());
			if(name.length>0)
				r.add(new NothingInject()
				{
					public Class<? extends Annotation> annotationType()
					{
						return NothingInject.class;
					}
					public NothingPriority priority()
					{
						return inject.priority();
					}
					public String[] name()
					{
						return name;
					}
					public Class<?>[] args()
					{
						return inject.args();
					}
					public NothingLocation location()
					{
						return inject.location();
					}
					public NothingByteCode byteCode()
					{
						return new NothingByteCode()
						{
							public Class<? extends Annotation> annotationType()
							{
								return NothingByteCode.class;
							}
							public int index()
							{
								return inject.byteCode().index();
							}
							public int opcode()
							{
								return inject.byteCode().opcode();
							}
							public Class<?> owner()
							{
								return inject.byteCode().owner();
							}
							public String[] name()
							{
								return Server.instance.inVersion(inject.byteCode().name());
							}
							public Class<?>[] methodArgs()
							{
								return inject.byteCode().methodArgs();
							}
							public int var()
							{
								return inject.byteCode().var();
							}
							public int label()
							{
								return inject.byteCode().label();
							}
						};
					}
					public int shift()
					{
						return inject.shift();
					}
					public boolean optional()
					{
						return inject.optional();
					}
				});
		}
		return r.toArray(new NothingInject[0]);
	}
}
