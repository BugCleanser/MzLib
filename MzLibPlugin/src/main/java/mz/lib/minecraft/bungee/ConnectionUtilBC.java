package mz.lib.minecraft.bungee;

import java.io.ByteArrayOutputStream;
import mz.lib.MzConnection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class ConnectionUtilBC
{
	private ConnectionUtilBC()
	{
	}
	public static MzConnection toMzConnection(ServerInfo server,String channel)
	{
		ProxyServer.getInstance().registerChannel(channel);
		MzConnection[] mzc={null};
		Listener listener=new Listener()
		{
			@EventHandler
			public void onPluginMessage(PluginMessageEvent event)
			{
				if(event.isCancelled())
					return;
				if(Objects.equals(event.getTag(),channel))
				{
					mzc[0].input(new ByteArrayInputStream(event.getData()));
				}
			}
		};
		ProxyServer.getInstance().getPluginManager().registerListener(MzLibBC.instance,listener);
		return mzc[0]=new MzConnection(new OutputStream()
		{
			ProxiedPlayer pp;
			ByteArrayOutputStream buf=new ByteArrayOutputStream();
			@Override
			public void write(int b) throws IOException
			{
				buf.write(b);
			}
			@Override
			public void write(byte[] b) throws IOException
			{
				buf.write(b, 0, b.length);
			}
			@Override
			public void write(byte[] b,int off,int len) throws IOException
			{
				buf.write(b,off,len);
			}
			@Override
			public void flush() throws IOException
			{
				if(pp==null)
				{
					try
					{
						pp=server.getPlayers().stream().findFirst().get();
					}
					catch(NoSuchElementException e)
					{
						throw new NoPlayerInServerException();
					}
				}
				pp.sendData(channel,buf.toByteArray());
				try{buf.close();}catch(Throwable t){}
				buf=new ByteArrayOutputStream();
			}
			@Override
			public void close() throws IOException
			{
				ProxyServer.getInstance().getPluginManager().unregisterListener(listener);
				ProxyServer.getInstance().unregisterChannel(channel);
			}
		});
	}

	public static class NoPlayerInServerException extends RuntimeException
	{
		private static final long serialVersionUID=2622473646102745830L;
		
		public NoPlayerInServerException()
		{
			super("There isn't any connection in this child server.");
		}
	}
}
