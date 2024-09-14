package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.EntityType")) // TODO
public interface EntityTypeTODO extends WrapperObject
{
    @WrapperCreator
    static EntityTypeTODO create(Object wrapped)
    {
        return WrapperObject.create(EntityTypeTODO.class, wrapped);
    }

    static EntityTypeTODO fromId(Identifier id)
    {
        return null; // TODO
    }

    Identifier getId(); // TODO
}
