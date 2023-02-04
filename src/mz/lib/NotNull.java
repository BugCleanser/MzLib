package mz.lib;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD,ElementType.LOCAL_VARIABLE,ElementType.METHOD,ElementType.PARAMETER})
@Inherited
public @interface NotNull
{

}
