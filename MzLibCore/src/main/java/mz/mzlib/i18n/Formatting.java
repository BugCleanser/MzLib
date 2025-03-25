package mz.mzlib.i18n;

import mz.mzlib.util.*;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Deprecated
public class Formatting
{
    public StringParser parser;
    public Map<String, Object> args;
    
    public Formatting(String format, Map<String, Object> args)
    {
        this.parser = new StringParser(format);
        this.args = args;
    }
    
    public static Set<Character> operators = new HashSet<>(Arrays.asList('.', ',', ';', '\'', '"', '`', '(', ')', '[', ']', '{', '}'));
    
    public String parseString() throws ParseException
    {
        char terminator = this.parser.read();
        if(terminator!='\'' && terminator!='"' && terminator!='`')
            throw this.parser.exception();
        StringBuilder sb = new StringBuilder();
        while(this.parser.peek()!=terminator)
        {
            if(this.parser.peek()=='\\')
                this.parser.read();
            sb.append(this.parser.read());
        }
        this.parser.read();
        return sb.toString();
    }
    
    public Object parseObject() throws ParseException
    {
        Object result;
        switch(this.parser.peek())
        {
            case '"':
            case '\'':
            case '`':
                result = new Formatting(this.parseString(), this.args).parse();
                break;
            case '(':
                this.parser.read();
                result = this.parseObject();
                if(this.parser.read()!=')')
                    throw this.parser.exception();
                break;
            default:
                String token = this.parser.readString(operators);
                switch(token)
                {
                    case "null":
                        result = null;
                        break;
                    case "true":
                        result = true;
                        break;
                    case "false":
                        result = false;
                        break;
                    default:
                        result = this.args.get(token);
                        break;
                }
                break;
        }
        a:
        while(this.parser.hasNext())
        {
            switch(this.parser.peek())
            {
                case '.':
                    this.parser.read();
                    if(result==null)
                        throw this.parser.exception(new NullPointerException());
                    String token = this.parser.readString(operators);
                    if(result instanceof Function || result instanceof Map)
                    {
                        try
                        {
                            result = map(result, token);
                        }
                        catch(Throwable e)
                        {
                            throw this.parser.exception(e);
                        }
                    }
                    else if(result instanceof String)
                    {
                        switch(token)
                        {
                            case "length":
                                result = ((String)result).length();
                                break;
                            default:
                                throw this.parser.exception();
                        }
                    }
                    else if(result.getClass().isArray() || result instanceof Collection)
                    {
                        switch(token)
                        {
                            case "map":
                                if(this.parser.read()!='(')
                                    throw this.parser.exception();
                                Object param = parseObject();
                                if(this.parser.read()!=',')
                                    throw this.parser.exception();
                                Object action = parseObject();
                                if(!(action instanceof String))
                                    throw this.parser.exception(new IllegalArgumentException());
                                if(this.parser.read()!=')')
                                    throw this.parser.exception();
                                result = (result instanceof Collection ? ((Collection<?>)result).stream() : Arrays.stream(CollectionUtil.toObjectArray(result))).map(((ThrowableFunction<Object, String, ParseException>)it->new Formatting((String)action, MapBuilder.hashMap().put(param, it).get()).parse())).collect(Collectors.toList());
                                break;
                            case "join":
                                if(this.parser.read()!='(')
                                    throw this.parser.exception();
                                String delimiter = Objects.toString(parseObject());
                                if(this.parser.read()!=')')
                                    throw this.parser.exception();
                                if(result instanceof String[])
                                    result = String.join(delimiter, (String[])result);
                                else if(result instanceof Collection)
                                    result = ((Collection<?>)result).stream().map(Object::toString).collect(Collectors.joining(delimiter));
                                else
                                    result = Arrays.stream(CollectionUtil.toObjectArray(result)).map(Object::toString).collect(Collectors.joining());
                                break;
                            default:
                                throw this.parser.exception();
                        }
                    }
                    else
                        throw this.parser.exception();
                    break;
                case '[':
                    this.parser.read();
                    Object index = this.parseObject();
                    if(result==null)
                        throw this.parser.exception(new NullPointerException());
                    if(this.parser.read()!=']')
                        throw this.parser.exception();
                    if(result instanceof String)
                    {
                        if(!(index instanceof Number))
                            throw this.parser.exception();
                        result = ((String)result).charAt(((Number)index).intValue());
                    }
                    else if(result.getClass().isArray())
                    {
                        if(!(index instanceof Number))
                            throw this.parser.exception();
                        result = Array.get(result, ((Number)index).intValue());
                    }
                    else if(result instanceof List)
                    {
                        if(!(index instanceof Number))
                            throw this.parser.exception();
                        result = ((List<?>)result).get(((Number)index).intValue());
                    }
                    else if(result instanceof Map || result instanceof Function)
                    {
                        try
                        {
                            result = map(result, index);
                        }
                        catch(Throwable e)
                        {
                            throw this.parser.exception(e);
                        }
                    }
                    else
                        throw this.parser.exception();
                    break;
                default:
                    break a;
            }
        }
        return result;
    }
    
    public String parse() throws ParseException
    {
        StringBuilder result = new StringBuilder();
        while(this.parser.hasNext())
        {
            if(this.parser.peek()!='$')
            {
                result.append(this.parser.read());
                continue;
            }
            this.parser.read();
            switch(this.parser.read())
            {
                case '$':
                    result.append('$');
                    break;
                case '{':
                    if(this.parser.peek()=='}')
                    {
                        parser.read();
                        continue;
                    }
                    Object o = parseObject();
                    String f = "s";
                    if(this.parser.peek()==';')
                    {
                        this.parser.read();
                        f = this.parser.readString('}');
                    }
                    if(this.parser.read()!='}')
                        throw this.parser.exception();
                    try
                    {
                        result.append(String.format("%"+f, o));
                    }
                    catch(IllegalFormatException e)
                    {
                        throw this.parser.exception(e);
                    }
                    break;
                default:
                    throw this.parser.exception();
            }
        }
        return result.toString();
    }
    
    public static Object map(Object mapping, Object key)
    {
        if(mapping instanceof Map)
            return ((Map<?, ?>)mapping).get(key);
        else if(mapping instanceof Function)
            return ((Function<?, ?>)mapping).apply(RuntimeUtil.cast(key));
        else
            throw new IllegalArgumentException();
    }
}
