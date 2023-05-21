package mz.mzlib.javautil.delegator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DelegationMethod
{
	/**
	 * @return Possible names
	 */
	String[] value();
}
