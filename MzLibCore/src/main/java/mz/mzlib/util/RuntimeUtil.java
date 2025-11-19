package mz.mzlib.util;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Predicate;

public class RuntimeUtil
{
    public static int jvmVersion;

    static
    {
        try
        {
            //noinspection JavaReflectionMemberAccess
            Object v = Runtime.class.getMethod("version").invoke(null);
            jvmVersion = (int) v.getClass().getMethod("major").invoke(v);
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored)
        {
            jvmVersion = 8;
        }
    }

    public static void nop()
    {
    }

    public static <T> T nul()
    {
        return RuntimeUtil.cast(null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object)
    {
        return (T) object;
    }

    public static byte castBooleanToByte(boolean value)
    {
        return (byte) (value ? 1 : 0);
    }
    public static boolean castByteToBoolean(byte value)
    {
        return value != 0;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> T orNull(Optional<T> optional)
    {
        return optional.orElse(null);
    }

    public static <T extends Throwable> RuntimeException sneakilyThrow(Throwable e) throws T
    {
        throw RuntimeUtil.<T>cast(e);
    }

    public static void sneakilyRun(ThrowableRunnable<? extends Throwable> action)
    {
        action.run();
    }
    public static <T> T sneakilyRun(ThrowableSupplier<T, ? extends Throwable> action)
    {
        return action.get();
    }

    /**
     * Throw an exception, but it's an expression <br/>
     * Used with a triadic operator <br/>
     * Example:
     * <pre><code>
     *     Object o = null;
     *     if(condition)
     *         o = new Object();
     *     else
     *         throw new UnsupportedOperationException();
     * </code></pre>
     * After simplification:
     * <pre><code>
     *     Object o = condition ? new Object() : RuntimeUtil.valueThrow(new UnsupportedOperationException());
     * </code></pre>
     */
    public static <T, E extends Throwable> T valueThrow(E throwable) throws E
    {
        throw throwable;
    }

    @SuppressWarnings("RedundantThrows")
    public static <T extends Throwable> void declaredlyThrow() throws T
    {
    }

    @SuppressWarnings("RedundantThrows")
    public static <T extends Throwable> void declaredlyThrow(Class<T> clazz) throws T
    {
    }
    public static void addGcListener(NotificationListener listener)
    {
        for(GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans())
        {
            if(gcBean instanceof NotificationEmitter)
            {
                ((NotificationEmitter) gcBean).addNotificationListener(listener, null, null);
                return;
            }
        }
        throw new UnsupportedOperationException();
    }
    public static void removeGcListener(NotificationListener listener)
    {
        try
        {
            for(GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans())
            {
                try
                {
                    if(gcBean instanceof NotificationEmitter)
                        ((NotificationEmitter) gcBean).removeNotificationListener(listener);
                }
                catch(ListenerNotFoundException ignored)
                {
                }
            }
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    public static Throwable runAndCatch(ThrowableRunnable<?> runnable)
    {
        try
        {
            runnable.runOrThrow();
        }
        catch(Throwable e)
        {
            return e;
        }
        return null;
    }

    public static <T> T require(T object, Predicate<T> con)
    {
        assert con.test(object);
        return object;
    }
}
