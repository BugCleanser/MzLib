package mz.mzlib.mzlang.lexer.unit;

import mz.mzlib.mzlang.SyntaxException;
import mz.mzlib.mzlang.lexer.Token;
import mz.mzlib.mzlang.lexer.TokenType;

public class LexerCharUnit extends LexerStringUnit
{
	public LexerCharUnit()
	{
	}
	
	@Override
	public boolean check(char c)
	{
		return super.check(c);
	}
	
	@Override
	public void append(char c) throws SyntaxException
	{
		if(this.escaping==0&&c=='\'')
			this.ended=!this.isEnded();
		else if(this.escaping==0&&c=='"')
			this.sb.append('"');
		else
			super.append(c);
	}
	
	@Override
	public Token toToken() throws SyntaxException
	{
		Token res=super.toToken();
		if(res.getValue().toString().length()!=1)
			throw new SyntaxException("Illegal characters num");
		return new Token(TokenType.LITERAL,res.getValue().toString().charAt(0));
	}
}
