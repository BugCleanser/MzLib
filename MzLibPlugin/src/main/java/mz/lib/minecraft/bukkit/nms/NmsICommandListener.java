package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.obc.ObcBlockCommandSender;
import mz.lib.minecraft.bukkit.obc.ObcEntity;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.*;
import mz.mzlib.wrapper.*;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;

import java.util.UUID;

@VersionalWrappedClass({@VersionalName(value="nms.ICommandListener",maxVer=17),@VersionalName(value="net.minecraft.commands.ICommandListener",minVer=17)})
public interface NmsICommandListener extends VersionalWrappedObject
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
	@VersionalWrappedMethod(@VersionalName(maxVer=16,value="sendMessage"))
	void sendMessageV_16(NmsIChatBaseComponent msg);
	@VersionalWrappedMethod({@VersionalName(minVer=16,maxVer=19,value="sendMessage"),@VersionalName(value="a",minVer=18,maxVer=19)})
	void sendMessageV16_19(NmsIChatBaseComponent msg,UUID sender);
	@VersionalWrappedMethod(@VersionalName(value="a",minVer=19))
	void sendMessageV19(NmsIChatBaseComponent msg);
	default void sendMessage(NmsIChatBaseComponent msg)
	{
		if(Server.instance.version<16)
			sendMessageV_16(msg);
		else if(Server.instance.version<19)
			sendMessageV16_19(msg,new UUID(0L,0L));
		else
			sendMessageV19(msg);
	}
	interface GeneraldutyCommandSender extends CommandSender
	{
		NmsICommandListener getNms();
	}
}
