package mz.mzlib.tester;

import mz.mzlib.util.ThrowableConsumer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class SimpleTester<C extends TesterContext> implements Tester<C>
{
    String name;
    Class<C> contextType;
    int minLevel;
    Function<C, CompletableFuture<List<Throwable>>> function;
    public SimpleTester(String name, Class<C> contextType, int minLevel, Function<C, CompletableFuture<List<Throwable>>> function)
    {
        this.name = name;
        this.contextType = contextType;
        this.minLevel = minLevel;
        this.function = function;
    }
    
    @Override
    public String getName()
    {
        return this.name;
    }
    
    @Override
    public Class<C> getContextType()
    {
        return this.contextType;
    }
    
    @Override
    public int getMinLevel()
    {
        return this.minLevel;
    }
    
    public Function<C, CompletableFuture<List<Throwable>>> getFunction()
    {
        return this.function;
    }
    
    @Override
    public CompletableFuture<List<Throwable>> test(C context)
    {
        return this.function.apply(context);
    }
    
    public static class Builder<C extends TesterContext>
    {
        SimpleTester<C> data;
        public Builder(Class<C> contextType)
        {
            data = new SimpleTester<>("<unnamed>", contextType, 0, context->CompletableFuture.completedFuture(Collections.emptyList()));
        }
        
        public static Builder<TesterContext> common()
        {
            return new Builder<>(TesterContext.class);
        }
        
        public SimpleTester<C> get()
        {
            return this.data;
        }
        
        public Builder<C> setName(String name)
        {
            this.get().name = name;
            return this;
        }
        
        public Builder<C> setMinLevel(int minLevel)
        {
            this.get().minLevel = minLevel;
            return this;
        }
        
        public Builder<C> setFunction(Function<C, CompletableFuture<List<Throwable>>> function)
        {
            this.get().function = function;
            return this;
        }
        
        public Builder<C> setFunction(ThrowableConsumer<C, Throwable> function)
        {
            return this.setFunction(c->
            {
                try
                {
                    function.acceptOrThrow(c);
                }
                catch(Throwable e)
                {
                    return CompletableFuture.completedFuture(Collections.singletonList(e));
                }
                return CompletableFuture.completedFuture(Collections.emptyList());
            });
        }
        
        public SimpleTester<C> build()
        {
            return new SimpleTester<>(this.get().getName(), this.get().getContextType(), this.get().getMinLevel(), this.get().getFunction());
        }
    }
}
