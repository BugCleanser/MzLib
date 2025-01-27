package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(end=1300)
@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.EntityType"))
public interface EntityTypesV_1300 extends WrapperObject
{
    @WrapperCreator
    static EntityTypesV_1300 create(Object wrapped)
    {
        return WrapperObject.create(EntityTypesV_1300.class, wrapped);
    }
    
    static Registry registry()
    {
        return create(null).staticRegistry();
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="REGISTRY"))
    Registry staticRegistry();
}
