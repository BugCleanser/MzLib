package mz.lib.minecraft.bukkitlegacy.command.argparser;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface SenderInfo
{
	String typeError();
}
