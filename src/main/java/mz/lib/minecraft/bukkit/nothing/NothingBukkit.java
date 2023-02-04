package mz.lib.minecraft.bukkit.nothing;

import com.google.common.collect.Lists;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.nothing.Nothing;
import mz.lib.nothing.NothingByteCode;
import mz.lib.nothing.NothingInject;
import mz.lib.nothing.NothingLocation;
import mz.lib.nothing.NothingPriority;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface NothingBukkit extends Nothing
{
	@Override
	default NothingInject[] getInjects(Method method)
	{
		List<NothingInject> r=Lists.newArrayList(Nothing.super.getInjects(method));
		for(NothingBukkitInject inject:method.getDeclaredAnnotationsByType(NothingBukkitInject.class))
		{
			String[] name=BukkitWrapper.inVersion(inject.name());
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
								return BukkitWrapper.inVersion(inject.byteCode().name());
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
