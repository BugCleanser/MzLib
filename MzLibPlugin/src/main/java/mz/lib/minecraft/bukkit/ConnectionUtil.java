package mz.lib.minecraft.bukkit;

import com.sun.tools.javac.util.ByteBuffer;
import mz.lib.MzConnection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;

public final class ConnectionUtil
{
	private ConnectionUtil()
	{
	}
	public static MzConnection fromBungee(String channel)
	{
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(MzLib.instance,channel);
		MzConnection[] mzc={null};
		Bukkit.getServer().getMessenger().registerIncomingPluginChannel(MzLib.instance,channel,(c,p,data)->
		{
			mzc[0].input(new ByteArrayInputStream(data));
		});
		return mzc[0]=new MzConnection(new OutputStream()
		{
			ByteBuffer buf=new ByteBuffer();
			Player p;
			@Override
			public void write(int b) throws IOException
			{
				buf.appendByte(b);
			}
			@Override
			public void write(byte[] b) throws IOException
			{
				buf.appendBytes(b);
			}
			@Override
			public void write(byte[] b,int off,int len) throws IOException
			{
				buf.appendBytes(b,off,len);
			}
			@Override
			public void flush() throws IOException
			{
				if(p==null)
				{
					try
					{
						p=Bukkit.getOnlinePlayers().stream().findFirst().get();
					}
					catch(NoSuchElementException e)
					{
						throw new NoPlayerInServerException();
					}
				}
				p.sendPluginMessage(MzLib.instance,channel,buf.elems);
				buf=new ByteBuffer();
			}
			@Override
			public void close() throws IOException
			{
				Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(MzLib.instance,channel);
				Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(MzLib.instance,channel);
			}
		});
	}

	public static class NoPlayerInServerException extends RuntimeException
	{
		private static final long serialVersionUID=2622473646102745830L;
		
		public NoPlayerInServerException()
		{
			super("There isn't any connection to bungee.");
		}
	}
}
