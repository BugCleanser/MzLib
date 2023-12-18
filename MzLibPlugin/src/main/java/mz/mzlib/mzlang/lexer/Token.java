package mz.mzlib.mzlang.lexer;

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
	public String toString()
	{
		if(value==null)
			return type.name();
		else
			return type+"["+value+"]";
	}
}