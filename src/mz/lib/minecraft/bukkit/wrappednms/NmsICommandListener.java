package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappedobc.ObcBlockCommandSender;
import mz.lib.minecraft.bukkit.wrappedobc.ObcEntity;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;

import java.util.UUID;

@WrappedBukkitClass({@VersionName(value="nms.ICommandListener",maxVer=17),@VersionName(value="net.minecraft.commands.ICommandListener",minVer=17)})
public interface NmsICommandListener extends WrappedBukkitObject
{
	static NmsICommandListener fromBukkit(CommandSender sender)
	{
		if(sender instanceof BlockCommandSender)
		{
			return WrappedObject.wrap(ObcBlockCommandSender.class,sender).getTileEntity();
		}
		else if(sender instanceof ConsoleCommandSender)
		{
			return NmsMinecraftServer.getServer();
		}
		else if(sender instanceof Entity)
		{
			return WrappedObject.wrap(ObcEntity.class,sender).getHandle();
		}
		else if(sender instanceof GeneraldutyCommandSender)
		{
			return ((GeneraldutyCommandSender) sender).getNms();
		}
		else
		{
			throw new IllegalArgumentException("unsupported type "+sender.getClass().getName()+" of CommandSender");
		}
	}
	@WrappedBukkitMethod(@VersionName(maxVer=16,value="sendMessage"))
	void sendMessageV_16(NmsIChatBaseComponent msg);
	@WrappedBukkitMethod({@VersionName(minVer=16,maxVer=19,value="sendMessage"),@VersionName(value="a",minVer=18,maxVer=19)})
	void sendMessageV16_19(NmsIChatBaseComponent msg,UUID sender);
	@WrappedBukkitMethod(@VersionName(value="a",minVer=19))
	void sendMessageV19(NmsIChatBaseComponent msg);
	default void sendMessage(NmsIChatBaseComponent msg)
	{
		if(BukkitWrapper.version<16)
			sendMessageV_16(msg);
		else if(BukkitWrapper.version<19)
			sendMessageV16_19(msg,new UUID(0L,0L));
		else
			sendMessageV19(msg);
	}
	interface GeneraldutyCommandSender extends CommandSender
	{
		NmsICommandListener getNms();
	}
}
