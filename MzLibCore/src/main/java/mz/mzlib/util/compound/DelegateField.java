package mz.mzlib.util.compound;

import mz.mzlib.util.wrapper.WrappedMemberFinder;
import mz.mzlib.util.wrapper.WrappedMemberFinderClass;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * Declare a delegate in the compound
 * <p>
 * Only use on the field setter
 * <p>
 * The param type of setter is the delegate class, and all the methods of the class will be delegated to it.
 * <p>
 * For getter, see {@link mz.mzlib.util.wrapper.WrapFieldAccessor}
 *
 * @see Compound
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(DelegateField.Handler.class)
public @interface DelegateField
{
    /**
     * Name of the delegate field
     */
    String value() default "delegate";

    /**
     * For the same method, the one with higher priority will be delegated
     */
    float priority() default 0;

    class Handler implements WrappedMemberFinder<DelegateField>
    {
        @Override
        public Member find(
            Class<? extends WrapperObject> wrapperClass,
            Class<?> wrappedClass,
            Method wrapperMethod,
            DelegateField annotation,
            Class<?> returnType,
            Class<?>[] argTypes) throws NoSuchFieldException
        {
            return wrappedClass.getDeclaredField(annotation.value());
        }
    }
}
