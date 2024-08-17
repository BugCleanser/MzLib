package mz.mzlib.mappings;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Util
{
    public static CompletableFuture<String> cache(File file, Supplier<CompletableFuture<String>> supplier)
    {
        CompletableFuture<String> result=new CompletableFuture<>();
        cache0(file,()->
        {
            CompletableFuture<byte[]> re=new CompletableFuture<>();
            supplier.get().whenComplete((r,e)->
            {
                if(e!=null)
                {
                    re.completeExceptionally(e);
                    return;
                }
                re.complete(r.getBytes(StandardCharsets.UTF_8));
            });
            return re;
        }).whenComplete((r,e)->
        {
            if(e!=null)
            {
                result.completeExceptionally(e);
                return;
            }
            result.complete(new String(r, StandardCharsets.UTF_8));
        });
        return result;
    }
    public static CompletableFuture<byte[]> cache0(File file, Supplier<CompletableFuture<byte[]>> supplier)
    {
        if(file==null)
            return supplier.get();
        if(file.isFile())
        {
            try(FileInputStream fis=new FileInputStream(file))
            {
                return CompletableFuture.completedFuture(readInputStream(fis));
            }
            catch (Throwable e)
            {
                CompletableFuture<byte[]> result=new CompletableFuture<>();
                result.completeExceptionally(e);
                return result;
            }
        }
        else
        {
            CompletableFuture<byte[]> result=new CompletableFuture<>();
            supplier.get().whenComplete((r, e) ->
            {
                if(e!=null)
                {
                    result.completeExceptionally(e);
                    return;
                }
                boolean ignored=file.getParentFile().mkdirs();
                try(FileOutputStream fos=new FileOutputStream(file))
                {
                    fos.write(r);
                }
                catch (Throwable e1)
                {
                    result.completeExceptionally(e1);
                    return;
                }
                result.complete(r);
            });
            return result;
        }
    }

    public static CompletableFuture<byte[]> request0(String url)
    {
        return request0(url(url));
    }
    public static CompletableFuture<byte[]> request0(URL url)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            try
            {
                return readInputStream(openConnectionCheckRedirects(url.openConnection()));
            }
            catch (Throwable e)
            {
                throw sneakilyThrow(e);
            }
        });
    }

    public static CompletableFuture<String> request(String url)
    {
        return request(url(url));
    }

    public static CompletableFuture<String> request(URL url)
    {
        CompletableFuture<String> future = new CompletableFuture<>();
        request0(url).whenComplete((r, e) ->
        {
            if (e != null)
            {
                future.completeExceptionally(e);
                return;
            }
            future.complete(new String(r, StandardCharsets.UTF_8));
        });
        return future;
    }

    public static <T> T runCatching(ThrowableSupplier<T> supplier)
    {
        try
        {
            return supplier.get();
        }
        catch (Throwable e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void runCatching(ThrowableRunnable runnable)
    {
        try
        {
            runnable.run();
        }
        catch (Throwable e)
        {
            throw new RuntimeException(e);
        }
    }

    public static URL url(String url)
    {
        return runCatching(() ->
                        // make absolute url
//                URI.create(url).toURL()
                        new URL(url)
        );
    }

    public interface ThrowableSupplier<T>
    {
        T get() throws Throwable;
    }

    public interface ThrowableRunnable
    {
        void run() throws Throwable;
    }

    public static byte[] readInputStream(InputStream inputStream)
    {
        byte[] buffer = new byte[4096];
        int len = 0;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream())
        {
            while ((len = inputStream.read(buffer)) != -1)
            {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        }
        catch (Throwable e)
        {
            throw new RuntimeException(e);
        }
    }

    public static InputStream openConnectionCheckRedirects(URLConnection c) throws IOException
    {
        boolean redir;
        int redirects = 0;
        InputStream in;
        do
        {
            if (c instanceof HttpURLConnection)
            {
                ((HttpURLConnection) c).setInstanceFollowRedirects(false);
            }
            in = c.getInputStream();
            redir = false;
            if (c instanceof HttpURLConnection)
            {
                HttpURLConnection http = (HttpURLConnection) c;
                int stat = http.getResponseCode();
                if (stat >= 300 && stat <= 307 && stat != 306 && stat != HttpURLConnection.HTTP_NOT_MODIFIED)
                {
                    URL base = http.getURL();
                    String loc = http.getHeaderField("Location");
                    URL target = null;
                    if (loc != null)
                    {
                        target = new URL(base, loc);
                    }
                    http.disconnect();
                    if (target == null || redirects >= 5)
                    {
                        throw new SecurityException("illegal URL redirect");
                    }
                    redir = true;
                    c = target.openConnection();
                    redirects++;
                }
            }
        } while (redir);
        return in;
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object)
    {
        return (T) object;
    }
    public static <T extends Throwable> RuntimeException sneakilyThrow(Throwable e) throws T
    {
        throw Util.<T>cast(e);
    }
}
