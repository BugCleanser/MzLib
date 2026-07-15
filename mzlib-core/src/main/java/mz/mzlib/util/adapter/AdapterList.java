package mz.mzlib.util.adapter;

import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.TypeUtil;
import mz.mzlib.util.proxy.ListProxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Adapter(AdapterList.Processor.class)
public @interface AdapterList
{
    class Processor<T, U> implements Adapter.Processor<List<T>, List<U>>
    {
        Adapter.Processor<T, U> delegate;
        @Override
        public void init(AnnotatedType type)
        {
            if(TypeUtil.toClass(type.getType()) != List.class)
                throw new IllegalArgumentException("Must be List: "+type);
            if(!(type instanceof AnnotatedParameterizedType))
                throw new IllegalArgumentException("The List has no type args:" + type);
            AnnotatedType[] args = ((AnnotatedParameterizedType) type).getAnnotatedActualTypeArguments();
            this.delegate = Adapter.Processor.of(args[0]);
        }
        @Override
        public Adapter.Processor<List<T>, List<U>> activate()
        {
            return new Activated<>(this.delegate.activate());
        }
        @Override
        public Class<? super List<U>> getSourceClass()
        {
            return List.class;
        }
        @Override
        public List<T> adapt(List<U> value)
        {
            throw new IllegalStateException();
        }
        @Override
        public List<U> revert(List<T> value)
        {
            throw new IllegalStateException();
        }
        
        static class Activated<T, U> implements Adapter.Processor<List<T>, List<U>>
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
            public Class<? super List<U>> getSourceClass()
            {
                return List.class;
            }
            
            @Override
            public List<T> adapt(List<U> value)
            {
                if(value instanceof ListProxy)
                {
                    ListProxy<?, ?> it = (ListProxy<?, ?>) value;
                    if(it.getFunction().equals(this.functionInverse))
                    {
                        //noinspection unchecked
                        return (List<T>) it.getDelegate();
                    }
                }
                return new ListProxy<>(value, this.function);
            }
            @Override
            public List<U> revert(List<T> value)
            {
                if(value instanceof ListProxy)
                {
                    ListProxy<?, ?> it = (ListProxy<?, ?>)value;
                    if(it.getFunction().equals(this.function))
                    {
                        //noinspection unchecked
                        return (List<U>) it.getDelegate();
                    }
                }
                return new ListProxy<>(value, this.functionInverse);
            }
        }
    }
}
