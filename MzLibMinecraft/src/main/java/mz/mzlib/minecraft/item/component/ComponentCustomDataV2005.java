package mz.mzlib.minecraft.item.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.component.type.NbtComponent", begin=2005))
public interface ComponentCustomDataV2005 extends WrapperObject
{
    @WrapperCreator
    static ComponentCustomDataV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentCustomDataV2005.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="nbt"))
    NbtCompound getData();
    
    @WrapConstructor
    ComponentCustomDataV2005 staticNewInstance(NbtCompound data);
    static ComponentCustomDataV2005 newInstance(NbtCompound data)
    {
        return create(null).staticNewInstance(data);
    }
}
