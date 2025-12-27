package mz.mzlib.minecraft.network.packet.codec;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.PropAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.BiConsumer;
import java.util.function.Function;

@VersionRange(begin = 2005)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.network.codec.PacketCodec"))
public interface BinaryCodecV2005<B, V> extends WrapperObject, BinaryEncoderV2005<B, V>, BinaryDecoderV2005<B, V>
{
    WrapperFactory<BinaryCodecV2005<?, ?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(BinaryCodecV2005.class));

    static <B, V> BinaryCodecV2005<B, V> of(BiConsumer<B, V> encoder, Function<B, V> decoder)
    {
        return Impl.of(encoder, decoder);
    }


    @VersionRange(begin = 2005)
    @Compound
    interface Impl<B, V> extends BinaryCodecV2005<B, V>
    {
        WrapperFactory<Impl<?, ?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(Impl.class));

        @PropAccessor("encoder")
        BiConsumer<B, V> getEncoder();
        @PropAccessor("encoder")
        void setEncoder(BiConsumer<B, V> encoder);

        @PropAccessor("decoder")
        Function<B, V> getDecoder();
        @PropAccessor("decoder")
        void setDecoder(Function<B, V> decoder);

        static <B, V> Impl<B, V> of(BiConsumer<B, V> encoder, Function<B, V> decoder)
        {
            Impl<B, V> result = FACTORY.getStatic().static$of();
            result.setEncoder(encoder);
            result.setDecoder(decoder);
            return result;
        }


        @WrapConstructor
        <B1, V1> Impl<B1, V1> static$of();

        @Override
        @CompoundOverride(parent = BinaryEncoderV2005.class, method = "encode")
        default void encode(B buf, V value)
        {
            this.getEncoder().accept(buf, value);
        }

        @Override
        @CompoundOverride(parent = BinaryDecoderV2005.class, method = "decode")
        default V decode(B buf)
        {
            return this.getDecoder().apply(buf);
        }
    }
}
