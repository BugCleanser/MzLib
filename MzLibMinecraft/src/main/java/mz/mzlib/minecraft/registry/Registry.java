package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.util.registry.Registry", end=1903), @VersionName(name="net.minecraft.registry.Registry", begin=1903)})
public interface Registry extends WrapperObject
{
    @WrapperCreator
    static Registry create(Object wrapped)
    {
        return WrapperObject.create(Registry.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getId", begin=1300))
    Identifier getIdV1300(WrapperObject value);
    
    @WrapMinecraftMethod(@VersionName(name="get"))
    WrapperObject get(Identifier id);
    
    default WrapperObject get(int rawId)
    {
        return this.castTo(SimpleRegistry::create).get(rawId);
    }
}
