package mz.mzlib.minecraft.event.player;

import mz.mzlib.event.Cancellable;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.entity.player.ActionResult;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.entity.player.EnumHandV900;
import mz.mzlib.minecraft.incomprehensible.TypedActionResultV900_2102;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.world.AbstractWorld;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;

public class EventPlayerUseItem extends EventPlayer implements Cancellable
{
    public EventPlayerUseItem(EntityPlayer player)
    {
        super(player);
    }
    
    @Override
    public void call()
    {
        super.call();
    }
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventPlayerUseItem.class);
            this.register(NothingItemStack.class);
            
            // TODO test
            this.register(new EventListener<>(EventPlayerUseItem.class, event->
            {
                event.getPlayer().sendMessage(Text.literal("Test"));
            }));
        }
    }
    
    @WrapSameClass(ItemStack.class)
    public interface NothingItemStack extends Nothing, ItemStack
    {
        @VersionRange(end=900)
        @NothingInject(wrapperMethodName="useV_900", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default ItemStack useBeginV_900(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event, @LocalVar(1) AbstractWorld world, @LocalVar(2) AbstractEntityPlayer player)
        {
            // TODO
            return Nothing.notReturn();
        }
        @VersionRange(end=900)
        @NothingInject(wrapperMethodName="useV_900", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class}, locateMethod="locateAllReturn", type=NothingInjectType.INSERT_BEFORE)
        default ItemStack useEndV_900(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event)
        {
            event.getWrapped().finish();
            return Nothing.notReturn();
        }
        
        @VersionRange(begin=900, end=2102)
        @NothingInject(wrapperMethodName="useV900_2102", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class, EnumHandV900.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default TypedActionResultV900_2102<ItemStack> useBeginV900_2102(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event, @LocalVar(1) AbstractWorld world, @LocalVar(2) AbstractEntityPlayer player, @LocalVar(3) EnumHandV900 hand)
        {
            // TODO
            return Nothing.notReturn();
        }
        @VersionRange(begin=900, end=2102)
        @NothingInject(wrapperMethodName="useV900_2102", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class, EnumHandV900.class}, locateMethod="locateAllReturn", type=NothingInjectType.INSERT_BEFORE)
        default TypedActionResultV900_2102<ItemStack> useEndV900_2102(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event)
        {
            event.getWrapped().finish();
            return Nothing.notReturn();
        }
        
        
        @VersionRange(begin=2102)
        @NothingInject(wrapperMethodName="useV2102", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class, EnumHandV900.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default ActionResult useBeginV2102(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event, @LocalVar(1) AbstractWorld world, @LocalVar(2) AbstractEntityPlayer player, @LocalVar(3) EnumHandV900 hand)
        {
            event.setWrapped(new EventPlayerUseItem(player.as(EntityPlayer.FACTORY)));
            event.getWrapped().call();
            if(event.getWrapped().isCancelled())
            {
                event.getWrapped().finish();
                return ActionResult.failV900(); // TODO
            }
            return Nothing.notReturn();
        }
        @VersionRange(begin=2102)
        @NothingInject(wrapperMethodName="useV2102", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class, EnumHandV900.class}, locateMethod="locateAllReturn", type=NothingInjectType.INSERT_BEFORE)
        default ActionResult useEndV2102(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event)
        {
            event.getWrapped().finish();
            return Nothing.notReturn();
        }
    }
}
