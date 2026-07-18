package mz.mzlib.util;

import mz.mzlib.util.asm.AsmUtil;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Executable;
import java.util.Arrays;

@ApiStatus.Experimental
public class MethodKey
{
    String name;
    Class<?>[] parameterTypes;
    
    public MethodKey(String name, Class<?> ...parameterTypes)
    {
        this.name = name;
        this.parameterTypes = parameterTypes;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public Class<?>[] getParameterTypes()
    {
        return this.parameterTypes;
    }
    
    public static MethodKey of(Executable member)
    {
        return new MethodKey(AsmUtil.getName(member), member.getParameterTypes());
    }
    
    public boolean isConstructor()
    {
        return this.name.equals("<init>");
    }
    
    public Executable getMember(Class<?> clazz) throws NoSuchMethodException
    {
        if(this.isConstructor())
            return clazz.getConstructor(this.getParameterTypes());
        else
            return clazz.getMethod(this.getName(), this.getParameterTypes());
    }
    
    public Executable getDeclaredMember(Class<?> clazz) throws NoSuchMethodException
    {
        if(this.isConstructor())
            return clazz.getDeclaredConstructor(this.getParameterTypes());
        else
            return clazz.getDeclaredMethod(this.getName(), this.getParameterTypes());
    }
    
    @Override
    public int hashCode()
    {
        return this.name.hashCode() * 31 + Arrays.hashCode(this.parameterTypes);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof MethodKey))
            return false;
        MethodKey other = (MethodKey) obj;
        return this.name.equals(other.name) && Arrays.equals(this.parameterTypes, other.parameterTypes);
    }
    
    @Override
    public String toString()
    {
        return this.name + "(" + String.join(", ", Arrays.stream(this.parameterTypes).map(Class::getTypeName).toArray(String[]::new)) + ")";
    }
}
