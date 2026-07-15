package mz.mzlib.util.adapter;

import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.TypeUtil;
import mz.mzlib.util.proxy.SetProxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.Set;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Adapter(AdapterSet.Processor.class)
public @interface AdapterSet
{
    class Processor<T, U> implements Adapter.Processor<Set<T>, Set<U>>
    {
        Adapter.Processor<T, U> delegate;
        @Override
        public void init(AnnotatedType type)
        {
            if(TypeUtil.toClass(type.getType()) != Set.class)
                throw new IllegalArgumentException("Must be Set: "+type);
            if(!(type instanceof AnnotatedParameterizedType))
                throw new IllegalArgumentException("The Set has no type args:" + type);
            AnnotatedType[] args = ((AnnotatedParameterizedType) type).getAnnotatedActualTypeArguments();
            this.delegate = Adapter.Processor.of(args[0]);
        }
        @Override
        public Adapter.Processor<Set<T>, Set<U>> activate()
        {
            return new Activated<>(this.delegate.activate());
        }
        @Override
        public Class<? super Set<U>> getSourceClass()
        {
            return Set.class;
        }
        @Override
        public Set<T> adapt(Set<U> value)
        {
            throw new IllegalStateException();
        }
        @Override
        public Set<U> revert(Set<T> value)
        {
            throw new IllegalStateException();
        }
        
        static class Activated<T, U> implements Adapter.Processor<Set<T>, Set<U>>
        {
            FunctionInvertible<U, T> function;
            FunctionInvertible<T, U> functionInverse;
            public Activated(Adapter.Processor<T, U> delegate)
            {
                this.function = delegate.toFunction();
                this.functionInverse = this.function.inverse();
            }
            
            @Override
            public void init(AnnotatedType type)
            {
                throw new IllegalStateException();
            }
            
            @Override
            public Class<? super Set<U>> getSourceClass()
            {
                return Set.class;
            }
            
            @Override
            public Set<T> adapt(Set<U> value)
            {
                if(value instanceof SetProxy)
                {
                    SetProxy<?, ?> it = (SetProxy<?, ?>) value;
                    if(it.getFunction().equals(this.functionInverse))
                    {
                        //noinspection unchecked
                        return (Set<T>) it.getDelegate();
                    }
                }
                return new SetProxy<>(value, this.function);
            }
            @Override
            public Set<U> revert(Set<T> value)
            {
                if(value instanceof SetProxy)
                {
                    SetProxy<?, ?> it = (SetProxy<?, ?>) value;
                    if(it.getFunction().equals(this.function))
                    {
                        //noinspection unchecked
                        return (Set<U>) it.getDelegate();
                    }
                }
                return new SetProxy<>(value, this.functionInverse);
            }
        }
    }
}
