package mz.mzlib.tester;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.async.AsyncFunction;
import mz.mzlib.util.async.AsyncFunctionRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

public interface Tester<C extends TesterContext>
{
    String getName();
    Class<C> getContextType();
    CompletableFuture<List<Throwable>> test(C context);
    
    class Registrar implements IRegistrar<Tester<?>>
    {
        public static Registrar instance=new Registrar();
        public List<Tester<?>> testers=new CopyOnWriteArrayList<>();
        
        @Override
        public Class<Tester<?>> getType()
        {
            return RuntimeUtil.cast(Tester.class);
        }
        
        @Override
        public void register(MzModule module,Tester<?> object)
        {
            this.testers.add(object);
        }
        @Override
        public void unregister(MzModule module,Tester<?> object)
        {
            this.testers.remove(object);
        }
    }
    static CompletableFuture<List<Throwable>> testAll(TesterContext testContext, Executor executor)
    {
        return new AsyncFunction<List<Throwable>>()
        {
            @Override
            public void run()
            {
            }
            
            @Override
            public List<Throwable> template() throws Throwable
            {
                List<Throwable> result=new ArrayList<>();
                for(Tester<?> tester: Registrar.instance.testers)
                {
                    if(tester.getContextType().isAssignableFrom(testContext.getClass()))
                    {
                        CompletableFuture<List<Throwable>> test=tester.test(RuntimeUtil.cast(testContext));
                        await0(test);
                        result.addAll(test.get());
                    }
                }
                return result;
            }
        }.start(executor);
    }
}
