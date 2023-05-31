package mz.lib.nothing;

import mz.asm.tree.AbstractInsnNode;
import mz.lib.AsmUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generate bytecode injected manually
 * Injection must be a static method:
 * 1st arg is a {@link NothingMethod} meaning the injected method
 * 2nd arg is a {@link java.util.List}&lt;{@link AbstractInsnNode}> meaning the bytecodes of the method before being injected
 * 3rd arg is a {@link AbstractInsnNode} meaning location of injection, codes should be injected before it
 * Return void
 * @see AsmUtil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface ManualByteCode
{
}
