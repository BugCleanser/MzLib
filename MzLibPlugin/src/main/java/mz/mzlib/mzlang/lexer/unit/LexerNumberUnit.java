package mz.mzlib.mzlang.lexer.unit;

import mz.mzlib.mzlang.SyntaxException;
import mz.mzlib.mzlang.lexer.Token;
import mz.mzlib.mzlang.lexer.TokenType;

public class LexerNumberUnit extends LexerUnit
{
	public char type='\0';
	public StringBuilder sb=new StringBuilder();
	
	@Override
	public boolean check(char c)
	{
		return c>='0'&&c<='9' || c>='a'&&c<='z' || c>='A'&&c<='Z';
	}
	
	@Override
	public void append(char c) throws SyntaxException
	{
		if(type!='\0')
			throw new SyntaxException();
		switch(c)
		{
			case 'f':
			case 'F':
				if(sb.length()>=2 && sb.charAt(0)=='0' && Character.toLowerCase(sb.charAt(1))=='x')
				{
					sb.append(c);
					break;
				}
			case 'l':
			case 'L':
			case 'b':
			case 'B':
			case 's':
			case 'S':
				type=Character.toLowerCase(c);
				break;
			default:
				sb.append(c);
				break;
		}
	}
	
	@Override
	public Token toToken() throws SyntaxException
	{
		try
		{
			int radix=10;
			String s=sb.toString();
			if(s.startsWith("0x") || s.startsWith("0X"))
			{
				radix=16;
				s=s.substring(2);
			}
			if(s.startsWith("0b") || s.startsWith("0B"))
			{
				radix=2;
				s=s.substring(2);
			}
			else if(s.startsWith("0"))
			{
				radix=8;
				s=s.substring(1);
			}
			switch(type)
			{
				case 'b':
					return new Token(TokenType.LITERAL,-Byte.valueOf('-'+s,radix));
				case 's':
					return new Token(TokenType.LITERAL,-Short.valueOf('-'+s,radix));
				case 'l':
					return new Token(TokenType.LITERAL,-Long.valueOf('-'+s,radix));
				case 'f':
					if(radix!=8 && radix!=10)
						throw new SyntaxException();
					return new Token(TokenType.LITERAL,-Float.parseFloat('-'+s));
				default:
					if(s.contains(".") || s.contains("E") || s.contains("e"))
					{
						if(radix!=8 && radix!=10)
							throw new SyntaxException();
						return new Token(TokenType.LITERAL,-Double.parseDouble('-'+s));
					}
					else
						return new Token(TokenType.LITERAL,-Integer.valueOf('-'+s,radix));
			}
		}
		catch(NumberFormatException e)
		{
			throw new SyntaxException(e);
		}
	}
}
