package mz.mzlib.util.async;

import mz.mzlib.module.MzModule;

import java.util.concurrent.Executor;

public interface AsyncFunctionRunner extends Executor
{
    void schedule(Runnable function);

    void schedule(Runnable function, BasicAwait await);
    
    @Override
    @SuppressWarnings("NullableProblems")
    default void execute(Runnable command)
    {
        this.schedule(command);
    }
    
    static AsyncFunctionRunner fromExecutor(Executor executor)
    {
        if(executor instanceof AsyncFunctionRunner)
            return (AsyncFunctionRunner) executor;
        return new AsyncFunctionRunner()
        {
            @Override
            public void schedule(Runnable function)
            {
                executor.execute(function);
            }
            
            @Override
            public void schedule(Runnable function,BasicAwait await)
            {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    class DelegatorModule implements AsyncFunctionRunner
    {
        public AsyncFunctionRunner delegate;
        public MzModule module;
        
        public DelegatorModule(AsyncFunctionRunner delegate, MzModule module)
        {
            this.delegate = delegate;
            this.module = module;
        }
        
        @Override
        public void schedule(Runnable function)
        {
            if(!module.isLoaded())
                return;
            delegate.schedule(()->
            {
                if(!module.isLoaded())
                    return;
                function.run();
            });
        }
        @Override
        public void schedule(Runnable function, BasicAwait await)
        {
            if(!module.isLoaded())
                return;
            delegate.schedule(()->
            {
                if(!module.isLoaded())
                    return;
                function.run();
            }, await);
        }
    }
    default DelegatorModule asModule(MzModule module)
    {
        return new DelegatorModule(this, module);
    }
}
