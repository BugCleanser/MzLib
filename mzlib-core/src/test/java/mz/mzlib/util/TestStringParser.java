package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class TestStringParser
{
    @Test
    void testPeek() throws Exception
    {
        StringParser p = new StringParser("abc");
        assertEquals('a', p.peek());
        assertEquals('a', p.peek()); // peek doesn't advance
    }

    @Test
    void testRead() throws Exception
    {
        StringParser p = new StringParser("abc");
        assertEquals('a', p.read());
        assertEquals('b', p.read());
        assertEquals('c', p.read());
    }

    @Test
    void testHasNext() throws Exception
    {
        StringParser p = new StringParser("ab");
        assertTrue(p.hasNext());
        p.read();
        assertTrue(p.hasNext());
        p.read();
        assertFalse(p.hasNext());
    }

    @Test
    void testReadPastEnd() throws Exception
    {
        StringParser p = new StringParser("a");
        p.read();
        assertFalse(p.hasNext());
        assertThrows(ParseException.class, p::read);
    }

    @Test
    void testPeekPastEnd()
    {
        StringParser p = new StringParser("");
        assertFalse(p.hasNext());
        assertThrows(ParseException.class, p::peek);
    }

    @Test
    void testReadString() throws Exception
    {
        StringParser p = new StringParser("hello world,");
        assertEquals("hello", p.readString(' ', ','));
        assertEquals(' ', p.read());
        assertEquals("world", p.readString(','));
    }

    @Test
    void testReadStringNoTerminator() throws Exception
    {
        StringParser p = new StringParser("hello");
        assertEquals("hello", p.readString('.'));
        assertFalse(p.hasNext());
    }

    @Test
    void testReadStringEmpty() throws Exception
    {
        StringParser p = new StringParser(",.rest");
        assertEquals("", p.readString(','));
    }

    @Test
    void testException()
    {
        StringParser p = new StringParser("abc");
        ParseException e = p.exception();
        assertEquals("abc", e.getMessage());
        assertEquals(0, e.getErrorOffset());
    }

    @Test
    void testExceptionWithCause()
    {
        StringParser p = new StringParser("abc");
        RuntimeException cause = new RuntimeException("cause");
        ParseException e = p.exception(cause);
        assertEquals(cause, e.getCause());
    }

    @Test
    void testExceptionAfterRead() throws Exception
    {
        StringParser p = new StringParser("abc");
        p.read();
        p.read();
        ParseException e = p.exception();
        assertEquals(2, e.getErrorOffset());
    }
}
