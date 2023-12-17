package mz.mzlib.mzlang.lexer;

public class Token
{
	private final TokenType type;
	private final Object value;
	
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
	
	@Override
	public String toString()
	{
		if(value==null)
			return type.name();
		else
			return type+"["+value+"]";
	}
}