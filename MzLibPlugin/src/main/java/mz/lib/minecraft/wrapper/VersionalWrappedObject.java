package mz.lib.minecraft.wrapper;

import mz.lib.minecraft.*;
import mz.lib.wrapper.*;
import mz.mzlib.*;

import java.lang.annotation.*;
import java.lang.reflect.*;

@WrappedClass("java.lang.Object")
public interface VersionalWrappedObject extends WrappedObject
{
	@Override
	default Class<?> getAnnotationClass(Class<? extends WrappedObject> wrapper)
	{
		VersionalWrappedClass a=wrapper.getDeclaredAnnotation(VersionalWrappedClass.class);
		if(a!=null)
			for(String n: Server.instance.convertClassName(Server.instance.inVersion(a.value())))
			{
				try
				{
					return Class.forName(n,false,this.getClass().getClassLoader());
				}
				catch(ClassNotFoundException ignored)
				{
				}
			}
		return WrappedObject.super.getAnnotationClass(wrapper);
	}
	
	@Override
	default Member getRawMember(Class<?> rawClass,Method m,Annotation a)
	{
		if(a instanceof VersionalWrappedFieldAccessor)
		{
			String[] name=Server.instance.inVersion(((VersionalWrappedFieldAccessor)a).value());
			if(name.length>0)
				return WrappedObject.super.getRawMember(rawClass,m,new WrappedFieldAccessor()
				{
					public Class<? extends Annotation> annotationType()
					{
						return WrappedFieldAccessor.class;
					}
					public String[] value()
					{
						return name;
					}
				});
			else
				return null;
		}
		else if(a instanceof VersionalWrappedConstructor)
		{
			if(Server.instance.inVersion((VersionalWrappedConstructor)a))
				return WrappedObject.super.getRawMember(rawClass,m,new WrappedConstructor()
				{
					public Class<? extends Annotation> annotationType()
					{
						return WrappedConstructor.class;
					}
				});
			else
				return null;
		}
		else if(a instanceof VersionalWrappedMethod)
		{
			String[] name=Server.instance.inVersion(((VersionalWrappedMethod)a).value());
			if(name.length>0)
				return WrappedObject.super.getRawMember(rawClass,m,new WrappedMethod()
				{
					public Class<? extends Annotation> annotationType()
					{
						return WrappedMethod.class;
					}
					public String[] value()
					{
						return name;
					}
				});
			else
				return null;
		}
		return WrappedObject.super.getRawMember(rawClass,m,a);
	}
}
