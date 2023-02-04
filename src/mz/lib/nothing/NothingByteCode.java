package mz.lib.nothing;

import mz.asm.Opcodes;

import java.lang.annotation.Target;

@Target({})
public @interface NothingByteCode
{
	/**
	 * The index of bytecode matched
	 */
	int index() default 0;
	/**
	 * @see Opcodes
	 */
	int opcode() default -1;
	
	/**
	 * Can be its wrapped class
	 * Enabled when it's a method insn or a field insn
	 */
	Class<?> owner() default void.class;
	/**
	 * Enabled when it's a method insn or a field insn
	 */
	String[] name() default {};
	/**
	 * Can be its wrapped class
	 * Enabled when it's a method insn
	 */
	Class<?>[] methodArgs() default void.class;
	/**
	 * Enabled when it's a var insn
	 */
	int var() default -1;
	/**
	 * Enabled when it's a label insn or a jump insn
	 */
	int label() default -1;
}
