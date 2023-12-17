package mz.mzlib.mzlang;

public class SyntaxException extends Throwable
{
	public SyntaxException()
	{
		super();
	}
	public SyntaxException(String msg)
	{
		super(msg);
	}
	public SyntaxException(Throwable cause)
	{
		super(cause);
	}
}
