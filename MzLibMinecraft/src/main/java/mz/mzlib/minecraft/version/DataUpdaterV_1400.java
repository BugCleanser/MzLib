package mz.mzlib.minecraft.version;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.datafixer.DataFixerUpper", end=1400)})
public interface DataUpdaterV_1400 extends WrapperObject
{
    @WrapperCreator
    static DataUpdaterV_1400 create(Object wrapped)
    {
        return WrapperObject.create(DataUpdaterV_1400.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="dataVersion", end=1400))
    int getDataVersion();
    
    @WrapMinecraftMethod(@VersionName(name="applyDataFixes", end=1400))
    NbtCompound update(DataUpdateTypeV_1400 type, NbtCompound data, int from);
}
