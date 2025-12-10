package mz.mzlib.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static mz.mzlib.util.RuntimeUtil.*;

public class TestRuntimeUtil
{
    @Test
    public void nop$test()
    {
        for(Object ignored : Collections.singleton(null))
        {
            nop();
        }
    }

    @Test
    public void sneakilyThrow$test()
    {
        Assertions.assertThrows(
            Throwable.class, () ->
            {
                Runnable r = () -> sneakilyThrow(new Throwable());
                r.run();
            }
        );
    }

    @Test
    public void sneakilyRun$test()
    {
        Assertions.assertThrows(
            Throwable.class, () ->
                sneakilyRun(() ->
                {
                    throw new Throwable();
                })
        );
    }

    @Test
    public void require$test()
    {
        require(null, Objects::isNull);
        try
        {
            require(null, Objects::nonNull);
        }
        catch(IllegalArgumentException e)
        {
            return;
        }
        assert false;
    }

    @Test
    public void consume$test()
    {
        /*
          It's a variable with unknown type
         */
        List<?> list = new ArrayList<>();
        /*
          You cannot use:
          ```
            list.add(null);
            list.add(list.get(0)); // type error
          ```
         */
        /*
          Capture its type and use it as a parameter of `consume()`
         */
        consume(
            list, l ->
            {
                l.add(null);
                l.add(l.get(0));
            }
        );
        Assertions.assertEquals(2, list.size());
    }

    @Test
    void get$test() throws Throwable
    {
        new get$Test();
    }
    static class get$Test
    {
        @SuppressWarnings("unused")
        Object f = get(() ->
        {
            declaredlyThrow(Throwable.class);
            return new Object();
        });
        get$Test() throws Throwable
        {
        }
    }

    @Test
    void second$test()
    {
        new second$Test();
        Assertions.assertThrowsExactly(RuntimeException.class, () -> new second$Test(null));
        Assertions.assertThrowsExactly(RuntimeException.class, () -> new second$Test(null, null));
    }
    static boolean second$flag = false;
    static class second$Test
    {
        @SuppressWarnings("unused")
        public second$Test(int arg)
        {
            second$flag = true;
        }
        second$Test()
        {
            this(get(() ->
            {
                // You can do sth. here before calling constructor
                Assertions.assertFalse(second$flag);
                return 114514;
            }));
            Assertions.assertTrue(second$flag);
            second$flag = false;
        }
        second$Test(Void ignoredSig)
        {
            this(second(
                valueThrow(new RuntimeException()) // you can do sth. with value here
                , 114514
            ));
        }
        second$Test(Void ignoredSig, Void ignoredSig2)
        {
            this(second(
                array(
                    valueThrow(new RuntimeException()),
                    valueThrow(new RuntimeException()) // you can do more sth. with value here
                ), 114514
            ));
        }
    }
}
