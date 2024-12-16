package mz.mzlib.minecraft.version;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.world.level.storage.LevelDataType", end=1400))
public interface DataUpdateTypesV_1400 extends WrapperObject, DataUpdateTypeV_1400
{
    @WrapperCreator
    static DataUpdateTypesV_1400 create(Object wrapped)
    {
        return WrapperObject.create(DataUpdateTypesV_1400.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="field_14384"))
    DataUpdateTypeV_1400 staticItemStack();
    static DataUpdateTypeV_1400 itemStack()
    {
        return create(null).staticItemStack();
    }
}
