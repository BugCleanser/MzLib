package mz.lib.minecraft.command.argparser;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface SenderInfo
{
	String typeError();
}
