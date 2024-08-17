package mz.mzlib.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public abstract class ElementSwitcher
{
    public abstract boolean isEnabled(Annotation annotation, AnnotatedElement element);

    public static boolean isEnabled(AnnotatedElement element)
    {
        try
        {
            for (Annotation a : element.getAnnotations())
            {
                ElementSwitcherClass clazz = a.annotationType().getAnnotation(ElementSwitcherClass.class);
                if (clazz == null)
                {
                    continue;
                }
                if (!isEnabled(a.annotationType()))
                {
                    continue;
                }
                if (!clazz.value().newInstance().isEnabled(a, element))
                {
                    return false;
                }
            }
            return true;
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
