package mz.mzlib.minecraft.registry.entry;

import mz.mzlib.minecraft.TagV1300;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.RegistryKeyV1600;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;

@VersionRange(begin = 1903)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.registry.RegistryEntryLookup"))
public interface RegistryEntryLookupV1903 extends WrapperObject
{
    WrapperFactory<RegistryEntryLookupV1903> FACTORY = WrapperFactory.of(RegistryEntryLookupV1903.class);

    default Option<RegistryEntryListV1903> get(TagV1300<?> tag)
    {
        return Option.fromOptional(this.get0(tag)).map(RegistryEntryListV1903.FACTORY::create);
    }


    @WrapMinecraftMethod(@VersionName(name = "getOptional"))
    Optional<?> get0(TagV1300<?> tag);


    @VersionRange(begin = 1903)
    @WrapMinecraftInnerClass(outer = RegistryEntryLookupV1903.class, name = @VersionName(name = "RegistryLookup"))
    interface RegistryLookup extends WrapperObject
    {
        WrapperFactory<RegistryLookup> FACTORY = WrapperFactory.of(RegistryLookup.class);

        default Option<RegistryWrapperV1903> get(RegistryKeyV1600 key)
        {
            return Option.fromOptional(this.get0(key)).map(RegistryWrapperV1903.FACTORY::create);
        }


        @WrapMinecraftMethod(@VersionName(name = "getOptional"))
        Optional<?> get0(RegistryKeyV1600 key);
    }
}
