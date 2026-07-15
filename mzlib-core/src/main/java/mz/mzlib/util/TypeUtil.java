package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypeUtil
{
    public static Class<?> toClass(Type type)
    {
        if(type instanceof Class)
            return (Class<?>) type;
        else if(type instanceof ParameterizedType)
        {
            type = ((ParameterizedType)type).getRawType();
            if(!(type instanceof Class))
                throw new IllegalArgumentException(type.toString());
            return (Class<?>) type;
        }
        else if(type instanceof GenericArrayType)
            return ClassUtil.arrayClass(toClass(((GenericArrayType) type).getGenericComponentType()));
        else if(type instanceof WildcardType)
        {
            Type[] bounds = ((WildcardType) type).getUpperBounds();
            if(bounds.length == 0)
                throw new IllegalArgumentException(type.toString());
            return toClass(bounds[0]);
        }
        else if(type instanceof TypeVariable)
        {
            Type[] bounds = ((TypeVariable<?>) type).getBounds();
            if(bounds.length == 0)
                throw new IllegalArgumentException(type.toString());
            return toClass(bounds[0]);
        }
        else
            throw new UnsupportedOperationException(type.toString());
    }
    
    @ApiStatus.Experimental
    public static ParameterizedType parameterizedType(Type rawType, @Nullable Type ownerType, Type ...actualTypeArguments)
    {
        return new ParameterizedType()
        {
            @Override
            public Type[] getActualTypeArguments()
            {
                return actualTypeArguments;
            }
            @Override
            public Type getRawType()
            {
                return rawType;
            }
            @Override
            public @Nullable Type getOwnerType()
            {
                return ownerType;
            }
            // TODO: equals ...
        };
    }
    
    @ApiStatus.Experimental
    public static AnnotatedType replace(AnnotatedType type, TypeVariable<?> var, AnnotatedType value)
    {
        throw new UnsupportedOperationException("TODO"); // TODO
    }
    
    @ApiStatus.Experimental
    public static AnnotatedType @Nullable [] resolveTypeArguments(AnnotatedType type, Class<?> generic)
    {
        AnnotatedType @Nullable [] result;
        if(type instanceof AnnotatedParameterizedType)
        {
            AnnotatedParameterizedType pt = (AnnotatedParameterizedType) type;
            Class<?> rawType = (Class<?>)((ParameterizedType)pt.getType()).getRawType();
            AnnotatedType[] args = pt.getAnnotatedActualTypeArguments();
            if(rawType == generic)
                return args;
            List<AnnotatedType> parents = new ArrayList<>();
            if(generic.isInterface())
                parents.addAll(Arrays.asList(rawType.getAnnotatedInterfaces()));
            AnnotatedType st = rawType.getAnnotatedSuperclass();
            if(st != null)
                parents.add(st);
            for(AnnotatedType it: parents)
            {
                for(int i = 0; i < args.length; i++)
                {
                    TypeVariable<? extends Class<?>>[] params = rawType.getTypeParameters();
                    it = replace(it, params[i], args[i]);
                }
                result = resolveTypeArguments(it, generic);
                if(result != null)
                    return result;
            }
        }
        else if(type.getType() instanceof Class<?>)
        {
            Class<?> c = (Class<?>) type.getType();
            if(generic.isInterface())
            {
                for(AnnotatedType i: c.getAnnotatedInterfaces())
                {
                    result = resolveTypeArguments(i, generic);
                    if(result != null)
                        return result;
                }
            }
            AnnotatedType st = c.getAnnotatedSuperclass();
            if(st != null)
            {
                result = resolveTypeArguments(st, generic);
                if(result != null)
                    return result;
            }
        }
        return null;
    }
}
