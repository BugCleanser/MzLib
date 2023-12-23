package mz.mzlib.mzlang.ast;

import mz.mzlib.mzlang.CompilingEnvironment;
import mz.mzlib.mzlang.SyntaxException;
import mz.mzlib.mzlang.lexer.Token;
import mz.mzlib.mzlang.lexer.TokenType;
import mz.mzlib.util.CollectionUtil;

import java.util.*;

public class Parser
{
	public CompilingEnvironment environment;
	public Iterator<Token> tokens;
	public Parser(CompilingEnvironment environment,Iterator<Token> tokens)
	{
		this.environment=environment;
		this.tokens=tokens;
	}
	
	public Token lastToken;
	public Token nextToken()
	{
		return lastToken=tokens.next();
	}
	
	Map<String,String> usings=new HashMap<>();
	
	public ASTClassNode apply() throws SyntaxException
	{
		try
		{
			ASTClassNode result=new ASTClassNode();
			while(tokens.hasNext())
			{
				Token keyword=nextToken();
				if(keyword.getType()!=TokenType.KEYWORD)
					throw new SyntaxException();
				switch((String)keyword.getValue())
				{
					case "using":
						List<Token> using=getExpression();
						if(using.isEmpty()||using.get(using.size()-1).getType()!=TokenType.IDENTIFIER)
							throw new SyntaxException("Using: Expect a class name.");
						StringBuilder name=new StringBuilder();
						for(Token t:using)
						{
							if(t.getType()!=TokenType.IDENTIFIER&&!t.equals(new Token(TokenType.OPERATOR,".")))
								throw new SyntaxException("Using: Expect identifier or '.', read "+t);
							name.append(t.getValue());
						}
						usings.put((String)using.get(using.size()-1).getValue(),name.toString());
						break;
					case "extends":
						List<List<Token>> extendings=CollectionUtil.split(getExpression(),new Token(TokenType.OPERATOR,","));
						//TODO
						break;
					case "var":
					case "val":
						//TODO
						break;
					case "fn":
						//TODO
						break;
					default:
						throw new SyntaxException();
				}
			}
			return result;
		}
		catch(SyntaxException e)
		{
			if(lastToken==null)
				throw e;
			else
				throw e.setPosition(lastToken);
		}
	}
	
	public List<Token> getExpression() throws SyntaxException
	{
		List<Token> result=new ArrayList<>();
		while(true)
		{
			if(!tokens.hasNext())
				throw new SyntaxException("Expression must end with ';'");
			Token t=nextToken();
			if(t.equals(new Token(TokenType.OPERATOR,";")))
				break;
			else
				result.add(t);
		}
		return result;
	}
}
