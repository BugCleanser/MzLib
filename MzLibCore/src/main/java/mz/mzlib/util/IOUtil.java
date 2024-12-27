package mz.mzlib.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class IOUtil
{
    public static int bufSize = 1024 * 1024;

    public static Properties readProperties(InputStream is) throws IOException
    {
        Properties properties=new Properties();
        properties.load(new InputStreamReader(is, StandardCharsets.UTF_8));
        return properties;
    }

    public static byte[] readAll(InputStream stream) throws IOException
    {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[bufSize];
        int bytesRead;
        while ((bytesRead = stream.read(buffer)) != -1)
        {
            result.write(buffer, 0, bytesRead);
        }
        return result.toByteArray();
    }

    public static <E extends Throwable> byte[] cache(File file, ThrowableSupplier<byte[],E> supplier) throws IOException,E
    {
        if(file.isFile())
        {
            try(FileInputStream fis=new FileInputStream(file))
            {
                return readAll(fis);
            }
        }
        else
        {
            byte[] result=supplier.getWithThrowable();
            boolean ignored=file.getParentFile().mkdirs();
            try(FileOutputStream fos=new FileOutputStream(file))
            {
                fos.write(result);
            }
            return result;
        }
    }

    public static InputStream openConnectionCheckRedirects(URL url, int retry) throws IOException
    {
        assert retry>0;
        IOException lastException=null;
        for(int i=0;i<retry;i++)
        {
            try
            {
                return openConnectionCheckRedirects(url);
            }
            catch (IOException e)
            {
                lastException=e;
            }
        }
        throw lastException;
    }
    public static InputStream openConnectionCheckRedirects(URL url) throws IOException
    {
        boolean redir;
        URLConnection c = url.openConnection();
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
