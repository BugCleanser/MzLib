package mz.lib.minecraft.mzlang;

import mz.lib.minecraft.*;
import mz.lib.*;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@Target(METHOD)
public @interface RefactorVersionSign
{
	VersionalName[] value();
}
