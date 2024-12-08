package mz.mzlib.minecraft;

import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.util.Identifier"))
public interface Identifier extends WrapperObject
{
    @WrapperCreator
    static Identifier create(Object wrapped)
    {
        return WrapperObject.create(Identifier.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="namespace"))
    String getNamespace();
    @WrapMinecraftFieldAccessor(@VersionName(name="path"))
    String getName();

    @WrapConstructor
    Identifier staticNewInstance(String namespace, String name);
    static Identifier newInstance(String namespace, String name)
    {
        return create(null).staticNewInstance(namespace, name);
    }
    static Identifier ofMinecraft(String name)
    {
        return newInstance("minecraft", name);
    }
    static Identifier newInstance(String str)
    {
        String[] result=str.split(":",2);
        if(result.length==2)
            return newInstance(result[0], result[1]);
        else
            return ofMinecraft(str);
    }
}
