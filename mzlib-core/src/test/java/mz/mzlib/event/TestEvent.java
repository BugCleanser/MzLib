package mz.mzlib.event;

import mz.mzlib.MzLib;
import mz.mzlib.module.MzModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class TestEvent
{
    @BeforeEach
    void setUp()
    {
        MzLib.instance.load();
    }

    @AfterEach
    void tearDown()
    {
        MzLib.instance.unload();
    }

    static class MyEvent extends Event
    {
        boolean called = false;

        @Override
        public void call()
        {
            called = true;
        }
    }

    @Test
    void testEventRunLater()
    {
        MyEvent event = new MyEvent();
        List<Integer> order = new ArrayList<>();
        event.runLater(() -> order.add(1));
        event.runLater(() -> order.add(2));
        event.finish();
        assertEquals(java.util.Arrays.asList(1, 2), order);
    }

    @Test
    void testEventIsFinished()
    {
        MyEvent event = new MyEvent();
        assertFalse(event.isFinished());
        event.finish();
        assertTrue(event.isFinished());
    }

    @Test
    void testRunLaterAfterFinish()
    {
        MyEvent event = new MyEvent();
        event.finish();
        assertThrows(IllegalStateException.class, () -> event.runLater(() -> {}));
    }

    @Test
    void testDoubleFinish()
    {
        MyEvent event = new MyEvent();
        event.finish();
        assertThrows(IllegalStateException.class, event::finish);
    }

    static class CancellableEvent extends Event implements Cancellable
    {
        @Override
        public void call()
        {
        }
    }

    @Test
    void testCancellable()
    {
        CancellableEvent event = new CancellableEvent();
        assertFalse(event.isCancelled());
        event.setCancelled(true);
        assertTrue(event.isCancelled());
        event.setCancelled(false);
        assertFalse(event.isCancelled());
    }

    @Test
    void testEventListenerConstruction()
    {
        AtomicInteger counter = new AtomicInteger(0);
        EventListener<MyEvent> listener = new EventListener<>(MyEvent.class, e -> counter.incrementAndGet());
        assertNotNull(listener);
    }

    @Test
    void testEventListenerWithPriority()
    {
        AtomicInteger counter = new AtomicInteger(0);
        EventListener<MyEvent> listener = new EventListener<>(MyEvent.class, 10.0f, e -> counter.incrementAndGet());
        assertNotNull(listener);
    }

    @Test
    void testEventCallBeforeRegistration()
    {
        MyEvent event = new MyEvent();
        event.call();
        assertTrue(event.called);
    }

}
