package mz.lib;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.*;

public final class FileUtil
{
	private FileUtil(){}
	
	public static int bufSize=1024;
	
	public static byte[] deflate(byte[] data)
	{
		Deflater deflater=new Deflater();
		deflater.setInput(data);
		deflater.finish();
		List<byte[]> bufs=new ArrayList<>();
		byte[] buf;
		int t;
		while((t=deflater.deflate(buf=new byte[bufSize]))==bufSize)
			bufs.add(buf);
		deflater.end();
		ByteBuffer r=ByteBuffer.allocate(bufs.size()*bufSize+t);
		for(byte[] i:bufs)
			r.put(i);
		r.put(buf,0,t);
		return r.array();
	}
	public static byte[] inflate(byte[] data)
	{
		try
		{
			Inflater inflater=new Inflater();
			inflater.setInput(data);
			List<byte[]> bufs=new ArrayList<>();
			byte[] buf;
			int t;
			while((t=inflater.inflate(buf=new byte[bufSize]))==bufSize)
				bufs.add(buf);
			inflater.end();
			ByteBuffer r=ByteBuffer.allocate(bufs.size()*bufSize+t);
			for(byte[] i: bufs)
				r.put(i);
			r.put(buf,0,t);
			return r.array();
		}
		catch(DataFormatException e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	/**
	 * 读取整个文件流
	 *
	 * @param inputStream 文件流
	 * @return 所有字节
	 * @throws IOException 文件流异常
	 */
	public static byte[] readInputStream(InputStream inputStream)
	{
		byte[] buffer=new byte[bufSize];
		int len=0;
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		try
		{
			while((len=inputStream.read(buffer))!=-1)
			{
				bos.write(buffer,0,len);
			}
			bos.close();
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
		return bos.toByteArray();
	}
	public static void copy(InputStream i,OutputStream o)
	{
		byte[] b=new byte[bufSize];
		int l;
		try
		{
			while((l=i.read(b))!=-1)
				o.write(b,0,l);
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static void exportDir(JarFile jar,String jarDir,File dir)
	{
		exportDir(jar,jarDir,dir,true);
	}
	public static void exportDir(JarFile jar,String jarDir,File dir,boolean cover)
	{
		if(dir.isFile())
			throw new IllegalArgumentException("dir");
		jarDir=jarDir.replace('\\','/');
		if(!jarDir.endsWith("/"))
			jarDir+='/';
		Enumeration<JarEntry> jee=jar.entries();
		while(jee.hasMoreElements())
		{
			JarEntry je=jee.nextElement();
			if(!je.isDirectory()&&je.getName().startsWith(jarDir))
			{
				File f=new File(dir,je.getName().substring(jarDir.length()));
				File d=f.getParentFile();
				if(d.isFile())
					throw new IllegalArgumentException(d.getPath());
				if(!d.exists())
					d.mkdirs();
				try
				{
					l1:
					{
						if(!f.exists())
							f.createNewFile();
						else if(!cover)
							break l1;
						try(FileOutputStream fos=new FileOutputStream(f))
						{
							copy(jar.getInputStream(je),fos);
						}
					}
				}
				catch(Throwable e)
				{
					throw TypeUtil.throwException(e);
				}
			}
		}
	}
	
	public static InputStream openConnectionCheckRedirects(URLConnection c) throws IOException
	{
		boolean redir;
		int redirects=0;
		InputStream in;
		do
		{
			if(c instanceof HttpURLConnection)
			{
				((HttpURLConnection) c).setInstanceFollowRedirects(false);
			}
			in=c.getInputStream();
			redir=false;
			if(c instanceof HttpURLConnection)
			{
				HttpURLConnection http=(HttpURLConnection) c;
				int stat=http.getResponseCode();
				if(stat>=300&&stat<=307&&stat!=306&&stat!=HttpURLConnection.HTTP_NOT_MODIFIED)
				{
					URL base=http.getURL();
					String loc=http.getHeaderField("Location");
					URL target=null;
					if(loc!=null)
					{
						target=new URL(base,loc);
					}
					http.disconnect();
					if(target==null||redirects>=5)
					{
						throw new SecurityException("illegal URL redirect");
					}
					redir=true;
					c=target.openConnection();
					redirects++;
				}
			}
		}while(redir);
		return in;
	}
}
