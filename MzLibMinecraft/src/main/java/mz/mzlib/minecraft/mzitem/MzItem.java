package mz.mzlib.minecraft.mzitem;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.player.async.EventAsyncPlayerDisplayItem;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.registry.entry.RegistryEntryListV1903;
import mz.mzlib.minecraft.registry.tag.TagKeyV1903;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Editor;
import mz.mzlib.util.Option;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.CallOnce;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.basic.Wrapper_boolean;

@WrapSameClass(ItemStack.class)
public interface MzItem extends ItemStack
{
    Identifier static$getMzId();
    
    ItemStack static$vanilla();
    
    default boolean isVanilla() // TODO V_2002
    {
        return false;
    }
    
    @CallOnce
    default void init(NbtCompound data)
    {
        for(NbtCompound customData: Item.reviseCustomData(this))
        {
            for(NbtCompound mz: customData.reviseNbtCompoundOrNew("mz"))
            {
                mz.put("id", this.getMzId().toString());
            }
        }
    }
    
    @CallOnce
    default void onDisplay(EntityPlayer player, ItemStack itemStack)
    {
        this.onDisplay$MzItem(player, itemStack);
    }
    default void onDisplay$MzItem(EntityPlayer player, ItemStack itemStack)
    {
        Identifier id = this.getMzId();
        String key = id.getNamespace() + ".item." + id.getName();
        if(Item.getCustomData(itemStack).isNone())
            Item.setCustomName(itemStack, Option.some(MinecraftI18n.resolveText(player, key)));
        // TODO lore
    }
    
    default Identifier getMzId()
    {
        return this.static$getMzId();
    }
    
    default Option<NbtCompound> getMzData()
    {
        for(NbtCompound customData: Item.getCustomData(this))
        {
            for(NbtCompound mz: customData.getNbtCompound("mz"))
            {
                return mz.getNbtCompound("data");
            }
        }
        return Option.none();
    }
    
    default Editor<NbtCompound> reviseMzData()
    {
        return Item.reviseCustomData(this)
                .then(nbt -> nbt.reviseNbtCompoundOrNew("mz"))
                .then(nbt -> nbt.reviseNbtCompoundOrNew("data"));
    }
    
    class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(NothingItemStack.class);
            this.register(RegistrarMzItem.instance);
            
            this.register(new EventListener<>(EventAsyncPlayerDisplayItem.class, Priority.VERY_HIGH, this::onAsyncPlayerDisplayItem));
            
            this.register(MzItemUsable.Module.instance);
            
            this.register(MzItemDebugStick.class);
        }
        
        public void onAsyncPlayerDisplayItem(EventAsyncPlayerDisplayItem<?> event)
        {
            event.sync(()->
            {
                for(MzItem mzItem: RegistrarMzItem.instance.toMzItem(event.getOriginal()))
                {
                    for(ItemStack itemStack: event.reviseItemStack())
                    {
                        mzItem.onDisplay(event.getPlayer(), itemStack);
                    }
                }
            });
        }
    }
    
    @WrapSameClass(ItemStack.class)
    interface NothingItemStack extends Nothing, ItemStack
    {
        default Wrapper_boolean handleVanilla()
        {
            for(MzItem mzItem: RegistrarMzItem.instance.toMzItem(this))
            {
                if(!mzItem.isVanilla())
                    return Wrapper_boolean.FACTORY.create(false);
            }
            return Nothing.notReturn();
        }
        
        @VersionRange(begin = 1903)
        @NothingInject(
                wrapperMethodName = "hasTagV1903",
                wrapperMethodParams = {TagKeyV1903.class},
                locateMethod = "",
                type = NothingInjectType.INSERT_BEFORE
        )
        default Wrapper_boolean hasTagV1903$begin()
        {
            return this.handleVanilla();
        }
        
        @VersionRange(begin = 2002)
        @NothingInject(
                wrapperMethodName = "isInV2002",
                wrapperMethodParams = { RegistryEntryListV1903.class },
                locateMethod = "",
                type = NothingInjectType.INSERT_BEFORE
        )
        default Wrapper_boolean isInV2002$begin()
        {
            return this.handleVanilla();
        }
    }
}
