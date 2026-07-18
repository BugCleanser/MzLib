package mz.mzlib.util.adapter;

import mz.mzlib.util.ListArray;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Array;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Adapter(AdapterArray.Processor.class)
public @interface AdapterArray
{
    class Processor<T, U> implements Adapter.Processor<List<T>, Object>
    {
        Adapter.Processor<List<T>, List<U>> delegate = new AdapterList.Processor<>();
        Class<U> componentType;
        Class<Object> type;
        MethodHandle setter;
        
        @Override
        public void init(AnnotatedType type)
        {
            this.delegate.init(type);
            AnnotatedType[] args = ((AnnotatedParameterizedType)type).getAnnotatedActualTypeArguments();
            //noinspection unchecked
            this.componentType = (Class<U>) Adapter.Processor.<T, U>of(args[0]).getAdapteeClass();
            //noinspection unchecked
            this.type = (Class<Object>) Array.newInstance(this.componentType, 0).getClass();
            this.setter = MethodHandles.arrayElementSetter(this.type).asType(MethodType.methodType(void.class, Object.class, int.class, Object.class));
        }
        
        @Override
        public Adapter.Processor<List<T>, ? super Object> activate()
        {
            this.delegate = this.delegate.activate();
            return this;
        }
        
        @Override
        public Class<Object> getAdapteeClass()
        {
            return this.type;
        }
        
        @Override
        public List<T> adapt(Object value)
        {
            //noinspection unchecked
            return this.delegate.adapt((List<U>) ListArray.of(value));
        }
        
        @Override
        public Object revert(List<T> value)
        {
            List<U> result = this.delegate.revert(value);
            if(result instanceof ListArray)
            {
                ListArray<U> l = (ListArray<U>) result;
                if(l.getArray().getClass() == this.type)
                    return l.getArray();
            }
            Object r = Array.newInstance(this.componentType, result.size());
            int i = 0;
            for(U item: result)
            {
                try
                {
                    this.setter.invokeExact(r, i, item);
                }
                catch(Throwable e)
                {
                    throw new AssertionError(e);
                }
                i++;
            }
            return r;
        }
    }
}
