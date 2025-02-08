package mz.mzlib.minecraft.entity;

import com.google.common.base.Optional;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.data.EntityDataAdapter;
import mz.mzlib.minecraft.entity.data.EntityDataKey;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.ThrowableFunction;
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
    
    @WrapMinecraftFieldAccessor(@VersionName(name="STACK"))
    EntityDataKey staticDataTypeItem();
    
    EntityDataAdapter<ItemStack> DATA_ADAPTER_ITEM = new EntityDataAdapter<>(dataKeyItem(), //
            MinecraftPlatform.instance.getVersion()<1100 ? new InvertibleFunction<>( //
                    ThrowableFunction.of(ItemStack::getWrapped).andThen(Optional::fromNullable), //
                    RuntimeUtil.<Optional<?>>functionCast().andThen(Optional::orNull).andThen(ItemStack::create)) : //
                    new InvertibleFunction<>(ItemStack::getWrapped, ItemStack::create));
}
