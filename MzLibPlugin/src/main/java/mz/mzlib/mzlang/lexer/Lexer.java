package mz.mzlib.mzlang.lexer;

import mz.mzlib.mzlang.SyntaxException;
import mz.mzlib.mzlang.lexer.unit.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

public class Lexer
{
	public static Map<Character,Character> brackets=new HashMap<>();
	static
	{
		brackets.put('(',')');
		brackets.put('[',']');
		brackets.put('{','}');
	}
	
	private final Reader reader;
	
	public Lexer(String input)
	{
		this(new StringReader(input));
	}
	public Lexer(Reader reader)
	{
		this.reader=reader;
	}
	
	public LexerUnit current;
	public List<Token> apply() throws IOException,SyntaxException
	{
		return apply('\0');
	}
	public List<Token> apply(char end) throws IOException,SyntaxException
	{
		List<Token> tokens=new ArrayList<>();
		int c;
		int lineNum=0,columnNum=0;
		try
		{
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
				if(c==end)
					break;
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
						else if(brackets.containsKey((char)c))
							tokens.add(new Token(TokenType.BRACKET_BLOCK,new BracketBlock((char)c,apply(brackets.get((char)c)))));
						else if(!Character.isWhitespace(c))
							throw new SyntaxException(Character.toString((char)c));
				}
				if(current!=null)
					current.append((char)c);
			}
			if(end!='\0'&&c!=end)
				throw new SyntaxException("Missing right bracket: "+end);
			if(current!=null)
				tokens.add(current.toToken().setPosition(lineNum,columnNum));
		}
		catch(SyntaxException e)
		{
			throw e.setPosition(lineNum,columnNum);
		}
		return tokens;
	}
}
