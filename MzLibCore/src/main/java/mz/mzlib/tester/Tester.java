package mz.mzlib.tester;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.async.AsyncFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;

public interface Tester<C extends TesterContext>
{
    String getName();
    
    Class<C> getContextType();
    
    default int getMinLevel()
    {
        return 0;
    }
    
    default boolean shouldTest(TesterContext context)
    {
        return this.getContextType().isInstance(context) && context.level>=this.getMinLevel();
    }
    
    CompletableFuture<List<Throwable>> test(C context);
    
    class Registrar implements IRegistrar<Tester<?>>
    {
        public static Registrar instance = new Registrar();
        public List<Tester<?>> testers = new CopyOnWriteArrayList<>();
        
        @Override
        public Class<Tester<?>> getType()
        {
            return RuntimeUtil.cast(Tester.class);
        }
        
        @Override
        public void register(MzModule module, Tester<?> object)
        {
            this.testers.add(object);
        }
        @Override
        public void unregister(MzModule module, Tester<?> object)
        {
            this.testers.remove(object);
        }
    }
    
    static CompletableFuture<List<Throwable>> testAll(TesterContext testContext)
    {
        return new AsyncFunction<List<Throwable>>()
        {
            protected List<Throwable> template() throws Throwable
            {
                List<Throwable> result = new ArrayList<>();
                for(Tester<?> tester: Registrar.instance.testers)
                {
                    if(tester.shouldTest(testContext))
                    {
                        CompletableFuture<List<Throwable>> test = tester.test(RuntimeUtil.cast(testContext));
                        await0(test);
                        result.addAll(test.get());
                    }
                }
                return result;
            }
            public void run()
            {
            }
        }.start(ForkJoinPool.commonPool());
    }
}
