package mz.mzlib.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;

@Retention(RetentionPolicy.RUNTIME)
@ElementSwitcherClass(JvmVersion.Handler.class)
public @interface JvmVersion
{
    int begin() default 0;
    
    int end() default Integer.MAX_VALUE;
    
    class Handler implements ElementSwitcher<JvmVersion>
    {
        @Override
        public boolean isEnabled(JvmVersion annotation, AnnotatedElement element)
        {
            return RuntimeUtil.jvmVersion>=annotation.begin() && RuntimeUtil.jvmVersion<=annotation.end();
        }
    }
}
