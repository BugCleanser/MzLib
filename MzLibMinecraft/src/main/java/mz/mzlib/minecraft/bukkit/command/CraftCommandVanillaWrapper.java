package mz.mzlib.minecraft.bukkit.command;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.CommandSender;

@BukkitOnly
@WrapCraftbukkitClass(@VersionName(name="OBC.command.VanillaCommandWrapper"))
public interface CraftCommandVanillaWrapper extends WrapperObject
{
    @WrapperCreator
    static CraftCommandVanillaWrapper create(Object wrapped)
    {
        return WrapperObject.create(CraftCommandVanillaWrapper.class, wrapped);
    }
    
    CraftCommandVanillaWrapper unsafe = RuntimeUtil.sneakilyRun(()->create(Root.getUnsafe().allocateInstance(create(null).staticGetWrappedClass())));
    
    static CommandSource toCommandSource(CommandSender object)
    {
        return create(null).staticToCommandSource(object);
    }
    
    CommandSource staticToCommandSource(CommandSender object);
    
    @VersionRange(end=1300)
    @WrapMethod("getListener")
    CommandSource toCommandSourceV_1300(CommandSender object);
    
    @SpecificImpl("staticToCommandSource")
    @VersionRange(end=1300)
    default CommandSource staticToCommandSourceV_1300(CommandSender object)
    {
        return unsafe.toCommandSourceV_1300(object);
    }
    
    @SpecificImpl("staticToCommandSource")
    @VersionRange(begin=1300)
    @WrapMethod("getListener")
    CommandSource staticToCommandSourceV1300(CommandSender object);
}
