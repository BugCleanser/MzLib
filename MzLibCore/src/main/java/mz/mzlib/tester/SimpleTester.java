package mz.mzlib.tester;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class SimpleTester<C extends TesterContext> implements Tester<C>
{
    public String name;
    public Class<C> contextType;
    public int minLevel;
    public Function<C, CompletableFuture<List<Throwable>>> tester;
    public SimpleTester(String name, Class<C> contextType, int minLevel, Function<C, CompletableFuture<List<Throwable>>> tester)
    {
        this.name = name;
        this.contextType = contextType;
        this.minLevel = minLevel;
        this.tester = tester;
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
    
    @Override
    public CompletableFuture<List<Throwable>> test(C context)
    {
        return this.tester.apply(context);
    }
}
