package mz.lib.nothing;

public enum NothingLocation
{
	/**
	 * At the beginning of the method
	 * After super(...); for constructor(&lt;init>)
	 */
	FRONT,
	/**
	 * With FJInject.byteCode()
	 * @see NothingInject
	 */
	BYTECODE,
	/**
	 * Inject before every return bytecode(including IRETURN, FRETURN, etc.)
	 * @see StackTop
	 */
	RETURN,
	/**
	 * Call when an anomaly occurs
	 * It will add a try-catch(Throwable) block
	 * FJInject.shift() is unavailable
	 * @see CaughtValue
	 */
	CATCH
}
