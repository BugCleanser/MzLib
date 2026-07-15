package mz.mzlib.util.adapter;

import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.TypeUtil;
import mz.mzlib.util.proxy.CollectionProxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.Collection;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Adapter(AdapterCollection.Processor.class)
public @interface AdapterCollection
{
    class Processor<T, U> implements Adapter.Processor<Collection<T>, Collection<U>>
    {
        Adapter.Processor<T, U> delegate;
        @Override
        public void init(AnnotatedType type)
        {
            if(TypeUtil.toClass(type.getType()) != Collection.class)
                throw new IllegalArgumentException("Must be Collection: "+type);
            if(!(type instanceof AnnotatedParameterizedType))
                throw new IllegalArgumentException("The Collection has no type args:" + type);
            AnnotatedType[] args = ((AnnotatedParameterizedType) type).getAnnotatedActualTypeArguments();
            this.delegate = Adapter.Processor.of(args[0]);
        }
        @Override
        public Adapter.Processor<Collection<T>, Collection<U>> activate()
        {
            return new Activated<>(this.delegate.activate());
        }
        @Override
        public Class<? super Collection<U>> getSourceClass()
        {
            return Collection.class;
        }
        @Override
        public Collection<T> adapt(Collection<U> value)
        {
            throw new IllegalStateException();
        }
        @Override
        public Collection<U> revert(Collection<T> value)
        {
            throw new IllegalStateException();
        }
        
        static class Activated<T, U> implements Adapter.Processor<Collection<T>, Collection<U>>
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
            public Class<? super Collection<U>> getSourceClass()
            {
                return Collection.class;
            }
            
            @Override
            public Collection<T> adapt(Collection<U> value)
            {
                if(value instanceof CollectionProxy)
                {
                    CollectionProxy<?, ?> it = (CollectionProxy<?, ?>) value;
                    if(it.getFunction().equals(this.functionInverse))
                    {
                        //noinspection unchecked
                        return (Collection<T>) it.getDelegate();
                    }
                }
                return CollectionProxy.of(value, this.function);
            }
            @Override
            public Collection<U> revert(Collection<T> value)
            {
                if(value instanceof CollectionProxy)
                {
                    CollectionProxy<?, ?> it = (CollectionProxy<?, ?>) value;
                    if(it.getFunction().equals(this.function))
                    {
                        //noinspection unchecked
                        return (Collection<U>) it.getDelegate();
                    }
                }
                return CollectionProxy.of(value, this.functionInverse);
            }
        }
    }
}
