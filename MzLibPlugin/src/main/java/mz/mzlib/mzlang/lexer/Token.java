package mz.mzlib.mzlang.lexer;

import java.util.Objects;

public class Token
{
	private final TokenType type;
	private final Object value;
	public int lineNum;
	public int columnNum;
	
	public Token(TokenType type, Object value)
	{
		this.type = type;
		this.value = value;
	}
	
	public TokenType getType()
	{
		return type;
	}
	
	public Object getValue() {
		return value;
	}
	
	public Token setPosition(int lineNum,int columnNum)
	{
		this.lineNum=lineNum;
		this.columnNum=columnNum;
		return this;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(o==null || getClass()!=o.getClass())
			return false;
		Token token=(Token)o;
		return type==token.type && Objects.equals(value,token.value);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(type,value);
	}
	
	@Override
	public String toString()
	{
		if(value==null)
			return type.name();
		else
			return type+"["+value+"]";
	}
}