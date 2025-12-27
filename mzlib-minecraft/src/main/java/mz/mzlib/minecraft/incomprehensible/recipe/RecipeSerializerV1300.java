package mz.mzlib.minecraft.incomprehensible.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.codec.BinaryCodecV2005;
import mz.mzlib.minecraft.serialization.CodecV1600;
import mz.mzlib.minecraft.serialization.MapCodecV1600;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1300)
@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.class_3578", end = 1400),
        @VersionName(name = "net.minecraft.recipe.RecipeSerializer", begin = 1400)
    }
)
public interface RecipeSerializerV1300 extends WrapperObject
{
    WrapperFactory<RecipeSerializerV1300> FACTORY = WrapperFactory.of(RecipeSerializerV1300.class);

    @VersionRange(begin = 2002, end = 2005)
    @WrapMinecraftMethod(@VersionName(name = "codec"))
    CodecV1600<Object> getCodecV2002_2005();

    @VersionRange(begin = 2005)
    @WrapMinecraftMethod(@VersionName(name = "codec"))
    MapCodecV1600<Object> getCodecV2005();

    @VersionRange(begin = 2005)
    @WrapMinecraftMethod(@VersionName(name = "packetCodec"))
    BinaryCodecV2005<Object, Object> getBinaryCodecV2005();
}
