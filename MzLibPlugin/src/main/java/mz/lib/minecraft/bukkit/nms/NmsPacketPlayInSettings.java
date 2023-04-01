package mz.lib.minecraft.bukkit.nms;

import mz.lib.DoubleKeyList;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.bukkitlegacy.ProtocolUtil;
import mz.lib.minecraft.VersionalName;
import mz.lib.module.MzModule;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInSettings",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInSettings",minVer=17)})
public interface NmsPacketPlayInSettings extends NmsPacket
{
	DoubleKeyList<Plugin,Player,Runnable> tasks=new DoubleKeyList<>(new HashMap<>(),new HashMap<>());
	
	default void runOnReceive(Plugin plugin,Player player,Runnable task)
	{
		tasks.add(plugin,player,task);
	}
	
	@VersionalWrappedFieldAccessor({@VersionalName("locale"),@VersionalName(value="a",maxVer=16),@VersionalName(value="b",minVer=17)})
	String getLocale();
	@VersionalWrappedFieldAccessor({@VersionalName("locale"),@VersionalName(value="a",maxVer=16),@VersionalName(value="b",minVer=17)})
	NmsPacketPlayInSettings setLocale(String locale);
	
	class Module extends MzModule
	{
		public static Module instance=new Module();
		{
			this.load();
		}
		@Override
		public void onLoad()
		{
			reg(new ProtocolUtil.ReceiveListener<>(EventPriority.MONITOR,NmsPacketPlayInSettings.class,(pl,pa,c)->
			{
				tasks.remove2(pl).forEach(t->
				{
					try
					{
						t.run();
					}
					catch(Throwable e)
					{
						e.printStackTrace();
					}
				});
			}));
		}
		
		@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
		void onPluginDisable(PluginDisableEvent event)
		{
			tasks.remove1(event.getPlugin());
		}
	}
}
