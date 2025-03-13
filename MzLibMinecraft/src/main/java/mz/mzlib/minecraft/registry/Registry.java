package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.util.registry.MutableRegistry", end=1300), @VersionName(name="net.minecraft.util.registry.Registry", begin=1300, end=1903), @VersionName(name="net.minecraft.registry.Registry", begin=1903)})
public interface Registry extends WrapperObject
{
    WrapperFactory<Registry> FACTORY = WrapperFactory.find(Registry.class);
    @Deprecated
    @WrapperCreator
    static Registry create(Object wrapped)
    {
        return WrapperObject.create(Registry.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getId", begin=1300))
    Identifier getIdV1300(WrapperObject value);
    
    
    WrapperObject get(Identifier id);
    
    @VersionRange(end=1300)
    @WrapMinecraftMethod(@VersionName(name="get"))
    Object get0V_1300(Object id);
    
    @SpecificImpl("get")
    @VersionRange(end=1300)
    default WrapperObject getV_1300(Identifier id)
    {
        return WrapperObject.create(this.get0V_1300(id.getWrapped()));
    }
    
    @SpecificImpl("get")
    @VersionRange(begin=1300)
    @WrapMinecraftMethod({@VersionName(name="getByIdentifier", end=1400), @VersionName(name="get", begin=1400)})
    WrapperObject getV1300(Identifier id);
    
    default WrapperObject get(int rawId)
    {
        return this.castTo(SimpleRegistry::create).get(rawId);
    }
}
