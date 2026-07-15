package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestUnit
{
    @Test
    void testSingleton()
    {
        assertSame(Unit.INSTANCE, Unit.INSTANCE);
    }

    @Test
    void testEquals()
    {
        assertEquals(Unit.INSTANCE, Unit.INSTANCE);
        // any Unit instance equals any other Unit instance via instanceof check
        assertNotEquals(Unit.INSTANCE, "not a unit");
    }

    @Test
    void testHashCode()
    {
        assertEquals(0, Unit.INSTANCE.hashCode());
    }

    @Test
    void testToString()
    {
        assertEquals("()", Unit.INSTANCE.toString());
    }

    @Test
    void testCompareTo()
    {
        assertEquals(0, Unit.INSTANCE.compareTo(Unit.INSTANCE));
    }
}
