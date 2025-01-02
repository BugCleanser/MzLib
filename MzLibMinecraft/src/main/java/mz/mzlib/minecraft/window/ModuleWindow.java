package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.nothing.LocalVar;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.WrapperBoolean;

import java.util.List;

public class ModuleWindow extends MzModule
{
    public static ModuleWindow instance = new ModuleWindow();
    
    public void onLoad()
    {
        this.register(NothingWindow.class);
    }
    
    @WrapSameClass(Window.class)
    public interface NothingWindow extends Window, Nothing
    {
        @WrapperCreator
        static NothingWindow create(Object wrapped)
        {
            return WrapperObject.create(NothingWindow.class, wrapped);
        }
        
        @NothingInject(wrapperMethod="placeIn", type=NothingInjectType.INSERT_BEFORE, locateMethod="")
        default WrapperBoolean placeInOverwrite(@LocalVar(1) ItemStack itemStack, @LocalVar(2) int begin, @LocalVar(3) int end, @LocalVar(4) boolean inverted)
        {
            return WrapperBoolean.create(this.placeInOrCheck(itemStack, begin, end, inverted, false));
        }
        
        default boolean placeInOrCheck(ItemStack itemStack, int begin, int end, boolean inverted, boolean doCheck)
        {
            if(doCheck)
                itemStack = itemStack.copy();
            
            boolean result = false;
            int k = begin;
            if(inverted)
                k = end-1;
            
            List<WindowSlot> slots = this.getSlots();
            WindowSlot slot;
            ItemStack itemstack1;
            int l;
            if(itemStack.isStackable())
            {
                while(!itemStack.isEmpty())
                {
                    if(inverted)
                    {
                        if(k<begin)
                            break;
                    }
                    else if(k>=end)
                        break;
                    
                    slot = slots.get(k);
                    itemstack1 = slot.getItemStack();
                    if(doCheck)
                        itemstack1 = itemstack1.copy();
                    
                    if(!itemstack1.isEmpty() && slot.canPlace(itemStack) && ItemStack.isStackable(itemStack, itemstack1))
                    {
                        l = itemstack1.getCount()+itemStack.getCount();
                        int i1 = slot.getMaxStackCount(itemstack1);
                        if(l<=i1)
                        {
                            itemStack.setCount(0);
                            itemstack1.setCount(l);
                            if(!doCheck)
                                slot.markDirty();
                            
                            result = true;
                        }
                        else if(itemstack1.getCount()<i1)
                        {
                            itemStack.shrink(i1-itemstack1.getCount());
                            itemstack1.setCount(i1);
                            if(!doCheck)
                                slot.markDirty();
                            
                            result = true;
                        }
                    }
                    
                    if(inverted)
                        --k;
                    else
                        ++k;
                }
            }
            
            if(!itemStack.isEmpty())
            {
                if(inverted)
                    k = end-1;
                else
                    k = begin;
                
                while(true)
                {
                    if(inverted)
                    {
                        if(k<begin)
                            break;
                    }
                    else if(k>=end)
                        break;
                    
                    slot = slots.get(k);
                    itemstack1 = slot.getItemStack();
                    if(doCheck)
                        itemstack1 = itemstack1.copy();
                    
                    if(itemstack1.isEmpty() && slot.canPlace(itemStack))
                    {
                        l = slot.getMaxStackCount(itemStack);
                        if(doCheck)
                            itemStack.shrink(Math.min(itemStack.getCount(), l));
                        else
                        {
                            slot.setItemStackByPlayer(itemStack.split(Math.min(itemStack.getCount(), l)));
                            slot.markDirty();
                        }
                        
                        result = true;
                        break;
                    }
                    
                    if(inverted)
                        --k;
                    else
                        ++k;
                }
            }
            
            return result;
        }
    }
}
