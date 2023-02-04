package mz.lib.wrapper;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Acquiescently, wrapped method or field accessor will try to access the raw method or field directly if the raw method or field is public,
 * It's only accessed by MethodHandle when it's not public.
 *
 * In some cases, such as in different modules, the raw field or method can not be accessed, even though it's public
 * You can add this annotation to make raw field or method accessed by MethodHandle
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessByForce
{
}
