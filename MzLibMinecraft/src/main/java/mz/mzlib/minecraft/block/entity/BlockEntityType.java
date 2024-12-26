package mz.mzlib.minecraft.block.entity;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.RegistriesV1903;
import mz.mzlib.minecraft.registry.Registry;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.block.entity.BlockEntityType"))
public interface BlockEntityType extends WrapperObject
{
    @WrapperCreator
    static BlockEntityType create(Object wrapped)
    {
        return WrapperObject.create(BlockEntityType.class, wrapped);
    }
    
    static Registry getRegistry()
    {
        return create(null).staticGetRegistry();
    }
    Registry staticGetRegistry();
    @SpecificImpl("staticGetRegistry")
    @VersionRange(end=1903)
    default Registry staticGetRegistryV_1903()
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    @SpecificImpl("staticGetRegistry")
    @VersionRange(begin=1903)
    default Registry staticGetRegistryV1903()
    {
        return RegistriesV1903.blockEntityType();
    }
    
    static BlockEntityType fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }
    static BlockEntityType fromId(Identifier id)
    {
        return getRegistry().get(id).castTo(BlockEntityType::create);
    }
}
