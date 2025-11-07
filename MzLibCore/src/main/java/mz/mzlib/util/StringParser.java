package mz.mzlib.util;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class StringParser
{
    public String string;
    public StringParser(String string)
    {
        this.string = string;
    }

    public int index = 0;

    public char peek() throws ParseException
    {
        if(!this.hasNext())
            throw this.exception();
        return this.string.charAt(this.index);
    }

    public char read() throws ParseException
    {
        char result = this.peek();
        this.index++;
        return result;
    }

    public boolean hasNext()
    {
        return this.index < this.string.length();
    }

    public String readString(Character... terminators) throws ParseException
    {
        return this.readString(Arrays.stream(terminators).collect(Collectors.toSet()));
    }
    public String readString(Set<Character> terminators) throws ParseException
    {
        StringBuilder result = new StringBuilder();
        while(this.hasNext() && !terminators.contains(this.peek()))
        {
            result.append(this.read());
        }
        return result.toString();
    }

    public ParseException exception()
    {
        return new ParseException(this.string, this.index);
    }
    public ParseException exception(Throwable cause)
    {
        ParseException result = this.exception();
        result.initCause(cause);
        return result;
    }
}
