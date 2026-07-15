package mz.mzlib.data;

import mz.mzlib.MzLib;
import mz.mzlib.module.MzModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestData
{
    // Holder type: a simple map-backed object
    static class PlayerData
    {
        Map<String, Object> data = new HashMap<>();

        boolean hasProperty(String key)
        {
            return data.containsKey(key);
        }

        void setProperty(String key, Object value)
        {
            data.put(key, value);
        }

        Object getProperty(String key)
        {
            return data.get(key);
        }
    }

    DataKey<PlayerData, Integer, Integer> HEALTH_KEY = new DataKey<>("health");

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

    @Test
    void testDataKeyNoHandler()
    {
        DataKey<PlayerData, String, String> key = new DataKey<>("test");
        assertThrows(IllegalStateException.class, () -> key.check(new PlayerData()));
    }

    @Test
    void testDataHandlerRegistration()
    {
        MzModule module = new MzModule();
        module.load();

        DataHandler<PlayerData, Integer, Integer> handler = DataHandler.<PlayerData, Integer, Integer>builder(HEALTH_KEY)
            .checker(p -> p.hasProperty("health"))
            .getter(p -> (Integer) p.getProperty("health"))
            .setter((p, v) -> p.setProperty("health", v))
            .reviserGetter(v -> v)
            .reviserApplier(v -> v)
            .build();

        module.register(handler);

        PlayerData player = new PlayerData();
        assertFalse(HEALTH_KEY.check(player));
        player.setProperty("health", 100);
        assertTrue(HEALTH_KEY.check(player));
        assertEquals(100, HEALTH_KEY.get(player));

        HEALTH_KEY.set(player, 50);
        assertEquals(50, player.getProperty("health"));

        module.unload();
    }

    @Test
    void testDataHandlerLifecycle()
    {
        MzModule module = new MzModule();
        module.load();

        DataHandler<PlayerData, Integer, Integer> handler = DataHandler.<PlayerData, Integer, Integer>builder(HEALTH_KEY)
            .checker(p -> p.hasProperty("health"))
            .getter(p -> (Integer) p.getProperty("health"))
            .setter((p, v) -> p.setProperty("health", v))
            .build();

        module.register(handler);

        PlayerData player = new PlayerData();
        player.setProperty("health", 100);
        assertEquals(100, HEALTH_KEY.get(player));

        // After unload, the key should have no handler
        module.unload();

        PlayerData p2 = new PlayerData();
        assertThrows(IllegalStateException.class, () -> HEALTH_KEY.check(p2));
    }

    @Test
    void testBuilderWithoutGetter()
    {
        assertThrows(IllegalStateException.class, () ->
            DataHandler.<PlayerData, Integer, Integer>builder(HEALTH_KEY)
                .setter((p, v) -> p.setProperty("health", v))
                .build());
    }

    @Test
    void testBuilderWithoutSetter()
    {
        assertThrows(IllegalStateException.class, () ->
            DataHandler.<PlayerData, Integer, Integer>builder(HEALTH_KEY)
                .getter(p -> (Integer) p.getProperty("health"))
                .build());
    }

    @Test
    void testMultipleRegistrations()
    {
        MzModule module1 = new MzModule();
        MzModule module2 = new MzModule();
        module1.load();
        module2.load();

        DataHandler<PlayerData, Integer, Integer> handler1 = DataHandler.<PlayerData, Integer, Integer>builder(HEALTH_KEY)
            .checker(p -> true)
            .getter(p -> (Integer) p.getProperty("health"))
            .setter((p, v) -> p.setProperty("health", v))
            .build();

        DataHandler<PlayerData, Integer, Integer> handler2 = DataHandler.<PlayerData, Integer, Integer>builder(HEALTH_KEY)
            .checker(p -> true)
            .getter(p -> 999) // always 999
            .setter((p, v) -> p.setProperty("health", v))
            .build();

        module1.register(handler1);
        module2.register(handler2);

        PlayerData player = new PlayerData();
        player.setProperty("health", 100);
        // The last registered handler wins
        assertEquals(999, HEALTH_KEY.get(player));

        module2.unload();
        // Now handler1 should be active again
        assertEquals(100, HEALTH_KEY.get(player));

        module1.unload();
    }
}
