package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestListArray
{
    @Test
    public void testObject()
    {
        String[] array = { "Hello", "World", "awa" };
        //noinspection deprecation
        assertEquals(Arrays.asList(array), ListArray.of(array));
    }
    
    @Test
    public void testInteger()
    {
        int[] array = { 1, 2, 3 };
        assertEquals(Arrays.asList(1, 2, 3), ListArray.of(array));
        assertNotEquals(Arrays.asList(1, 1, 4), ListArray.of(array));
    }
}
