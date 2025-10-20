package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

import java.net.URI;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.ClickEvent"))
public interface TextClickEvent extends WrapperObject
{
    WrapperFactory<TextClickEvent> FACTORY = WrapperFactory.of(TextClickEvent.class);
    @Deprecated
    @WrapperCreator
    static TextClickEvent create(Object wrapped)
    {
        return WrapperObject.create(TextClickEvent.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getAction"))
    Action getAction();
    
    static TextClickEvent runCommand(String command)
    {
        return FACTORY.getStatic().staticRunCommand(command);
    }
    TextClickEvent staticRunCommand(String command);
    @VersionRange(end=2105)
    @SpecificImpl("staticRunCommand")
    default TextClickEvent staticRunCommandV_2105(String command)
    {
        return newInstanceV_2105(Action.runCommand(), command);
    }
    @VersionRange(begin=2105)
    @SpecificImpl("staticRunCommand")
    default TextClickEvent staticRunCommandV2105(String command)
    {
        return RunCommandV2105.newInstance(command);
    }
    
    static TextClickEvent changePage(int page)
    {
        throw new UnsupportedOperationException(); // TODO
    }
    static TextClickEvent copyToClipboard(String value)
    {
        throw new UnsupportedOperationException(); // TODO
    }
    static TextClickEvent openFile(String path)
    {
        throw new UnsupportedOperationException(); // TODO
    }
    static TextClickEvent openUrl(URI uri)
    {
        throw new UnsupportedOperationException(); // TODO
    }
    static TextClickEvent suggestCommand(String command)
    {
        throw new UnsupportedOperationException(); // TODO
    }
    
    static TextClickEvent newInstanceV_2105(TextClickEvent.Action action, String value)
    {
        return FACTORY.getStatic().staticNewInstanceV_2105(action, value);
    }
    @VersionRange(end=2105)
    @WrapConstructor
    TextClickEvent staticNewInstanceV_2105(TextClickEvent.Action action, String value);

    @VersionRange(end=2105)
    @WrapMinecraftMethod(@VersionName(name="getValue"))
    String getValueV_2105();

    @WrapMinecraftInnerClass(outer=TextClickEvent.class, name=@VersionName(name="Action"))
    interface Action extends WrapperObject
    {
        WrapperFactory<Action> FACTORY = WrapperFactory.of(Action.class);
        @Deprecated
        @WrapperCreator
        static Action create(Object wrapped)
        {
            return WrapperObject.create(Action.class, wrapped);
        }

        @WrapMinecraftFieldAccessor({@VersionName(name="OPEN_URL", end=1400), @VersionName(name="field_11749", begin=1400)})
        Action staticOpenUrl();
        static Action openUrl()
        {
            return FACTORY.getStatic().staticOpenUrl();
        }

        @WrapMinecraftFieldAccessor({@VersionName(name="OPEN_FILE", end=1400), @VersionName(name="field_11746", begin=1400)})
        Action staticOpenFile();
        static Action openFile()
        {
            return FACTORY.getStatic().staticOpenFile();
        }

        @WrapMinecraftFieldAccessor({@VersionName(name="RUN_COMMAND", end=1400), @VersionName(name="field_11750", begin=1400)})
        Action staticRunCommand();
        static Action runCommand()
        {
            return FACTORY.getStatic().staticRunCommand();
        }

        @WrapMinecraftFieldAccessor({@VersionName(name="SUGGEST_COMMAND", end=1400), @VersionName(name="field_11745", begin=1400)})
        Action staticSuggestCommand();
        static Action suggestCommand()
        {
            return FACTORY.getStatic().staticSuggestCommand();
        }

        @WrapMinecraftFieldAccessor({@VersionName(name="CHANGE_PAGE", end=1400), @VersionName(name="field_11748", begin=1400)})
        Action staticChangePage();
        static Action changePage()
        {
            return FACTORY.getStatic().staticChangePage();
        }

        @WrapMinecraftFieldAccessor({@VersionName(name="field_21462", begin=1500)})
        Action staticCopyToClipboardV1500();
        static Action copyToClipboardV1500()
        {
            return FACTORY.getStatic().staticCopyToClipboardV1500();
        }
    }
    
    @VersionRange(begin=2105)
    @WrapMinecraftInnerClass(outer=TextClickEvent.class, name=@VersionName(name="RunCommand"))
    interface RunCommandV2105 extends TextClickEvent
    {
        WrapperFactory<RunCommandV2105> FACTORY = WrapperFactory.of(RunCommandV2105.class);
        
        static RunCommandV2105 newInstance(String command)
        {
            return FACTORY.getStatic().staticNewInstance(command);
        }
        @WrapConstructor
        RunCommandV2105 staticNewInstance(String command);
    }
}
