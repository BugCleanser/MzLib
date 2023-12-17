package mz.mzlib.mzlang.lexer.unit;

import mz.mzlib.mzlang.SyntaxException;
import mz.mzlib.mzlang.lexer.Token;
import mz.mzlib.mzlang.lexer.TokenType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LexerIdentifierUnit extends LexerUnit
{
	public StringBuilder sb=new StringBuilder();
	
	@Override
	public boolean check(char c)
	{
		return c>='a'&&c<='z' || c>='A'&&c<='Z' || c>='0'&&c<='9' || c=='_';
	}
	
	@Override
	public void append(char c) throws SyntaxException
	{
		sb.append(c);
	}
	
	public static Set<String> keywords=new HashSet<>(Arrays.asList(
			"class",
			"new",
			"extends"
	));
	@Override
	public Token toToken() throws SyntaxException
	{
		String s=sb.toString();
		switch(s)
		{
			case "true":
				return new Token(TokenType.LITERAL,true);
			case "false":
				return new Token(TokenType.LITERAL,false);
			default:
				if(keywords.contains(s))
					return new Token(TokenType.KEYWORD,s);
				return new Token(TokenType.IDENTIFIER,s);
		}
	}
}
