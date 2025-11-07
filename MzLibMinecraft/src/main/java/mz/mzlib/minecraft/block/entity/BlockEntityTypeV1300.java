package mz.mzlib.minecraft.block.entity;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.RegistriesV1300;
import mz.mzlib.minecraft.registry.Registry;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1300)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.block.entity.BlockEntityType"))
public interface BlockEntityTypeV1300 extends WrapperObject
{
    WrapperFactory<BlockEntityTypeV1300> FACTORY = WrapperFactory.of(BlockEntityTypeV1300.class);
    @Deprecated
    @WrapperCreator
    static BlockEntityTypeV1300 create(Object wrapped)
    {
        return WrapperObject.create(BlockEntityTypeV1300.class, wrapped);
    }

    static Registry getRegistry()
    {
        return RegistriesV1300.blockEntityType();
    }

    static BlockEntityTypeV1300 fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }

    static BlockEntityTypeV1300 fromId(Identifier id)
    {
        return getRegistry().get(id).castTo(BlockEntityTypeV1300.FACTORY);
    }
}
