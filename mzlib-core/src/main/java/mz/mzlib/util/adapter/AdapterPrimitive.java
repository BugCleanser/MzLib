package mz.mzlib.util.adapter;

import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.TypeUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Adapter(AdapterPrimitive.Processor.class)
public @interface AdapterPrimitive
{
    class Processor<T> implements Adapter.Processor<T, T>
    {
        Class<T> type;
        @Override
        public void init(AnnotatedType type)
        {
            Class<?> clazz = ClassUtil.getPrimitive(TypeUtil.toClass(type.getType()));
            if(!clazz.isPrimitive())
                throw new IllegalArgumentException();
            //noinspection unchecked
            this.type = (Class<T>) clazz;
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
    }
}
