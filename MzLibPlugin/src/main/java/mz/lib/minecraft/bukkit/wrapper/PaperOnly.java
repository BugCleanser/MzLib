package mz.lib.minecraft.bukkit.wrapper;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@Target(TYPE)
public @interface PaperOnly
{
}
