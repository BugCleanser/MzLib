package mz.mzlib.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil
{
	private IOUtil() {}
	
	public static int bufSize=1024*1024;
	
	public static byte[] readAll(InputStream stream) throws IOException
	{
		ByteArrayOutputStream result=new ByteArrayOutputStream();
		byte[] buffer = new byte[bufSize];
		int bytesRead;
		while((bytesRead=stream.read(buffer))!=-1)
		{
			result.write(buffer,0,bytesRead);
		}
		return result.toByteArray();
	}
}
