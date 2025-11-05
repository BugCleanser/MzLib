package mz.mzlib.minecraft.datafixer;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=900, end=1300)
@WrapMinecraftClass(@VersionName(name="net.minecraft.world.level.storage.LevelDataType"))
public interface DataUpdateTypesV900_1300 extends WrapperObject, DataUpdateTypeV900_1300
{
    WrapperFactory<DataUpdateTypesV900_1300> FACTORY = WrapperFactory.of(DataUpdateTypesV900_1300.class);
    @Deprecated
    @WrapperCreator
    static DataUpdateTypesV900_1300 create(Object wrapped)
    {
        return WrapperObject.create(DataUpdateTypesV900_1300.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="field_14384"))
    DataUpdateTypeV900_1300 staticItemStack();
    static DataUpdateTypeV900_1300 itemStack()
    {
        return FACTORY.getStatic().staticItemStack();
    }
}
