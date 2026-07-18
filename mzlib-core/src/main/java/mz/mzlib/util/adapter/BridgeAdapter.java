package mz.mzlib.util.adapter;

import mz.mzlib.util.TypeUtil;

import java.lang.reflect.AnnotatedType;

@SuppressWarnings("unused")
public interface BridgeAdapter<T>
{
    class Processor<A extends BridgeAdapter<T>, T, U> implements Adapter.Processor<A, U>
    {
        Adapter.Processor<T, U> target;
        
        @Override
        public void init(AnnotatedType type)
        {
            AnnotatedType[] args = TypeUtil.resolveTypeArguments(type, BridgeAdapter.class);
            if(args == null)
                throw new IllegalArgumentException();
            this.target = Adapter.Processor.of(args[0]);
        }
        @Override
        public Class<? super U> getAdapteeClass()
        {
            return this.target.getAdapteeClass();
        }
        @Override
        public Adapter.Processor<A, U> activate()
        {
            this.target = this.target.activate();
            return this;
        }
        @Override
        public A adapt(U value)
        {
            //noinspection unchecked
            return (A) this.target.adapt(value);
        }
        @Override
        public U revert(A value)
        {
            //noinspection unchecked
            return this.target.revert((T) value);
        }
    }
}
