package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.EntityType")) // TODO
public interface EntityType extends WrapperObject
{
    @WrapperCreator
    static EntityType create(Object wrapped)
    {
        return WrapperObject.create(EntityType.class, wrapped);
    }

    static EntityType fromId(Identifier id)
    {
        return null; // TODO
    }
    
    default Identifier getId()
    {
        // TODO
        throw new UnsupportedOperationException();
    }
}
