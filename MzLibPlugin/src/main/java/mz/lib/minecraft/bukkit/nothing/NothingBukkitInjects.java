package mz.lib.minecraft.bukkit.nothing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface NothingBukkitInjects
{
	NothingBukkitInject[] value();
}
