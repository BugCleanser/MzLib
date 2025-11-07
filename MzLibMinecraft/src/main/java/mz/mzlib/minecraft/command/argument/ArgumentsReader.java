package mz.mzlib.minecraft.command.argument;

public class ArgumentsReader implements Cloneable
{
    public String arguments;
    public int index;

    public ArgumentsReader(String arguments)
    {
        this(arguments, 0);
    }
    public ArgumentsReader(String arguments, int index)
    {
        this.arguments = arguments;
        this.index = index;
    }

    public boolean hasNext()
    {
        return this.index < this.arguments.length();
    }

    public char peek()
    {
        return this.arguments.charAt(this.index);
    }

    public char read()
    {
        return this.arguments.charAt(this.index++);
    }

    public void skipWhitespace()
    {
        while(this.hasNext() && Character.isWhitespace(this.peek()))
        {
            this.read();
        }
    }

    public String readString()
    {
        this.skipWhitespace();
        StringBuilder result = new StringBuilder();
        while(this.hasNext() && !Character.isWhitespace(this.peek()))
        {
            result.append(this.read());
        }
        return result.toString();
    }

    public String readAll()
    {
        String result = this.arguments.substring(this.index);
        this.index = this.arguments.length();
        return result;
    }

    @Override
    public ArgumentsReader clone()
    {
        ArgumentsReader result;
        try
        {
            result = (ArgumentsReader) super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            throw new AssertionError(e);
        }
        return result;
    }
}
