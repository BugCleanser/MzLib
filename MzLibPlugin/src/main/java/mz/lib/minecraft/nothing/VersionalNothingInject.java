package mz.lib.minecraft.nothing;

import mz.lib.minecraft.VersionalName;
import mz.lib.nothing.NothingLocation;
import mz.lib.nothing.NothingPriority;
import mz.lib.wrapper.WrappedObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
@Repeatable(VersionalNothingInjects.class)
public @interface VersionalNothingInject
{
	NothingPriority priority() default NothingPriority.NORMAL;
	
	/**
	 * All possible names of target method
	 * Only one method can be matched
	 */
	VersionalName[] name();
	/**
	 * Args types for matching method
	 * @see WrappedObject
	 */
	Class<?>[] args();
	NothingLocation location();
	
	/**
	 * @see NothingLocation
	 */
	VersionalNothingByteCode byteCode() default @VersionalNothingByteCode;
	/**
	 * For example, 1 means the next bytecode
	 */
	int shift() default 0;
	
	/**
	 * @see mz.lib.Optional
	 */
	boolean optional() default false;
}
