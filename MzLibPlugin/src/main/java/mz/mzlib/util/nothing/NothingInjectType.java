package mz.mzlib.util.nothing;

public enum NothingInjectType
{
	/**
	 * You handle the bytecode yourself. <br/>
	 * This option is generally not used.
	*/
	RAW,
	/**
	 * Insert before the located insns.
	 */
	INSERT_BEFORE,
	/**
	 * Skip some insns beginning with the located insns.
	 */
	SKIP,
	/**
	 * Try some insns beginning with the located insns, <br/>
	 * and catch exception by your handler
	 */
	CATCH
}
