package mz.mzlib.test;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Tester<C extends TestContext>
{
    String getName();
    Class<C> getContextType();
    CompletableFuture<List<Throwable>> test(C context);
}
