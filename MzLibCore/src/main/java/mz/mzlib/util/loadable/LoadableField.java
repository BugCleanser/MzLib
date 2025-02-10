package mz.mzlib.util.loadable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LoadableField {
    String name() default "";
    String path() default "";
    Class<? extends LoadableSerializer> loadableSerializer() default LoadableSerializer.MundaneSerializer.class;
}
