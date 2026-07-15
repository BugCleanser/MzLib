package mz.mzlib.module;

import mz.mzlib.MzLib;
import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestModule
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

    @Test
    void testModuleLoadUnload()
    {
        MzModule module = new MzModule();
        assertFalse(module.isLoaded());

        module.load();
        assertTrue(module.isLoaded());
        assertTrue(module.future.isDone());

        module.unload();
        assertFalse(module.isLoaded());
    }

    @Test
    void testModuleDoubleLoad()
    {
        MzModule module = new MzModule();
        module.load();
        assertThrows(IllegalStateException.class, module::load);
    }

    @Test
    void testModuleUnloadWithoutLoad()
    {
        MzModule module = new MzModule();
        assertThrows(IllegalStateException.class, module::unload);
    }

    @Test
    void testRegisterUnregisterModule()
    {
        MzModule parent = new MzModule();
        MzModule child = new MzModule();
        parent.load();
        parent.register(child);
        assertTrue(child.isLoaded());
        parent.unregister(child);
        assertFalse(child.isLoaded());
        parent.unload();
    }

    @Test
    void testRegisterInstance()
    {
        MzModule module = new MzModule();
        module.load();
        module.register(new MyTestInstanceImpl("test"));
        assertNotNull(MyTestInstance.instance);
        module.unload();
        assertNull(MyTestInstance.instance);
    }

    @Test
    void testOnLoadUnloadCallbacks()
    {
        boolean[] loaded = {false};
        boolean[] unloaded = {false};

        MzModule module = new MzModule()
        {
            @Override
            public void onLoad()
            {
                loaded[0] = true;
            }

            @Override
            public void onUnload()
            {
                unloaded[0] = true;
            }
        };

        module.load();
        assertTrue(loaded[0]);
        assertFalse(unloaded[0]);

        module.unload();
        assertTrue(unloaded[0]);
    }

    @Test
    void testAutoRegisterSubmodule()
    {
        MzModule parent = new MzModule();
        MzModule child = new MzModule();
        parent.load();
        parent.register(child);
        assertTrue(child.isLoaded());
        parent.unload();
        assertFalse(child.isLoaded());
    }

    @Test
    void testInstanceStacking()
    {
        MzModule module1 = new MzModule();
        MzModule module2 = new MzModule();
        module1.load();
        module2.load();

        MyTestInstance impl1 = new MyTestInstanceImpl("a");
        MyTestInstance impl2 = new MyTestInstanceImpl("b");

        module1.register(impl1);
        assertSame(impl1, MyTestInstance.instance);

        module2.register(impl2);
        assertSame(impl2, MyTestInstance.instance);

        // Unregister newest -> falls back to previous
        module2.unregister(impl2);
        assertSame(impl1, MyTestInstance.instance);

        // Unregister last -> null
        module1.unregister(impl1);
        assertNull(MyTestInstance.instance);

        module2.unload();
        module1.unload();
    }

    interface MyTestInstance extends Instance
    {
        MyTestInstance instance = RuntimeUtil.nul();
    }

    static class MyTestInstanceImpl implements MyTestInstance
    {
        public static MyTestInstance instance = null;
        final String name;

        MyTestInstanceImpl(String name)
        {
            this.name = name;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }
}
