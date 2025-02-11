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
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.ItemEntity"))
public interface EntityItem extends WrapperObject, Entity
{
    @WrapperCreator
    static EntityItem create(Object wrapped)
    {
        return WrapperObject.create(EntityItem.class, wrapped);
    }
    
    EntityType ENTITY_TYPE = EntityType.fromId(Identifier.ofMinecraft("item"));
    
    /**
     * typeV_1100: {@link Optional<ItemStack>}
     * typeV1100: {@link ItemStack}
     */
    static EntityDataKey dataKeyItem()
    {
        return create(null).staticDataTypeItem();
    }
    
    EntityDataKey staticDataTypeItem();
    
    @SpecificImpl("staticDataTypeItem")
    @VersionRange(end=900)
    default EntityDataKey staticDataTypeItemV_900()
    {
        return EntityDataKey.newInstanceV_900(10, (byte)5);
    }
    
    @SpecificImpl("staticDataTypeItem")
    @VersionRange(begin=900)
    @WrapMinecraftFieldAccessor(@VersionName(name="STACK"))
    EntityDataKey staticDataTypeItemV900();
    
    EntityDataAdapter<ItemStack> DATA_ADAPTER_ITEM = new EntityDataAdapter<>(dataKeyItem(), //
            MinecraftPlatform.instance.getVersion()>=900 && MinecraftPlatform.instance.getVersion()<1100 ? //
                    InvertibleFunction.wrap(ItemStack::create).inverse().thenApply(InvertibleFunction.optional()).thenCast() : //
                    new InvertibleFunction<>(ItemStack::getWrapped, ItemStack::create));
}
