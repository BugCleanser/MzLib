package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftChildClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
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

    @WrapMinecraftMethod(@VersionName(name="getAction"))
    Action getAction();

    @WrapMinecraftMethod(@VersionName(name="getValue"))
    Text getValue();

    @WrapMinecraftChildClass(wrapperSupper=TextHoverEvent.class, name=@VersionName(name="Action"))
    interface Action extends WrapperObject
    {
        @WrapperCreator
        static Action create(Object wrapped)
        {
            return WrapperObject.create(Action.class, wrapped);
        }

        @WrapMinecraftFieldAccessor(@VersionName(name="SHOW_TEXT"))
        Action staticShowText();
        static Action showText()
        {
            return Action.create(null).staticShowText();
        }
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
