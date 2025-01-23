package mz.mzlib.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public interface ElementSwitcher<T extends Annotation>
{
    boolean isEnabled(T annotation, AnnotatedElement element);

    static boolean isEnabled(AnnotatedElement element)
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
                //noinspection unchecked
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
