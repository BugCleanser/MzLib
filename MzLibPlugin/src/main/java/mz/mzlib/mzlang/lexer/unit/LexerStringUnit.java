package mz.mzlib.mzlang.lexer.unit;

import mz.mzlib.mzlang.SyntaxException;
import mz.mzlib.mzlang.lexer.Token;
import mz.mzlib.mzlang.lexer.TokenType;

public class LexerStringUnit extends LexerUnit
{
	public StringBuilder sb;
	
	public LexerStringUnit()
	{
		this.sb=new StringBuilder();
		this.ended=true;
	}
	
	@Override
	public boolean check(char c)
	{
		if(this.isEnded())
			return false;
		return c!='\n';
	}
	
	public int escaping=0;
	public byte[] unicode=new byte[4];
	@Override
	public void append(char c) throws SyntaxException
	{
		if(this.escaping==1)
		{
			this.escaping=0;
			switch(c)
			{
				case '0':
					sb.append('\0');
					break;
				case 't':
					sb.append('\t');
					break;
				case 'r':
					sb.append('\r');
					break;
				case 'n':
					sb.append('\n');
					break;
				case 'b':
					sb.append('\b');
					break;
				case 'f':
					sb.append('\f');
					break;
				case '\'':
					sb.append('\'');
					break;
				case '\"':
					sb.append('\"');
					break;
				case '\\':
					sb.append('\\');
					break;
				case 'u':
					this.escaping=2;
					break;
				default:
					throw new SyntaxException("Unsupported escaping: "+c);
			}
		}
		else if(this.escaping>1)
		{
			try
			{
				unicode[this.escaping-2]=Byte.parseByte(Character.toString(c),16);
				this.escaping++;
				if(this.escaping-2==4)
				{
					sb.append((char)(unicode[0]<<12|unicode[0]<<8|unicode[0]<<4|unicode[0]));
					this.escaping=0;
				}
			}
			catch(NumberFormatException e)
			{
				throw new SyntaxException(e);
			}
		}
		else if(c=='\"')
			this.ended=!this.isEnded();
		else if(c=='\\')
			this.escaping=1;
		else
			sb.append(c);
	}
	
	@Override
	public Token toToken() throws SyntaxException
	{
		if(!this.isEnded())
			throw new SyntaxException("String literal must end with '\\\"': "+sb.toString());
		return new Token(TokenType.LITERAL,sb.toString());
	}
}
