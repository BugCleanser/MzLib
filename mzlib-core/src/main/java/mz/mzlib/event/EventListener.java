package mz.mzlib.event;

import mz.mzlib.util.RuntimeUtil;
import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EventListener<T extends Event>
{
    Class<T> type;
    float priority;
    @ApiStatus.Experimental
    Consumer<T> handler;
    
    @ApiStatus.Experimental
    public void run(T event)
    {
        this.handler.accept(event);
    }
    
    public static <T extends Event> EventListener<T> of(Class<T> type, float priority, Consumer<T> handler)
    {
        return new EventListener<>(type, priority, handler);
    }
    public static <T extends Event> EventListener<T> of(Class<T> type, Consumer<T> handler)
    {
        return of(type, 0.f, handler);
    }
    
    protected EventListener(Class<T> type, float priority)
    {
        this.type = type;
        this.priority = priority;
        this.handler = it -> RuntimeUtil.valueThrow(new AbstractMethodError());
    }
    @Deprecated
    public EventListener(Class<T> type, float priority, Consumer<T> handler)
    {
        this.type = type;
        this.priority = priority;
        this.handler = handler;
    }
    @Deprecated
    public EventListener(Class<T> type, Consumer<T> handler)
    {
        this(type, 0.f, handler);
    }
    
    public static class FutureSupplier<T extends Event> extends EventListener<T> implements Supplier<CompletableFuture<T>>
    {
        public static <T extends Event> FutureSupplier<T> of(Class<T> type, float priority)
        {
            return new FutureSupplier<>(type, priority);
        }
        public static <T extends Event> FutureSupplier<T> of(Class<T> type)
        {
            return of(type, 0.f);
        }
        
        private FutureSupplier(Class<T> type, float priority)
        {
            super(type, priority);
        }
        
        AtomicReference<CompletableFuture<T>> future = new AtomicReference<>(new CompletableFuture<>());
        
        @Override
        public CompletableFuture<T> get()
        {
            return this.future.get();
        }
        
        @Override
        public void run(T event)
        {
            this.future.getAndSet(new CompletableFuture<>()).complete(event);
        }
    }
}
