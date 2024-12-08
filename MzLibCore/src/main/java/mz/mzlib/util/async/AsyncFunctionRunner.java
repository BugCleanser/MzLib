package mz.mzlib.util.async;

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
}
