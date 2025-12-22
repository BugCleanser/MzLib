package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.*;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * yarn V_1903: net.minecraft.tag.ItemTags
 * yarn V1903: net.minecraft.registry.tag.ItemTags
 */
@VersionRange(begin = 1300)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.tag.ItemTags", end = 1400),
    @VersionName(name = "net.minecraft.class_3489", begin = 1400)
})
public interface ItemTagsV1300 extends WrapperObject
{
    WrapperFactory<ItemTagsV1300> FACTORY = WrapperFactory.of(ItemTagsV1300.class);

    TagV1300<Item> WOOL = of("wool");
    TagV1300<Item> PLANKS = of("planks");

    static TagV1300<Item> of(String id)
    {
        return FACTORY.getStatic().static$of(id);
    }


    TagV1300<Item> static$of(String id);
    @SpecificImpl("static$of")
    @VersionRange(end = 1802)
    default TagV1300<Item> static$ofV_1802(String id)
    {
        return Option.fromNullable(Private.cacheV_1802.get(Identifier.newInstance(id))).unwrap(() -> new IllegalArgumentException(id));
    }
    @SpecificImpl("static$of")
    @VersionRange(begin = 1802)
    @WrapMinecraftMethod(@VersionName(name = "method_15102"))
    TagV1300<Item> static$ofV1802(String id);

    class Private
    {
        static Map<Identifier, TagV1300<Item>> cacheV_1802 = RuntimeUtil.sneakilyRun(() ->
        {
            if(MinecraftPlatform.instance.getVersion() >= 1802)
                return RuntimeUtil.nul();
            Map<Identifier, TagV1300<Item>> result = new HashMap<>();
            for(Field field : FACTORY.getWrappedClass().getDeclaredFields())
            {
                if(!Modifier.isStatic(field.getModifiers()) || !TagV1300.FACTORY.getWrappedClass().isAssignableFrom(field.getType()))
                    continue;
                TagV1300<Item> value = RuntimeUtil.cast(TagV1300.FACTORY.create(
                    ClassUtil.findFieldGetter(field.getDeclaringClass(), true, field.getName()).invoke()));
                result.put(value.getIdV_1802(), value);
            }
            return result;
        });
    }
}
