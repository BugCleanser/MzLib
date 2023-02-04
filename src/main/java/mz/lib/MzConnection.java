package mz.lib;

import io.github.karlatemp.unsafeaccessor.Root;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.*;
import java.lang.ref.WeakReference;
import java.lang.reflect.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class MzConnection
{
	public @Deprecated
	OutputStream out;
	public @Deprecated
	Consumer<Object> reader;
	public @Deprecated
	Map<UUID,WeakReference<Object>> proxyObjects=new ConcurrentHashMap<>();
	public @Deprecated
	int threshold=10;
	public @Deprecated
	Map<UUID,Consumer<Object>> returnTasks=new ConcurrentHashMap<>();
	public MzConnection(SocketChannel channel,Consumer<Object> reader)
	{
		channel.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>()
		{
			protected void channelRead0(ChannelHandlerContext context,ByteBuf byteBuf) throws Exception
			{
				input(new InputStream()
				{
					@Override
					public int read() throws IOException
					{
						if(byteBuf.isReadable())
							return byteBuf.readByte();
						return -1;
					}
					@Override
					public int read(byte[] b) throws IOException
					{
						int length=byteBuf.readableBytes();
						byteBuf.readBytes(b,0,length);
						return length;
					}
					@Override
					public int read(byte[] b,int off,int len) throws IOException
					{
						int length=byteBuf.readableBytes();
						if(len<length)
							length=len;
						byteBuf.readBytes(b,off,length);
						return length;
					}
				});
			}
		});
		out=new OutputStream()
		{
			public void write(int b) throws IOException
			{
				channel.write(Unpooled.copiedBuffer(new byte[]{(byte) b}));
			}
			public void write(byte[] b) throws IOException
			{
				channel.write(Unpooled.copiedBuffer(b));
			}
			public void write(byte[] b,int off,int len) throws IOException
			{
				channel.write(Unpooled.copiedBuffer(b,off,len));
			}
			public void flush() throws IOException
			{
				channel.flush();
			}
			public void close() throws IOException
			{
				try
				{
					channel.closeFuture().sync();
				}
				catch(Throwable e)
				{
					throw TypeUtil.throwException(e);
				}
			}
		};
		this.reader=reader;
	}
	public MzConnection(String host,int port,Consumer<Object> reader) throws InterruptedException
	{
		EpollEventLoopGroup group=new EpollEventLoopGroup();
		Channel channel=new Bootstrap().group(group).channel(NioSocketChannel.class).remoteAddress(host,port).handler(new SimpleChannelInboundHandler<ByteBuf>()
		{
			protected void channelRead0(ChannelHandlerContext context,ByteBuf byteBuf) throws Exception
			{
				input(new InputStream()
				{
					@Override
					public int read() throws IOException
					{
						if(byteBuf.isReadable())
							return byteBuf.readByte();
						return -1;
					}
					@Override
					public int read(byte[] b) throws IOException
					{
						int length=byteBuf.readableBytes();
						byteBuf.readBytes(b,0,length);
						return length;
					}
					@Override
					public int read(byte[] b,int off,int len) throws IOException
					{
						int length=byteBuf.readableBytes();
						if(len<length)
							length=len;
						byteBuf.readBytes(b,off,length);
						return length;
					}
				});
			}
		}).connect().sync().channel();
		out=new OutputStream()
		{
			public void write(int b) throws IOException
			{
				channel.write(Unpooled.copiedBuffer(new byte[]{(byte) b}));
			}
			public void write(byte[] b) throws IOException
			{
				channel.write(Unpooled.copiedBuffer(b));
			}
			public void write(byte[] b,int off,int len) throws IOException
			{
				channel.write(Unpooled.copiedBuffer(b,off,len));
			}
			public void flush() throws IOException
			{
				channel.flush();
			}
			public void close() throws IOException
			{
				try
				{
					channel.closeFuture().sync();
				}
				catch(Throwable e)
				{
					throw TypeUtil.throwException(e);
				}
				finally
				{
					group.shutdownGracefully();
				}
			}
		};
		this.reader=reader;
	}
	public MzConnection(OutputStream out)
	{
		this.out=out;
	}
	public static Object readObject(DataInput input)
	{
		try
		{
			String className=readString(input);
			if(className.length()==0)
				return null;
			Class<?> clazz=Class.forName(className);
			if(clazz==int.class||clazz==Integer.class)
				return input.readInt();
			else if(clazz==float.class||clazz==Float.class)
				return input.readFloat();
			else if(clazz==double.class||clazz==Double.class)
				return input.readDouble();
			else if(clazz==long.class||clazz==Long.class)
				return input.readLong();
			else if(clazz==short.class||clazz==Short.class)
				return input.readShort();
			else if(clazz==byte.class||clazz==Byte.class)
				return input.readByte();
			else if(clazz==boolean.class||clazz==Boolean.class)
				return input.readBoolean();
			else if(clazz==char.class||clazz==Character.class)
				return input.readChar();
			else if(clazz==String.class)
				return readString(input);
			else if(clazz==Class.class)
				return Class.forName(readString(input));
			else if(clazz.isArray())
			{
				clazz=clazz.getComponentType();
				if(clazz.isPrimitive())
				{
					if(clazz==byte.class)
					{
						byte[] obj=new byte[input.readInt()];
						input.readFully(obj);
						return obj;
					}
					else if(clazz==short.class)
					{
						short[] obj=new short[input.readInt()];
						for(int i=0;i<obj.length;i++)
							obj[i]=input.readShort();
						return obj;
					}
					else if(clazz==int.class)
					{
						int[] obj=new int[input.readInt()];
						for(int i=0;i<obj.length;i++)
							obj[i]=input.readInt();
						return obj;
					}
					else if(clazz==long.class)
					{
						long[] obj=new long[input.readInt()];
						for(int i=0;i<obj.length;i++)
							obj[i]=input.readLong();
						return obj;
					}
					else if(clazz==char.class)
					{
						char[] obj=new char[input.readInt()];
						for(int i=0;i<obj.length;i++)
							obj[i]=input.readChar();
						return obj;
					}
					else if(clazz==boolean.class)
					{
						boolean[] obj=new boolean[input.readInt()];
						for(int i=0;i<obj.length;i++)
							obj[i]=input.readBoolean();
						return obj;
					}
					else if(clazz==float.class)
					{
						float[] obj=new float[input.readInt()];
						for(int i=0;i<obj.length;i++)
							obj[i]=input.readFloat();
						return obj;
					}
					else if(clazz==double.class)
					{
						double[] obj=new double[input.readInt()];
						for(int i=0;i<obj.length;i++)
							obj[i]=input.readDouble();
						return obj;
					}
					else
						throw new IllegalArgumentException();
				}
				else
				{
					Object[] obj=TypeUtil.cast(Array.newInstance(clazz,input.readInt()));
					for(int i=0;i<obj.length;i++)
						obj[i]=readObject(input);
					return obj;
				}
			}
			else
			{
				Object obj=Root.getUnsafe().allocateInstance(clazz);
				for(Field f: clazz.getDeclaredFields())
				{
					Root.setAccessible(f,true);
					f.set(obj,readObject(input));
				}
				return obj;
			}
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static void writeObject(DataOutput output,Object obj)
	{
		try
		{
			Class<? extends Object> clazz=obj.getClass();
			writeString(output,clazz.getName());
			if(clazz==int.class||clazz==Integer.class)
				output.writeInt((Integer) obj);
			else if(clazz==float.class||clazz==Float.class)
				output.writeFloat((Float) obj);
			else if(clazz==double.class||clazz==Double.class)
				output.writeDouble((Double) obj);
			else if(clazz==long.class||clazz==Long.class)
				output.writeLong((Long) obj);
			else if(clazz==short.class||clazz==Short.class)
				output.writeShort((Short) obj);
			else if(clazz==byte.class||clazz==Byte.class)
				output.writeByte((Byte) obj);
			else if(clazz==boolean.class||clazz==Boolean.class)
				output.writeBoolean((Boolean) obj);
			else if(clazz==char.class||clazz==Character.class)
				output.writeChar((Character) obj);
			else if(clazz==String.class)
				writeString(output,(String) obj);
			else if(clazz==Class.class)
				writeString(output,((Class<?>) obj).getName());
			else if(clazz.isArray())
			{
				clazz=clazz.getComponentType();
				if(clazz.isPrimitive())
				{
					if(clazz==int.class)
					{
						int[] o=TypeUtil.cast(obj);
						output.writeInt(o.length);
						for(int i=0;i<o.length;i++)
						{
							output.writeInt(o[i]);
						}
					}
					else if(clazz==byte.class)
					{
						byte[] o=TypeUtil.cast(obj);
						output.writeInt(o.length);
						for(int i=0;i<o.length;i++)
						{
							output.writeByte(o[i]);
						}
					}
					else if(clazz==short.class)
					{
						short[] o=TypeUtil.cast(obj);
						output.writeInt(o.length);
						for(int i=0;i<o.length;i++)
						{
							output.writeShort(o[i]);
						}
					}
					else if(clazz==long.class)
					{
						long[] o=TypeUtil.cast(obj);
						output.writeInt(o.length);
						for(int i=0;i<o.length;i++)
						{
							output.writeLong(o[i]);
						}
					}
					else if(clazz==float.class)
					{
						float[] o=TypeUtil.cast(obj);
						output.writeInt(o.length);
						for(int i=0;i<o.length;i++)
						{
							output.writeFloat(o[i]);
						}
					}
					else if(clazz==double.class)
					{
						double[] o=TypeUtil.cast(obj);
						output.writeInt(o.length);
						for(int i=0;i<o.length;i++)
						{
							output.writeDouble(o[i]);
						}
					}
					else if(clazz==char.class)
					{
						char[] o=TypeUtil.cast(obj);
						output.writeInt(o.length);
						for(int i=0;i<o.length;i++)
						{
							output.writeChar(o[i]);
						}
					}
					else if(clazz==boolean.class)
					{
						boolean[] o=TypeUtil.cast(obj);
						output.writeInt(o.length);
						for(int i=0;i<o.length;i++)
						{
							output.writeBoolean(o[i]);
						}
					}
				}
				else
				{
					Object[] o=TypeUtil.cast(obj);
					output.writeInt(o.length);
					for(int i=0;i<o.length;i++)
					{
						writeObject(output,o[i]);
					}
				}
			}
			else
			{
				for(Field f: clazz.getDeclaredFields())
				{
					Root.setAccessible(f,true);
					writeObject(output,f.get(obj));
				}
			}
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static String readString(DataInput input)
	{
		try
		{
			byte[] bs=new byte[input.readInt()];
			input.readFully(bs);
			return new String(bs,StringUtil.UTF8);
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static void writeString(DataOutput output,String str)
	{
		try
		{
			output.writeInt(str.length());
			output.write(str.getBytes(StringUtil.UTF8));
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public void input(InputStream stream)
	{
		DataInputStream dis=new DataInputStream(stream);
		try
		{
			switch(PacketType.values()[dis.readInt()])
			{
				case OBJECT:
					this.reader.accept(readObject(dis));
					break;
				case PROXY:
					this.reader.accept(readProxy(dis));
					break;
				case INVOKE:
					try
					{
						Object obj=proxyObjects.get(UUID.fromString(readString(dis))).get();
						Method m=obj.getClass().getMethod(readString(dis),TypeUtil.<Class<?>[],Object>cast(readObject(dis)));
						Object[] args=new Object[dis.readInt()];
						for(int i=0;i<args.length;i++)
						{
							switch(PacketType.values()[dis.readInt()])
							{
								case OBJECT:
									args[i]=readObject(dis);
									break;
								case PROXY:
									args[i]=readProxy(dis);
									break;
								default:
									throw new RuntimeException("err packet");
							}
						}
						UUID ri=UUID.fromString(readString(dis));
						if(!ri.equals(new UUID(0L,0L)))
						{
							if(isAllowedToInvoke(m,obj,args))
							{
								Root.setAccessible(m,true);
								try
								{
									Object r=m.invoke(obj,args);
									if(r==null||!m.getReturnType().isInterface())
										sendReturnObject(ri,r);
									else
										sendReturnProxy(ri,r,m.getReturnType());
								}
								catch(InvocationTargetException e)
								{
									throw TypeUtil.throwException(e.getTargetException());
								}
							}
							else
							{
								sendReturnObject(ri,null);
							}
						}
					}
					catch(NullPointerException e)
					{
						System.err.println("Tried to invoke a method("+readString(dis)+") of a released object");
					}
					break;
				case INVOKESTATIC:
					Method m=Class.forName(readString(dis)).getMethod(readString(dis),TypeUtil.<Class<?>[],Object>cast(readObject(dis)));
					Object[] args=new Object[dis.readInt()];
					for(int i=0;i<args.length;i++)
					{
						switch(PacketType.values()[dis.readInt()])
						{
							case OBJECT:
								args[i]=readObject(dis);
								break;
							case PROXY:
								args[i]=readProxy(dis);
								break;
							default:
								throw new RuntimeException("err packet");
						}
					}
					UUID ri=UUID.fromString(readString(dis));
					if(!ri.equals(new UUID(0L,0L)))
					{
						if(isAllowedToInvoke(m,null,args))
						{
							Root.setAccessible(m,true);
							try
							{
								Object r=m.invoke(null,args);
								if(r==null||!m.getReturnType().isInterface())
									sendReturnObject(ri,r);
								else
									sendReturnProxy(ri,r,m.getReturnType());
							}
							catch(InvocationTargetException e)
							{
								throw TypeUtil.throwException(e.getTargetException());
							}
						}
						else
						{
							sendReturnObject(ri,null);
						}
					}
					break;
				case RETURN:
					UUID rid=UUID.fromString(readString(dis));
					switch(PacketType.values()[dis.readInt()])
					{
						case OBJECT:
							returnTasks.get(rid).accept(readObject(dis));
							break;
						case PROXY:
							returnTasks.get(rid).accept(readProxy(dis));
							break;
						default:
							throw new RuntimeException("err packet");
					}
					break;
			}
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	@Deprecated
	public synchronized void sendReturnObject(UUID rid,Object o)
	{
		try
		{
			DataOutputStream dos=new DataOutputStream(out);
			dos.writeInt(PacketType.RETURN.ordinal());
			writeString(dos,rid.toString());
			dos.writeInt(PacketType.OBJECT.ordinal());
			writeObject(dos,o);
			dos.flush();
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	@Deprecated
	public synchronized void sendReturnProxy(UUID rid,Object o,Class<?> interfase)
	{
		try
		{
			DataOutputStream dos=new DataOutputStream(out);
			dos.writeInt(PacketType.RETURN.ordinal());
			writeString(dos,rid.toString());
			dos.writeInt(PacketType.PROXY.ordinal());
			writeProxy(dos,o,interfase);
			dos.flush();
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public boolean isAllowedToInvoke(Method m,Object obj,Object[] args)
	{
		return true;
	}
	public synchronized void sendObject(Object obj)
	{
		DataOutputStream dos=new DataOutputStream(out);
		try
		{
			dos.writeInt(PacketType.OBJECT.ordinal());
			writeObject(dos,obj);
			dos.flush();
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public synchronized void release()
	{
		new ConcurrentHashMap<>(proxyObjects).forEach((u,wr)->
		{
			if(wr.get()==null)
			{
				proxyObjects.remove(u);
			}
		});
		threshold=proxyObjects.size()*6/5;
	}
	public Object readProxy(DataInput input)
	{
		try
		{
			UUID id=UUID.fromString(readString(input));
			Class<?> interfase=Class.forName(readString(input));
			return Proxy.newProxyInstance(interfase.getClassLoader(),new Class<?>[]{interfase},(obj,m,args)->
			{
				return sendInvoke(id,m,args);
			});
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public void writeProxy(DataOutput output,Object obj,Class<?> interfase)
	{
		UUID id;
		while(proxyObjects.containsKey(id=UUID.randomUUID()))
			;
		proxyObjects.put(id,new WeakReference<Object>(obj));
		if(proxyObjects.size()>threshold)
			release();
		writeString(output,id.toString());
		writeString(output,interfase.getName());
	}
	public synchronized void sendProxy(Object obj,Class<?> interfase)
	{
		if(!interfase.isInterface())
			throw new IllegalArgumentException();
		if(!interfase.isInstance(obj))
			throw new IllegalArgumentException();
		DataOutputStream dos=new DataOutputStream(this.out);
		try
		{
			dos.writeInt(PacketType.PROXY.ordinal());
			writeProxy(dos,obj,interfase);
			dos.flush();
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	@SuppressWarnings("deprecation")
	public synchronized Object sendInvoke(UUID id,Method m,Object[] args)
	{
		DataOutputStream dos=new DataOutputStream(out);
		try
		{
			dos.writeInt(PacketType.INVOKE.ordinal());
			writeString(dos,id.toString());
			writeString(dos,m.getName());
			Class<?>[] types=m.getParameterTypes();
			writeObject(dos,types);
			dos.writeInt(args.length);
			for(int i=0;i<args.length;i++)
			{
				if(args[i]==null||!types[i].isInterface())
					writeObject(dos,args[i]);
				else
					writeProxy(dos,args[i],types[i]);
			}
			if(m.getReturnType()!=void.class)
			{
				UUID rid;
				while(returnTasks.containsKey(rid=UUID.randomUUID()))
					;
				writeString(dos,rid.toString());
				Thread t=Thread.currentThread();
				Object[] r={null};
				returnTasks.put(rid,o->
				{
					r[0]=o;
					t.resume();
				});
				dos.flush();
				t.suspend();
				return r[0];
			}
			else
			{
				writeString(dos,new UUID(0L,0L).toString());
				dos.flush();
				return null;
			}
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	@SuppressWarnings("deprecation")
	public synchronized Object sendInvokeStatic(Method m,Object[] args)
	{
		DataOutputStream dos=new DataOutputStream(out);
		try
		{
			dos.writeInt(PacketType.INVOKESTATIC.ordinal());
			writeString(dos,m.getDeclaringClass().getName());
			writeString(dos,m.getName());
			Class<?>[] types=m.getParameterTypes();
			writeObject(dos,types);
			dos.writeInt(args.length);
			for(int i=0;i<args.length;i++)
			{
				if(args[i]==null||!types[i].isInterface())
					writeObject(dos,args[i]);
				else
					writeProxy(dos,args[i],types[i]);
			}
			if(m.getReturnType()!=void.class)
			{
				UUID rid;
				while(returnTasks.containsKey(rid=UUID.randomUUID()))
					;
				writeString(dos,rid.toString());
				Thread t=Thread.currentThread();
				Object[] r={null};
				returnTasks.put(rid,o->
				{
					r[0]=o;
					t.resume();
				});
				dos.flush();
				t.suspend();
				return r[0];
			}
			else
			{
				writeString(dos,new UUID(0L,0L).toString());
				dos.flush();
				return null;
			}
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public synchronized void close()
	{
		try
		{
			out.close();
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	public enum PacketType
	{
		OBJECT,
		PROXY,
		INVOKE,
		INVOKESTATIC,
		RETURN
	}
}
