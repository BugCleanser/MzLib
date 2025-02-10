package mz.mzlib.minecraft;

import mz.mzlib.minecraft.mappings.MappingMethod;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.nothing.StackTop;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperObject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.AbstractLogger;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// TODO
public class ModuleMapStackTrace extends MzModule
{
    public static ModuleMapStackTrace instance = new ModuleMapStackTrace();
    
    @Override
    public void onLoad()
    {
        this.register(NothingThrowable.class);
        this.register(NothingAbstractLogger.class);
    }
    
    @WrapClass(AbstractLogger.class)
    public interface NothingAbstractLogger extends Nothing
    {
        @WrapMethod("logMessageSafely")
        void logMessageSafely(String fqcn, Level level, Marker marker, Message msg, Throwable throwable);
        
        @NothingInject(wrapperMethodName="logMessageSafely", wrapperMethodParams={String.class, Level.class, Marker.class, Message.class, Throwable.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default void logMessageSafelyBegin()
        {
            NothingThrowable.isStackTracePrinting.set(true);
        }
        
        @NothingInject(wrapperMethodName="logMessageSafely", wrapperMethodParams={String.class, Level.class, Marker.class, Message.class, Throwable.class}, locateMethod="locateAllReturn", type=NothingInjectType.INSERT_BEFORE)
        default void logMessageSafelyEnd()
        {
            NothingThrowable.isStackTracePrinting.remove();
        }
    }
    
    @WrapClass(Throwable.class)
    public interface NothingThrowable extends Nothing
    {
        @Override
        Throwable getWrapped();
        
        @WrapMethod("printStackTrace")
        void printStackTrace(PrintStream s);
        
        @WrapMethod("getOurStackTrace")
        StackTraceElement[] getOurStackTrace();
        
        ThreadLocal<Boolean> isStackTracePrinting = new ThreadLocal<>();
        
        @NothingInject(wrapperMethodName="printStackTrace", wrapperMethodParams={PrintStream.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default void printStackTraceBegin()
        {
            isStackTracePrinting.set(true);
        }
        
        @NothingInject(wrapperMethodName="printStackTrace", wrapperMethodParams={PrintStream.class}, locateMethod="locateAllReturn", type=NothingInjectType.INSERT_BEFORE)
        default void printStackTraceEnd()
        {
            isStackTracePrinting.remove();
        }
        
        @NothingInject(wrapperMethodName="getOurStackTrace", wrapperMethodParams={}, locateMethod="locateAllReturn", type=NothingInjectType.INSERT_BEFORE)
        default WrapperObject getOurStackTraceEnd(@StackTop StackTraceElement[] returnValue)
        {
            if(!Boolean.TRUE.equals(isStackTracePrinting.get()))
                return Nothing.notReturn();
            StackTraceElement[] result = new StackTraceElement[returnValue.length];
            for(int i = 0; i<result.length; i++)
            {
                String name = MinecraftPlatform.instance.getMappings().mapClass0(returnValue[i].getClassName());
                if(name!=null)
                {
                    String methodName = returnValue[i].getMethodName();
                    if(!Objects.equals(methodName, "<init>") && !Objects.equals(methodName, "<clinit>"))
                    {
                        try
                        {
                            String finalMethodName = methodName;
                            Set<Method> methods = Arrays.stream(Class.forName(returnValue[i].getClassName()).getDeclaredMethods()).filter(m->m.getName().equals(finalMethodName)).collect(Collectors.toSet());
                            if(methods.size()!=1)
                                throw new NoSuchMethodException();
                            Method method = methods.iterator().next();
                            methodName = MinecraftPlatform.instance.getMappings().mapMethod(returnValue[i].getClassName(), new MappingMethod(method.getName(), Arrays.stream(method.getParameterTypes()).map(AsmUtil::getType).toArray(String[]::new)));
                        }
                        catch(ClassNotFoundException|NoSuchMethodException e)
                        {
                            methodName = "?"+methodName;
                        }
                    }
                    result[i] = new StackTraceElement(name, methodName, returnValue[i].getFileName(), returnValue[i].getLineNumber());
                }
                else
                    result[i] = returnValue[i];
            }
            return WrapperObject.create(result);
        }
    }
}
