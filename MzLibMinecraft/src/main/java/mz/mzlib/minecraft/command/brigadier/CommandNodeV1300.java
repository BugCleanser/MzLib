package mz.mzlib.minecraft.command.brigadier;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Map;

@VersionRange(begin=1300)
@WrapClassForName("com.mojang.brigadier.tree.CommandNode")
public interface CommandNodeV1300 extends WrapperObject
{
    @WrapperCreator
    static CommandNodeV1300 create(Object wrapped)
    {
        return WrapperObject.create(CommandNodeV1300.class, wrapped);
    }
    
    @WrapFieldAccessor("children")
    Map<String, ?> getChildren();
    @WrapFieldAccessor("literals")
    Map<String, ?> getLiterals();
    @WrapFieldAccessor("arguments")
    Map<String, ?> getArguments();
    
    default void removeChild(String childName)
    {
        this.getChildren().remove(childName);
        this.getLiterals().remove(childName);
        this.getArguments().remove(childName);
    }
}
