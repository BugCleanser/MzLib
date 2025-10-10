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
        return create(null).staticNewInstance(namespace, name);
    }
    
    Identifier staticNewInstance(String namespace, String name);
    
    @VersionRange(end=900)
    @WrapConstructor
    Identifier staticNewInstanceV_900(String name);
    
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=900)
    default Identifier staticNewInstanceV_900(String namespace, String name)
    {
        Identifier result = staticNewInstanceV_900("");
        result.setNamespace(namespace);
        result.setName(name);
        return result;
    }
    
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=900)
    @WrapConstructor
    Identifier staticNewInstanceV900(String namespace, String name);
    
    static Identifier ofMinecraft(String name)
    {
        return newInstance("minecraft", name);
    }
    
    static Identifier newInstance(String str)
    {
        String[] result = str.split(":", 2);
        if(result.length==2)
            return newInstance(result[0], result[1]);
        else
            return ofMinecraft(str);
    }
}
