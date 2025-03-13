package mz.mzlib.minecraft.datafixer;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=900, end=1300)
@WrapMinecraftClass({@VersionName(name="net.minecraft.datafixer.DataFixerUpper")})
public interface DataUpdaterV900_1300 extends WrapperObject
{
    WrapperFactory<DataUpdaterV900_1300> FACTORY = WrapperFactory.find(DataUpdaterV900_1300.class);
    @Deprecated
    @WrapperCreator
    static DataUpdaterV900_1300 create(Object wrapped)
    {
        return WrapperObject.create(DataUpdaterV900_1300.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="dataVersion"))
    int getDataVersion();
    
    @WrapMinecraftMethod(@VersionName(name="applyDataFixes"))
    NbtCompound update(DataUpdateTypeV900_1300 type, NbtCompound data, int from);
}
