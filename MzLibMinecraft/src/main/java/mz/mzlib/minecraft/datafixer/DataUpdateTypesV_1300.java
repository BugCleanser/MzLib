package mz.mzlib.minecraft.datafixer;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(end=1300)
@WrapMinecraftClass(@VersionName(name="net.minecraft.world.level.storage.LevelDataType"))
public interface DataUpdateTypesV_1300 extends WrapperObject, DataUpdateTypeV_1300
{
    @WrapperCreator
    static DataUpdateTypesV_1300 create(Object wrapped)
    {
        return WrapperObject.create(DataUpdateTypesV_1300.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="field_14384"))
    DataUpdateTypeV_1300 staticItemStack();
    static DataUpdateTypeV_1300 itemStack()
    {
        return create(null).staticItemStack();
    }
}
