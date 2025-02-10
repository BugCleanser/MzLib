package mz.mzlib.util.placeholder.primitive;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PlaceholderParseable {
    String[] params() default {};
}
