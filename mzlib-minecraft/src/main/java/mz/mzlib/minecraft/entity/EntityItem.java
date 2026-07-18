package mz.mzlib.minecraft.entity;

import com.google.common.base.Optional;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.data.EntityDataAdapter;
import mz.mzlib.minecraft.entity.data.EntityDataKey;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.wrapper.Impl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.ItemEntity"))
public interface EntityItem extends WrapperObject, Entity
{
    WrapperFactory<EntityItem> FACTORY = WrapperFactory.of(EntityItem.class);
    EntityType ENTITY_TYPE = EntityType.fromId(Identifier.minecraft("item"));

    /**
     * typeV_1100: {@link Optional<ItemStack>}
     * typeV1100: {@link ItemStack}
     */
    static EntityDataKey<?> dataKeyItem()
    {
        return FACTORY.getStatic().static$dataTypeItem();
    }

    EntityDataKey<?> static$dataTypeItem();

    @Impl("static$dataTypeItem")
    @VersionRange(end = 900)
    default EntityDataKey<?> static$dataTypeItemV_900()
    {
        return EntityDataKey.newInstanceV_900(10, (byte) 5);
    }

    @Impl("static$dataTypeItem")
    @VersionRange(begin = 900)
    @WrapMinecraftFieldAccessor(@VersionName(name = "STACK"))
    EntityDataKey<?> static$dataTypeItemV900();

    EntityDataAdapter<ItemStack> DATA_ADAPTER_ITEM = new EntityDataAdapter<>(
        dataKeyItem(), //
        MinecraftPlatform.instance.getVersion() >= 900 && MinecraftPlatform.instance.getVersion() < 1100 ? //
            FunctionInvertible.wrapper(ItemStack.FACTORY).inverse().thenApply(Optional::fromNullable, Optional::orNull)
                .thenCast() : //
            FunctionInvertible.of(ItemStack::getWrapped, ItemStack.FACTORY::create).thenCast()
    );
}

