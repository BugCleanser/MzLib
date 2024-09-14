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
                @VersionName(name="net.minecraft.text.ClickEvent", end=1400),
                @VersionName(name="net.minecraft.network.chat.ClickEvent", begin=1400, end=1403),
                @VersionName(name="net.minecraft.text.ClickEvent", begin=1403)
        })
public interface TextClickEvent extends WrapperObject
{
    @WrapperCreator
    static TextClickEvent create(Object wrapped)
    {
        return WrapperObject.create(TextClickEvent.class, wrapped);
    }

    @WrapConstructor
    TextClickEvent staticNewInstance(TextClickEvent.Action action, String value);
    static TextClickEvent newInstance(TextClickEvent.Action action, String value)
    {
        return create(null).staticNewInstance(action, value);
    }

    @WrapMinecraftMethod(@VersionName(name="getAction"))
    Action getAction();

    @WrapMinecraftMethod(@VersionName(name="getValue"))
    String getValue();

    @WrapMinecraftChildClass(wrapperSupper=TextClickEvent.class, name=@VersionName(name="Action"))
    interface Action extends WrapperObject
    {
        @WrapperCreator
        static Action create(Object wrapped)
        {
            return WrapperObject.create(Action.class, wrapped);
        }

        @WrapMinecraftFieldAccessor(@VersionName(name="OPEN_URL"))
        Action staticOpenUrl();
        static Action openUrl()
        {
            return Action.create(null).staticOpenUrl();
        }

        @WrapMinecraftFieldAccessor(@VersionName(name="OPEN_FILE"))
        Action staticOpenFile();
        static Action openFile()
        {
            return Action.create(null).staticOpenFile();
        }

        @WrapMinecraftFieldAccessor(@VersionName(name = "RUN_COMMAND"))
        Action staticRunCommand();
        static Action runCommand()
        {
            return TextClickEvent.Action.create(null).staticRunCommand();
        }

        @WrapMinecraftFieldAccessor(@VersionName(name = "SUGGEST_COMMAND"))
        Action staticSuggestCommand();
        static Action suggestCommand()
        {
            return Action.create(null).staticSuggestCommand();
        }

        @WrapMinecraftFieldAccessor(@VersionName(name = "CHANGE_PAGE"))
        Action staticChangePage();
        static Action changePage()
        {
            return Action.create(null).staticChangePage();
        }

        @WrapMinecraftFieldAccessor(@VersionName(name="COPY_TO_CLIPBOARD", begin=1500))
        Action staticCopyToClipboardV1500();
        static Action copyToClipboardV1500()
        {
            return Action.create(null).staticCopyToClipboardV1500();
        }
    }
}
