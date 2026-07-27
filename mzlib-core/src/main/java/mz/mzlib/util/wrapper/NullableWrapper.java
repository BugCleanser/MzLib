package mz.mzlib.util.wrapper;

import mz.mzlib.util.adapter.Adapter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedType;

@ApiStatus.Experimental
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Adapter(NullableWrapper.Processor.class)
public @interface NullableWrapper
{
    class Processor<T extends WrapperObject, S> implements Adapter.Processor<@Nullable T, @Nullable S>
    {
        Adapter.Processor<T, S> delegate = new OfWrapper<>();
        
        @Override
        public void init(AnnotatedType type)
        {
            this.delegate.init(type);
        }
        @Override
        public Class<? super S> getAdapteeClass()
        {
            return this.delegate.getAdapteeClass();
        }
        @Override
        public Adapter.Processor<@Nullable T, @Nullable S> activate()
        {
            this.delegate = this.delegate.activate();
            return this;
        }
        @Override
        public @Nullable T adapt(@Nullable S value)
        {
            if(value == null)
                return null;
            return this.delegate.adapt(value);
        }
        @Override
        public @Nullable S revert(@Nullable T value)
        {
            if(value == null)
                return null;
            return this.delegate.revert(value);
        }
    }
}
