package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.io.DataInput;
import java.io.DataOutput;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.nbt.NbtElement", end = 1400),
                @VersionName(name = "net.minecraft.nbt.Tag", begin = 1400, end = 1605),
                @VersionName(name = "net.minecraft.nbt.NbtElement", begin = 1605)
        })
public interface NbtElement extends WrapperObject
{
    WrapperFactory<NbtElement> FACTORY = WrapperFactory.of(NbtElement.class);
    @Deprecated
    @WrapperCreator
    static NbtElement create(Object wrapped)
    {
        return WrapperObject.create(NbtElement.class, wrapped);
    }

    @WrapMinecraftMethod(@VersionName(name="getType"))
    byte getTypeId();
    
    @WrapMinecraftMethod({@VersionName(name="getReader", begin=1500, end=1605), @VersionName(name="getNbtType", begin=1605)})
    NbtElementTypeV1500 getTypeV1500();
    
    @WrapMinecraftMethod(@VersionName(name="copy"))
    NbtElement copy0();
    default NbtElement copy()
    {
        return (NbtElement)this.copy0().castTo(new WrapperFactory<>(this));
    }
    
    @WrapMinecraftMethod(@VersionName(name="read", end=1500))
    void loadV_1500(DataInput input, int depth, NbtReadingCounter counter);
    
    /**
     * @deprecated this method will not save the type of the tag
     * @see NbtIo
     */
    @Deprecated
    @WrapMinecraftMethod(@VersionName(name="write"))
    void save(DataOutput output);
}
