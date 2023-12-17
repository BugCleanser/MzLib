package mz.mzlib.mzlang.lexer.unit;

import mz.mzlib.mzlang.SyntaxException;
import mz.mzlib.mzlang.lexer.Token;
import mz.mzlib.mzlang.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class LexerOperatorUnit extends LexerUnit
{
	public static Set<String> operators=new HashSet<>();
	public static Set<Character> operatorChars=new HashSet<>();
	public static void regOperator(String operator)
	{
		operators.add(operator);
		for(char c:operator.toCharArray())
			operatorChars.add(c);
	}
	static
	{
		regOperator("+");
		regOperator("-");
		regOperator("*");
		regOperator("/");
		regOperator("+=");
		regOperator("-=");
		regOperator("*=");
		regOperator("/=");
		regOperator(".");
		regOperator(";");
		regOperator("(");
		regOperator(")");
		regOperator("[");
		regOperator("]");
		regOperator("{");
		regOperator("}");
		//TODO
	}
	
	public StringBuilder sb=new StringBuilder();
	
	@Override
	public boolean check(char c)
	{
		return operators.contains(sb.toString()+c);
	}
	
	@Override
	public void append(char c) throws SyntaxException
	{
		sb.append(c);
	}
	
	@Override
	public Token toToken() throws SyntaxException
	{
		String s=sb.toString();
		if(!operators.contains(s))
			throw new SyntaxException("Unknown operator "+s);
		return new Token(TokenType.OPERATOR,s);
	}
}
