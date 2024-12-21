package mz.mzlib.minecraft.item.component;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.RegistriesV1903;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.component.DataComponentType", begin=2005, end=2100), @VersionName(name="net.minecraft.component.ComponentType", begin=2100)})
public interface ComponentKeyV2005 extends WrapperObject
{
    @WrapperCreator
    static ComponentKeyV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentKeyV2005.class, wrapped);
    }
    
    static ComponentKeyV2005 fromId(Identifier id)
    {
        return RegistriesV1903.componentKey().get(id).castTo(ComponentKeyV2005::create);
    }
    static ComponentKeyV2005 fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }
}
