package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.RegistriesV1903;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.DataComponentTypes", begin=2005))
public interface ComponentKeysV2005 extends WrapperObject
{
    @WrapperCreator
    static ComponentKeysV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentKeysV2005.class, wrapped);
    }
    
    static ComponentKeyV2005 get(String id)
    {
        return get(Identifier.newInstance(id));
    }
    static ComponentKeyV2005 get(Identifier id)
    {
        return RegistriesV1903.componentKey().get(id).castTo(ComponentKeyV2005::create);
    }
}
