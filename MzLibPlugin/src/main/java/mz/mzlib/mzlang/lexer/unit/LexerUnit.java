package mz.mzlib.mzlang.lexer.unit;

import mz.mzlib.mzlang.SyntaxException;
import mz.mzlib.mzlang.lexer.Token;

public abstract class LexerUnit
{
	protected boolean ended;
	
	public abstract boolean check(char c);
	public abstract void append(char c) throws SyntaxException;
	public boolean isEnded()
	{
		return this.ended;
	}
	
	public abstract Token toToken() throws SyntaxException;
}
