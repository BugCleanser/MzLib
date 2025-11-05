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
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.ItemEntity"))
public interface EntityItem extends WrapperObject, Entity
{
    WrapperFactory<EntityItem> FACTORY = WrapperFactory.of(EntityItem.class);
    @Deprecated
    @WrapperCreator
    static EntityItem create(Object wrapped)
    {
        return WrapperObject.create(EntityItem.class, wrapped);
    }
    
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
    
    @SpecificImpl("static$dataTypeItem")
    @VersionRange(end=900)
    default EntityDataKey<?> static$dataTypeItemV_900()
    {
        return EntityDataKey.newInstanceV_900(10, (byte)5);
    }
    
    @SpecificImpl("static$dataTypeItem")
    @VersionRange(begin=900)
    @WrapMinecraftFieldAccessor(@VersionName(name="STACK"))
    EntityDataKey<?> static$dataTypeItemV900();
    
    EntityDataAdapter<ItemStack> DATA_ADAPTER_ITEM = new EntityDataAdapter<>(dataKeyItem(), //
            MinecraftPlatform.instance.getVersion()>=900 && MinecraftPlatform.instance.getVersion()<1100 ? //
                    InvertibleFunction.wrapper(ItemStack.FACTORY).inverse().thenApply(Optional::fromNullable, Optional::orNull).thenCast() : //
                    new InvertibleFunction<>(ItemStack::getWrapped, ItemStack.FACTORY::create).thenCast());
}
