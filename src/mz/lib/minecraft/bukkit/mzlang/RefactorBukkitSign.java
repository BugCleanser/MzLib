package mz.lib.minecraft.bukkit.mzlang;

import mz.lib.minecraft.bukkit.*;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@Target(METHOD)
public @interface RefactorBukkitSign
{
	VersionName[] value();
}
