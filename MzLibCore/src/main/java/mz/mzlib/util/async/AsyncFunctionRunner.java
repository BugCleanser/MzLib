package mz.mzlib.util.async;

import mz.mzlib.module.MzModule;

import java.util.HashSet;
import java.util.Set;
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
            public void schedule(Runnable function, BasicAwait await)
            {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Deprecated
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
            delegate.schedule(() ->
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
            delegate.schedule(
                () ->
                {
                    if(!module.isLoaded())
                        return;
                    function.run();
                }, await
            );
        }
    }
    /**
     * @see #registrable()
     */
    @Deprecated
    default DelegatorModule asModule(MzModule module)
    {
        return new DelegatorModule(this, module);
    }

    class Registrable implements AsyncFunctionRunner, mz.mzlib.module.Registrable
    {
        AsyncFunctionRunner delegate;

        public Registrable(AsyncFunctionRunner delegate)
        {
            this.delegate = delegate;
        }

        Set<MzModule> modules = new HashSet<>();
        @Override
        public void onRegister(MzModule module)
        {
            this.modules.add(module);
        }
        @Override
        public void onUnregister(MzModule module)
        {
            this.modules.remove(module);
        }
        @Override
        public void schedule(Runnable function)
        {
            if(this.modules.isEmpty())
                return;
            this.delegate.schedule(function);
        }
        @Override
        public void schedule(Runnable function, BasicAwait await)
        {
            if(this.modules.isEmpty())
                return;
            this.delegate.schedule(function, await);
        }
    }
    default AsyncFunctionRunner registrable()
    {
        return new Registrable(this);
    }
}
