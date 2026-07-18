package mz.mzlib.util.adapter;

import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.TypeUtil;
import mz.mzlib.util.proxy.MapProxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Adapter(AdapterMap.Processor.class)
public @interface AdapterMap
{
    class Processor<K, V, K1, V1> implements Adapter.Processor<Map<K, V>, Map<K1, V1>>
    {
        Adapter.Processor<K, K1> delegateKey;
        Adapter.Processor<V, V1> delegateValue;
        Class<? super K> keyType;
        Class<? super V> valueType;
        @Override
        public void init(AnnotatedType type)
        {
            if(TypeUtil.toClass(type.getType()) != Map.class)
                throw new IllegalArgumentException("Must be Map: "+type);
            if(!(type instanceof AnnotatedParameterizedType))
                throw new IllegalArgumentException("The Map has no type args:" + type);
            AnnotatedType[] args = ((AnnotatedParameterizedType) type).getAnnotatedActualTypeArguments();
            this.delegateKey = Adapter.Processor.of(args[0]);
            this.delegateValue = Adapter.Processor.of(args[1]);
            //noinspection unchecked
            this.keyType = (Class<? super K>) TypeUtil.toClass(args[0].getType());
            //noinspection unchecked
            this.valueType = (Class<? super V>) TypeUtil.toClass(args[1].getType());
        }
        @Override
        public Adapter.Processor<Map<K, V>, Map<K1, V1>> activate()
        {
            return new Activated<>(this.keyType, this.valueType, this.delegateKey.activate(), this.delegateValue.activate());
        }
        @Override
        public Class<? super Map<K1, V1>> getAdapteeClass()
        {
            return Map.class;
        }
        @Override
        public Map<K, V> adapt(Map<K1, V1> value)
        {
            throw new IllegalStateException();
        }
        @Override
        public Map<K1, V1> revert(Map<K, V> value)
        {
            throw new IllegalStateException();
        }
        
        static class Activated<K, V, K1, V1> implements Adapter.Processor<Map<K, V>, Map<K1, V1>>
        {
            Class<? super K> keyType;
            Class<? super V> valueType;
            Class<? super K1> key1Type;
            Class<? super V1> value1Type;
            FunctionInvertible<K1, K> functionKey;
            FunctionInvertible<K, K1> functionKeyInverse;
            FunctionInvertible<V1, V> functionValue;
            FunctionInvertible<V, V1> functionValueInverse;
            public Activated(Class<? super K> keyType, Class<? super V> valueType, Adapter.Processor<K, K1> delegateKey, Adapter.Processor<V, V1> delegateValue)
            {
                this.keyType = keyType;
                this.valueType = valueType;
                
                this.key1Type = delegateKey.getAdapteeClass();
                this.functionKey = delegateKey.toFunction();
                this.functionKeyInverse = this.functionKey.inverse();
                
                this.value1Type = delegateValue.getAdapteeClass();
                this.functionValue = delegateValue.toFunction();
                this.functionValueInverse = this.functionValue.inverse();
            }
            
            @Override
            public void init(AnnotatedType type)
            {
                throw new IllegalStateException();
            }
            
            @Override
            public Class<? super Map<K1, V1>> getAdapteeClass()
            {
                return Map.class;
            }
            
            @Override
            public Map<K, V> adapt(Map<K1, V1> value)
            {
                if(value instanceof MapProxy)
                {
                    MapProxy<?, ?, ?, ?> it = (MapProxy<?, ?, ?, ?>) value;
                    if(it.getFunctionKey().equals(this.functionKeyInverse) && it.getFunctionValue().equals(this.functionValueInverse))
                    {
                        //noinspection unchecked
                        return (Map<K, V>) it.getDelegate();
                    }
                }
                return new MapProxy<>(value, this.functionKey, this.functionValue, this.keyType, this.valueType);
            }
            @Override
            public Map<K1, V1> revert(Map<K, V> value)
            {
                if(value instanceof MapProxy)
                {
                    MapProxy<?, ?, ?, ?> it = (MapProxy<?, ?, ?, ?>)value;
                    if(it.getFunctionKey().equals(this.functionKey) && it.getFunctionValue().equals(this.functionValue))
                    {
                        //noinspection unchecked
                        return (Map<K1, V1>) it.getDelegate();
                    }
                }
                return new MapProxy<>(value, this.functionKeyInverse, this.functionValueInverse, this.key1Type, this.value1Type);
            }
        }
    }
}
