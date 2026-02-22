package mz.mzlib.minecraft.incomprehensible.registry;

import io.netty.buffer.ByteBuf;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Function;

@VersionRange(begin = 2005)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.network.RegistryByteBuf"))
public interface ByteBufWithRegistriesV2005 extends WrapperObject
{
    WrapperFactory<ByteBufWithRegistriesV2005> FACTORY = WrapperFactory.of(ByteBufWithRegistriesV2005.class);
    @Override
    ByteBuf getWrapped();

    static Function<ByteBuf, ? extends ByteBuf> method_56350(RegistryManagerV1602 registries)
    {
        return FACTORY.getStatic().static$method_56350(registries);
    }
    @WrapMinecraftMethod(@VersionName(name = "method_56350"))
    Function<ByteBuf, ? extends ByteBuf> static$method_56350(RegistryManagerV1602 registries);
}

