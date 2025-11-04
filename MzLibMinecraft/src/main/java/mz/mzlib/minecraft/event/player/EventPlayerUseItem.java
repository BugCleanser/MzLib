package mz.mzlib.minecraft.event.player;

import mz.mzlib.event.Cancellable;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.entity.player.ActionResult;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.entity.player.Hand;
import mz.mzlib.minecraft.incomprehensible.TypedActionResultV900_2102;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.mzitem.RegistrarMzItem;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.world.AbstractWorld;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Function;

public class EventPlayerUseItem extends EventPlayer implements Cancellable
{
    ItemStack itemStack;
    ItemStack resultItemStack;
    ActionResult result = ActionResult.pass();
    
    public EventPlayerUseItem(EntityPlayer player, ItemStack itemStack)
    {
        super(player);
        this.resultItemStack = this.itemStack = RegistrarMzItem.instance.toMzItem(itemStack).map(Function.<ItemStack>identity()).unwrapOr(itemStack);
    }
    
    public ItemStack getItemStack()
    {
        return this.itemStack;
    }
    
    public ItemStack getResultItemStack()
    {
        return this.resultItemStack;
    }
    public void setResultItemStack(ItemStack value)
    {
        this.resultItemStack = value;
    }
    
    public ActionResult getResult()
    {
        return this.result;
    }
    public void setResult(ActionResult value)
    {
        this.result = value;
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
        }
    }
    
    @WrapSameClass(ItemStack.class)
    public interface NothingItemStack extends Nothing, ItemStack
    {
        default boolean handleBegin(WrapperObject.Generic<EventPlayerUseItem> event, AbstractEntityPlayer player, Hand hand)
        {
            event.setWrapped(new EventPlayerUseItem(player.as(EntityPlayer.FACTORY), this));
            event.getWrapped().call();
            if(event.getWrapped().isCancelled())
            {
                event.getWrapped().finish();
                return false;
            }
            return true;
        }
        
        static ItemStack resultV_900(EventPlayerUseItem event)
        {
            return event.getResultItemStack();
        }
        @VersionRange(end=900)
        @NothingInject(wrapperMethodName="useV_900", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default ItemStack useV_900$begin(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event, @LocalVar(2) AbstractEntityPlayer player)
        {
            if(handleBegin(event, player, Hand.mainHand()))
                return Nothing.notReturn();
            return resultV_900(event.getWrapped());
        }
        @VersionRange(end=900)
        @NothingInject(wrapperMethodName="useV_900", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class}, locateMethod="locateAllReturn", type=NothingInjectType.INSERT_BEFORE)
        default ItemStack useV_900$end(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event, @StackTop ItemStack returnValue)
        {
            event.getWrapped().setResult(ActionResult.pass());
            event.getWrapped().setResultItemStack(returnValue);
            event.getWrapped().finish();
            return resultV_900(event.getWrapped());
        }
        
        static TypedActionResultV900_2102<?> resultV900_2102(EventPlayerUseItem event)
        {
            return TypedActionResultV900_2102.Wrapper.newInstance(event.getResult(), event.getResultItemStack()).getBase();
        }
        @VersionRange(begin=900, end=2102)
        @NothingInject(wrapperMethodName="use0V900_2102", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class, Hand.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default TypedActionResultV900_2102<?> use0V900_2102$begin(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event, @LocalVar(2) AbstractEntityPlayer player, @LocalVar(3) Hand hand)
        {
            if(handleBegin(event, player, hand))
                return Nothing.notReturn();
            return resultV900_2102(event.getWrapped());
        }
        @VersionRange(begin=900, end=2102)
        @NothingInject(wrapperMethodName="use0V900_2102", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class, Hand.class}, locateMethod="locateAllReturn", type=NothingInjectType.INSERT_BEFORE)
        default TypedActionResultV900_2102<?> use0V900_2102$end(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event, @StackTop TypedActionResultV900_2102<?> returnValue)
        {
            event.getWrapped().setResult(returnValue.getActionResult());
            event.getWrapped().setResultItemStack(new TypedActionResultV900_2102.Wrapper<>(returnValue, ItemStack.FACTORY).getValue());
            event.getWrapped().finish();
            return resultV900_2102(event.getWrapped());
        }
        
        static ActionResult resultV2102(EventPlayerUseItem event)
        {
            if(event.getResult().is(ActionResult.SuccessV2102.FACTORY))
                return event.getResult().as(ActionResult.SuccessV2102.FACTORY).withNewHandStack(event.getResultItemStack());
            return event.getResult();
        }
        @VersionRange(begin=2102)
        @NothingInject(wrapperMethodName="useV2102", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class, Hand.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default ActionResult useV2102$begin(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event, @LocalVar(2) AbstractEntityPlayer player, @LocalVar(3) Hand hand)
        {
            if(handleBegin(event, player, hand))
                return Nothing.notReturn();
            return resultV2102(event.getWrapped());
        }
        @VersionRange(begin=2102)
        @NothingInject(wrapperMethodName="useV2102", wrapperMethodParams={AbstractWorld.class, AbstractEntityPlayer.class, Hand.class}, locateMethod="locateAllReturn", type=NothingInjectType.INSERT_BEFORE)
        default ActionResult useV2102$end(@CustomVar("eventUse") WrapperObject.Generic<EventPlayerUseItem> event, @StackTop ActionResult returnValue)
        {
            event.getWrapped().setResult(returnValue);
            if(returnValue.is(ActionResult.SuccessV2102.FACTORY))
                event.getWrapped().setResultItemStack(returnValue.as(ActionResult.SuccessV2102.FACTORY).getNewHandStack());
            else
                event.getWrapped().setResultItemStack(this);
            event.getWrapped().finish();
            return resultV2102(event.getWrapped());
        }
    }
}
