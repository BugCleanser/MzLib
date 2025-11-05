package mz.mzlib.minecraft;

import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.*;

@WrapMinecraftClass(@VersionName(name="net.minecraft.util.Identifier"))
public interface Identifier extends WrapperObject
{
    WrapperFactory<Identifier> FACTORY = WrapperFactory.of(Identifier.class);
    @Deprecated
    @WrapperCreator
    static Identifier create(Object wrapped)
    {
        return WrapperObject.create(Identifier.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="namespace"))
    String getNamespace();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="namespace"))
    void setNamespace(String value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="path"))
    String getName();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="path"))
    void setName(String value);
    
    static Identifier newInstance(String namespace, String name)
    {
        return FACTORY.getStatic().static$newInstance(namespace, name);
    }
    
    Identifier static$newInstance(String namespace, String name);
    
    @VersionRange(end=900)
    @WrapConstructor
    Identifier static$newInstanceV_900(String name);
    
    @SpecificImpl("static$newInstance")
    @VersionRange(end=900)
    default Identifier static$newInstanceV_900(String namespace, String name)
    {
        Identifier result = static$newInstanceV_900("");
        result.setNamespace(namespace);
        result.setName(name);
        return result;
    }
    
    @SpecificImpl("static$newInstance")
    @VersionRange(begin=900)
    @WrapConstructor
    Identifier static$newInstanceV900(String namespace, String name);
    
    static Identifier minecraft(String name)
    {
        return newInstance("minecraft", name);
    }
    static Identifier ofMinecraft(String name)
    {
        return minecraft(name);
    }
    
    static Identifier newInstance(String str)
    {
        String[] result = str.split(":", 2);
        if(result.length==2)
            return newInstance(result[0], result[1]);
        else
            return minecraft(str);
    }
}
