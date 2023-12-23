package mz.mzlib.mzlang;

import mz.mzlib.mzlang.lexer.Token;

public class SyntaxException extends Throwable
{
	int lineNum=0,columnNum=0;
	
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
	
	public SyntaxException setPosition(int lineNum,int columnNum)
	{
		this.lineNum=lineNum;
		this.columnNum=columnNum;
		return this;
	}
	public SyntaxException setPosition(Token token)
	{
		return this.setPosition(token.lineNum,token.columnNum);
	}
	
	@Override
	public String toString()
	{
		return "SyntaxException{"+"lineNum="+lineNum+", columnNum="+columnNum+'}';
	}
}
