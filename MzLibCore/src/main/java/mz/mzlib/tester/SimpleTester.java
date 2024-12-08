package mz.mzlib.tester;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class SimpleTester<C extends TesterContext> implements Tester<C>
{
    public String name;
    public Class<C> contextType;
    public Function<C, CompletableFuture<List<Throwable>>> tester;
    public SimpleTester(String name,Class<C> contextType, Function<C, CompletableFuture<List<Throwable>>> tester)
    {
        this.name=name;
        this.contextType =contextType;
        this.tester=tester;
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
    public CompletableFuture<List<Throwable>> test(C context)
    {
        return this.tester.apply(context);
    }
}
