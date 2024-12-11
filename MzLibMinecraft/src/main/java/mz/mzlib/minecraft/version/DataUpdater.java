package mz.mzlib.minecraft.version;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.datafixer.DataFixerUpper", end=1400)})
public interface DataUpdater extends WrapperObject
{
    @WrapperCreator
    static DataUpdater create(Object wrapped)
    {
        return WrapperObject.create(DataUpdater.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="dataVersion", end=1400))
    int getDataVersionV_1400();
    
    @WrapMinecraftMethod(@VersionName(name="applyDataFixes", end=1400))
    NbtCompound updateV_1400(DataUpdateTypeV_1400 type, NbtCompound data, int from);
}
