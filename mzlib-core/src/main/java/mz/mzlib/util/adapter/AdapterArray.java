package mz.mzlib.util.adapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Adapter(AdapterArray.Processor.class)
public @interface AdapterArray
{
    class Processor<T, U> implements Adapter.Processor<List<T>, U[]>
    {
        Adapter.Processor<List<T>, List<U>> delegate = new AdapterList.Processor<>();
        Class<U> componentType;
        Class<U[]> type;
        
        @Override
        public void init(AnnotatedType type)
        {
            this.delegate.init(type);
            AnnotatedType[] args = ((AnnotatedParameterizedType)type).getAnnotatedActualTypeArguments();
            //noinspection unchecked
            this.componentType = (Class<U>) Adapter.Processor.<T, U>of(args[0]).getSourceClass();
            //noinspection unchecked
            this.type = (Class<U[]>) Array.newInstance(this.componentType, 0).getClass();
        }
        @Override
        public Adapter.Processor<List<T>, U[]> activate()
        {
            this.delegate = this.delegate.activate();
            return this;
        }
        @Override
        public Class<? super U[]> getSourceClass()
        {
            return this.type;
        }
        @Override
        public List<T> adapt(U[] value)
        {
            return this.delegate.adapt(Arrays.asList(value));
        }
        @Override
        public U[] revert(List<T> value)
        {
            // TODO: optimize
            List<U> result = this.delegate.revert(value);
            //noinspection unchecked
            return result.toArray((U[]) Array.newInstance(this.componentType, result.size()));
        }
    }
}
