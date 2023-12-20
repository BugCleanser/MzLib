package mz.mzlib.mzlang.lexer;

import mz.mzlib.mzlang.SyntaxException;
import mz.mzlib.mzlang.lexer.unit.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class Lexer
{
	private final StringReader reader;
	
	public Lexer(String input)
	{
		this(new StringReader(input));
	}
	public Lexer(StringReader reader)
	{
		this.reader=reader;
	}
	
	public LexerUnit current;
	public List<Token> apply() throws IOException,SyntaxException
	{
		List<Token> tokens=new ArrayList<>();
		int c;
		int lineNum=0,columnNum=0;
		while((c=reader.read())!=-1)
		{
			if(c=='\n')
			{
				lineNum++;
				columnNum=0;
			}
			else
				columnNum++;
			if(current!=null)
			{
				if(current.check((char)c))
				{
					current.append((char)c);
					if(current.isEnded())
					{
						tokens.add(current.toToken().setPosition(lineNum,columnNum));
						current=null;
					}
					continue;
				}
				else
				{
					tokens.add(current.toToken().setPosition(lineNum,columnNum));
					current=null;
				}
			}
			switch(c)
			{
				case '\'':
					current=new LexerCharUnit();
					break;
				case '\"':
					current=new LexerStringUnit();
					break;
				default:
					if(c>='0' && c<='9')
						current=new LexerNumberUnit();
					else if(c=='_' || c>='a' && c<='z' || c>='A' && c<='Z')
						current=new LexerIdentifierUnit();
					else if(LexerOperatorUnit.operatorChars.contains((char)c))
						current=new LexerOperatorUnit();
					else if(!Character.isWhitespace(c))
						throw new SyntaxException(Character.toString((char)c));
			}
			if(current!=null)
				current.append((char)c);
		}
		if(current!=null)
			tokens.add(current.toToken().setPosition(lineNum,columnNum));
		return tokens;
	}
}
