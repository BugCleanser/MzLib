package mz.mzlib.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class TestTaskQueue
{
    @Test
    void testScheduleAndRun()
    {
        TaskQueue q = new TaskQueue();
        List<Integer> order = new ArrayList<>();
        q.schedule(() -> order.add(0));
        q.schedule(() -> order.add(1));
        q.schedule(() -> order.add(2));
        q.run();
        assertEquals(java.util.Arrays.asList(0, 1, 2), order);
    }

    @Test
    void testIsEmpty()
    {
        TaskQueue q = new TaskQueue();
        assertTrue(q.isEmpty());
        q.schedule(() -> {});
        assertFalse(q.isEmpty());
        q.run();
        assertTrue(q.isEmpty());
    }

    @Test
    void testExceptionHandling()
    {
        TaskQueue q = new TaskQueue();
        List<Integer> order = new ArrayList<>();
        q.schedule(() -> { throw new RuntimeException("boom"); });
        q.schedule(() -> order.add(42));
        q.run();
        assertEquals(1, order.size());
        assertEquals(42, order.get(0));
    }

    @Test
    void testExecuteMethod()
    {
        TaskQueue q = new TaskQueue();
        AtomicInteger counter = new AtomicInteger(0);
        q.execute(() -> counter.incrementAndGet());
        q.run();
        assertEquals(1, counter.get());
    }

    @Test
    void testNestedSchedule()
    {
        TaskQueue q = new TaskQueue();
        List<Integer> order = new ArrayList<>();
        q.schedule(() ->
        {
            order.add(0);
            q.schedule(() -> order.add(1));
        });
        q.run();
        assertEquals(java.util.Arrays.asList(0, 1), order);
    }

    @Test
    void testEmptyRun()
    {
        TaskQueue q = new TaskQueue();
        assertDoesNotThrow(q::run);
    }
}
