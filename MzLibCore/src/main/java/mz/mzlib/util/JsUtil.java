package mz.mzlib.util;

import org.mozilla.javascript.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsUtil
{
    public static Object newObject(Object scope)
    {
        NativeObject result = new NativeObject();
        ScriptRuntime.setBuiltinProtoAndParent(result, (Scriptable)scope, TopLevel.Builtins.Object);
        return result;
    }
    
    public static Object initSafeStandardObjects()
    {
        try(Context context = Context.enter())
        {
            return context.initSafeStandardObjects();
        }
    }
    
    public static Object initStandardObjects()
    {
        try(Context context = Context.enter())
        {
            return context.initStandardObjects();
        }
    }
    
    public static Object eval(Object scope, String script)
    {
        try(Context context = Context.enter())
        {
            return context.evaluateString((Scriptable)scope, script, "js", 1, null);
        }
    }
    
    public static Object mapToObject(Object scope, Map<String, Object> map)
    {
        Scriptable result = (Scriptable)newObject(scope);
        for(Map.Entry<String, Object> e: map.entrySet())
        {
            result.put(e.getKey(), result, e.getValue());
        }
        return result;
    }
    
    public static Object toJvm(Object obj)
    {
        if(!(obj instanceof Scriptable))
            return obj;
        if(obj instanceof Wrapper)
            return ((Wrapper)obj).unwrap();
        else if(obj instanceof NativeArray)
            return RuntimeUtil.<List<Object>>cast(obj).stream().map(JsUtil::toJvm).collect(Collectors.toList());
        else if(obj instanceof NativeObject)
            return RuntimeUtil.<Map<String, Object>>cast(obj).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> toJvm(e.getValue())));
        else if(obj instanceof NativeCall)
            return null;
        else
            throw new IllegalArgumentException(obj.toString());
    }
}
