package mz.mzlib.minecraft;

import mz.mzlib.minecraft.registry.Registry;
import mz.mzlib.minecraft.registry.RegistryKeyV1600;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

/**
 * yarn V1802_1903: net.minecraft.tag.TagKey
 * yarn V1903: net.minecraft.registry.tag.TagKey
 */
@VersionRange(begin = 1300)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.tag.Tag", end = 1802),
    @VersionName(name = "net.minecraft.class_6862", begin = 1802)
})
public interface TagV1300<T extends WrapperObject> extends WrapperObject
{
    WrapperFactory<TagV1300<?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(TagV1300.class));

    Identifier getIdV_1802();
    @SpecificImpl("getIdV_1802")
    @VersionRange(end = 1600)
    @WrapMinecraftMethod(@VersionName(name = "getId"))
    Identifier getIdV_1600();
    @SpecificImpl("getIdV_1802")
    @VersionRange(begin = 1600, end = 1802)
    default Identifier getIdV1600_1802()
    {
        return this.as(class_5123V1600_1802.FACTORY).getId();
    }

    static <T extends WrapperObject> TagV1300<T> ofV1802(RegistryKeyV1600<Registry.Wrapper<T>> type, Identifier id)
    {
        return FACTORY.getStatic().static$ofV1802(type, id);
    }


    @VersionRange(begin = 1600, end = 1802)
    @WrapMinecraftInnerClass(outer = TagV1300.class, name = @VersionName(name = "class_5123"))
    interface class_5123V1600_1802<T extends WrapperObject> extends TagV1300<T>
    {
        WrapperFactory<class_5123V1600_1802<?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(class_5123V1600_1802.class));

        @WrapMinecraftMethod(@VersionName(name = "getId"))
        Identifier getId();
    }

    @VersionRange(begin = 1802)
    @WrapMinecraftMethod(@VersionName(name = "of"))
    <T1 extends WrapperObject> TagV1300<T1> static$ofV1802(RegistryKeyV1600<Registry.Wrapper<T1>> type, Identifier id);
}
