package mz.mzlib.mzlang.lexer;

import java.util.List;

public class BracketBlock
{
	public char leftBracket;
	public List<Token> content;
	
	public BracketBlock(char leftBracket,List<Token> content)
	{
		this.leftBracket=leftBracket;
		this.content=content;
	}
	
	@Override
	public String toString()
	{
		return "BracketExpression{"+"leftBracket="+leftBracket+", content="+content+'}';
	}
}
