package mz.mzlib.util.adapter;

import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.TypeUtil;
import mz.mzlib.util.wrapper.WrapperClassData;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.TYPE_USE})
public @interface Adapter
{
    @SuppressWarnings("rawtypes")
    Class<? extends Processor> value();
    
    @ApiStatus.Experimental
    interface Processor<T, S>
    {
        void init(AnnotatedType type);
        
        Class<? super S> getAdapteeClass();
        
        default Processor<T, S> activate()
        {
            return this;
        }
        
        T adapt(S value);
        
        S revert(T value);
        
        @ApiStatus.Experimental
        default boolean isIdentity()
        {
            return false;
        }
        
        default FunctionInvertible<S, T> toFunction()
        {
            return new FunctionInvertible<>(this::adapt, this::revert);
        }
        
        static <T, S> Processor<T, S> of(AnnotatedType type)
        {
            if(type.getDeclaredAnnotation(NoAdapter.class) != null)
            {
                //noinspection unchecked
                return (Processor<T, S>) new Identity<>(type);
            }
            Adapter annot = type.getDeclaredAnnotation(Adapter.class);
            if(annot == null)
            {
                for(Annotation i: type.getDeclaredAnnotations())
                {
                    annot = i.annotationType().getAnnotation(Adapter.class);
                    if(annot != null)
                        break;
                }
            }
            Class<?> clazz = TypeUtil.toClass(type.getType());
            if(annot == null)
                annot = clazz.getDeclaredAnnotation(Adapter.class);
            Processor<T, S> result;
            if(annot != null)
            {
                try
                {
                    //noinspection unchecked
                    result = annot.value().newInstance();
                }
                catch(InstantiationException | IllegalAccessException e)
                {
                    throw RuntimeUtil.sneakilyThrow(e);
                }
            }
            else if(WrapperObject.class.isAssignableFrom(clazz))
            {
                //noinspection unchecked
                result = (Processor<T, S>) new OfWrapper<WrapperObject, S>();
            }
            else
            {
                //noinspection unchecked
                result = (Processor<T, S>) new Identity<>();
            }
            result.init(type);
            return result;
        }
        
        @ApiStatus.Internal
        class OfWrapper<T extends WrapperObject, S> implements Adapter.Processor<T, S>
        {
            WrapperClassData data;
            @Override
            public void init(AnnotatedType type)
            {
                Class<?> clazz = TypeUtil.toClass(type.getType());
                if(!WrapperObject.class.isAssignableFrom(clazz))
                    throw new IllegalArgumentException(type.toString());
                //noinspection unchecked
                this.init((Class<T>) clazz);
            }
            void init(Class<T> type)
            {
                this.data = WrapperClassData.get(type);
            }
            @Override
            public Class<? super S> getAdapteeClass()
            {
                //noinspection unchecked
                return (Class<? super S>) this.data.getWrappedClass();
            }
            @Override
            public Processor<T, S> activate()
            {
                //noinspection unchecked
                return new Activated<>((WrapperFactory<T>) WrapperFactory.of(this.data.getWrapperClass()));
            }
            @Override
            public T adapt(S value)
            {
                throw new IllegalStateException();
            }
            @Override
            public S revert(T value)
            {
                throw new IllegalStateException();
            }
            
            static class Activated<T extends WrapperObject, S> implements Adapter.Processor<T, S>
            {
                WrapperFactory<T> factory;
                public Activated(WrapperFactory<T> factory)
                {
                    this.factory = factory;
                }
                @Override
                public void init(AnnotatedType type)
                {
                    throw new IllegalStateException();
                }
                @Override
                public Class<? super S> getAdapteeClass()
                {
                    //noinspection unchecked
                    return (Class<? super S>) factory.getWrappedClass();
                }
                @Override
                public T adapt(S value)
                {
                    return this.factory.create(value);
                }
                @Override
                public S revert(T value)
                {
                    //noinspection unchecked
                    return (S) value.getWrapped();
                }
            }
        }
        
        @ApiStatus.Internal
        class Identity<T> implements Processor<T, T>
        {
            public Identity()
            {
            }
            public Identity(AnnotatedType type)
            {
                this.init(type);
            }
            public Identity(Class<T> type)
            {
                this.init(type);
            }
            Class<T> type;
            public void init(Class<T> type)
            {
                this.type = type;
            }
            @Override
            public void init(AnnotatedType type)
            {
                //noinspection unchecked
                this.init((Class<T>) TypeUtil.toClass(type.getType()));
            }
            @Override
            public Class<? super T> getAdapteeClass()
            {
                return this.type;
            }
            @Override
            public T adapt(T value)
            {
                return value;
            }
            @Override
            public T revert(T value)
            {
                return value;
            }
            @Override
            public boolean isIdentity()
            {
                return true;
            }
        }
    }
}
