package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtByte;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtShort;
import mz.mzlib.minecraft.nbt.NbtString;
import mz.mzlib.minecraft.wrapper.WrapMinecraftChildClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name="net.minecraft.text.HoverEvent", end=1400) //TODO
        })
public interface TextHoverEvent extends WrapperObject
{
    @WrapperCreator
    static TextHoverEvent create(Object wrapped)
    {
        return WrapperObject.create(TextHoverEvent.class, wrapped);
    }

    @WrapConstructor
    TextHoverEvent staticNewInstance(Action action, Text value);
    static TextHoverEvent newInstance(Action action, Text value)
    {
        return create(null).staticNewInstance(action, value);
    }
    static TextHoverEvent showItemV_1300(ItemStack is)
    {
        NbtCompound nbt=NbtCompound.newInstance();
        nbt.put("id", NbtString.newInstance(is.getId().toString()));
        nbt.put("Count", NbtByte.newInstance((byte) is.getCount()));
        nbt.put("damage", NbtShort.newInstance((short) is.getDamageV_1300()));
        nbt.put("tag", is.getTagV_2005());

        return newInstance(Action.showItem(), Text.literal(nbt.toString()));
    }

    @WrapMinecraftChildClass(wrapperSupper=TextHoverEvent.class, name=@VersionName(name="Action"))
    interface Action extends WrapperObject
    {
        @WrapperCreator
        static Action create(Object wrapped)
        {
            return WrapperObject.create(Action.class, wrapped);
        }

        static Action showText()
        {
            return Action.create(null).staticShowText();
        }
        @WrapMinecraftFieldAccessor(@VersionName(name="SHOW_TEXT"))
        Action staticShowText();
        @WrapMinecraftFieldAccessor(@VersionName(name="SHOW_ITEM"))
        Action staticShowItem();
        static Action showItem()
        {
            return Action.create(null).staticShowItem();
        }
        @WrapMinecraftFieldAccessor(@VersionName(name="SHOW_ENTITY"))
        Action staticShowEntity();
        static Action showEntity()
        {
            return Action.create(null).staticShowEntity();
        }
    }
}
