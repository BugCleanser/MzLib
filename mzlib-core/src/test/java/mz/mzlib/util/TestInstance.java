package mz.mzlib.util;

import mz.mzlib.MzLib;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestInstance
{
    interface MyInstance extends Instance
    {
        /**
         * Do not use `null`.
         * Use `RuntimeUtil.nul()` instead.
         * Fields in an interface are implicitly final.
         * They will be inlined.
         */
        MyInstance instance = RuntimeUtil.nul();
    }

    static class MyInstanceImpl implements MyInstance
    {
        /**
         * You can use `null` if the field is not final.
         * However, it is suggested to use `final` where possible.
         */
        public static MyInstance instance = null;
    }

    @Test
    void test()
    {
        MzLib.instance.load();

        MzLib.instance.register(new MyInstanceImpl());
        Assertions.assertNotNull(MyInstance.instance);
        Assertions.assertNotNull(MyInstanceImpl.instance);

        MzLib.instance.unload();
    }
}
