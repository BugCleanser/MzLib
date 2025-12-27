package mz.mzlib.util;

import org.junit.jupiter.api.Test;
import org.mozilla.javascript.Context;

import java.util.function.Consumer;

public class TestJsUtil
{
    Consumer<Runnable> callback;
    public void setCallback(Consumer<Runnable> callback)
    {
        this.callback = callback;
    }

    @Test
    public void test()
    {
        JsUtil.eval(JsUtil.wrap(JsUtil.initScope(), this), "setCallback(r=>r.run())");
        System.out.println(Context.getCurrentContext());
        this.callback.accept(() ->
        {
            System.out.println(Context.getCurrentContext());
        });
    }
}
