package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cWindowOpen;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.nothing.LocalVar;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.WrapperBoolean;
import mz.mzlib.util.wrapper.basic.WrapperString;
import mz.mzlib.util.wrapper.basic.Wrapper_int;

import java.util.List;

public class ModuleWindow extends MzModule
{
    public static ModuleWindow instance = new ModuleWindow();

    public void onLoad()
    {
        this.register(NothingPacketS2cWindowOpen.class);
        this.register(NothingWindow.class);
    }

    @WrapSameClass(PacketS2cWindowOpen.class)
    public interface NothingPacketS2cWindowOpen extends PacketS2cWindowOpen, Nothing
    {
        @VersionRange(end = 1400)
        @NothingInject(wrapperMethodName = "<init>", wrapperMethodParams = {
            int.class,
            String.class,
            Text.class,
            int.class
        }, locateMethod = "", type = NothingInjectType.INSERT_BEFORE)
        static void initBeforeV_1400(@LocalVar(2) WrapperString typeId, @LocalVar(4) Wrapper_int size)
        {
            int i = typeId.getWrapped().indexOf('*');
            if(i == -1)
                return;
            size.setWrapped(Integer.parseInt(typeId.getWrapped().substring(i + 1)));
            typeId.setWrapped(typeId.getWrapped().substring(0, i));
        }
    }

    @WrapSameClass(Window.class)
    public interface NothingWindow extends Window, Nothing
    {
        WrapperFactory<NothingWindow> FACTORY = WrapperFactory.of(NothingWindow.class);
        @Deprecated
        @WrapperCreator
        static NothingWindow create(Object wrapped)
        {
            return WrapperObject.create(NothingWindow.class, wrapped);
        }

        @NothingInject(wrapperMethodName = "placeIn", wrapperMethodParams = {
            ItemStack.class,
            int.class,
            int.class,
            boolean.class
        }, type = NothingInjectType.INSERT_BEFORE, locateMethod = "")
        default WrapperBoolean placeInOverwrite(
            @LocalVar(1) ItemStack itemStack,
            @LocalVar(2) int begin,
            @LocalVar(3) int end,
            @LocalVar(4) boolean inverted)
        {
            return WrapperBoolean.FACTORY.create(this.placeInOrCheck(itemStack, begin, end, inverted, false));
        }

        default boolean placeInOrCheck(ItemStack itemStack, int begin, int end, boolean inverted, boolean doCheck)
        {
            if(!itemStack.isPresent())
                throw new NullPointerException("ItemStack");
            if(itemStack.isEmpty())
                return false;
            if(doCheck)
                itemStack = itemStack.copy();

            boolean result = false;
            int k = begin;
            if(inverted)
                k = end - 1;

            List<WindowSlot> slots = this.getSlots();
            WindowSlot slot;
            ItemStack is;
            int l;
            if(itemStack.isStackable())
            {
                while(!itemStack.isEmpty())
                {
                    if(inverted)
                    {
                        if(k < begin)
                            break;
                    }
                    else if(k >= end)
                        break;

                    slot = slots.get(k);
                    is = slot.getItemStack();
                    if(doCheck)
                        is = is.copy();

                    if(!is.isEmpty() && slot.canPlace(itemStack) && ItemStack.isStackable(itemStack, is))
                    {
                        l = is.getCount() + itemStack.getCount();
                        int i1 = slot.getMaxStackCount(is);
                        if(l <= i1)
                        {
                            itemStack.setCount(0);
                            is.setCount(l);
                            if(!doCheck)
                                slot.markDirty();

                            result = true;
                        }
                        else if(is.getCount() < i1)
                        {
                            itemStack.shrink(i1 - is.getCount());
                            is.setCount(i1);
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
                    k = end - 1;
                else
                    k = begin;

                while(true)
                {
                    if(inverted)
                    {
                        if(k < begin)
                            break;
                    }
                    else if(k >= end)
                        break;

                    slot = slots.get(k);
                    is = slot.getItemStack();
                    if(doCheck)
                        is = is.copy();

                    if(is.isEmpty() && slot.canPlace(itemStack))
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
