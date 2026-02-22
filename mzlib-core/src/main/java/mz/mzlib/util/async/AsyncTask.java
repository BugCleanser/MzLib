package mz.mzlib.util.async;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

@ApiStatus.Experimental
public class AsyncTask
{
    public static class StopTask extends Throwable
    {
    }

    public CompletableFuture<@Nullable Void> step = CompletableFuture.completedFuture(null);
    public void pause()
    {
        this.step = new CompletableFuture<>();
    }
    public void resume()
    {
        this.step.complete(null);
    }
    public void stop(Throwable exception)
    {
        CompletableFuture<Void> step = new CompletableFuture<>();
        step.completeExceptionally(exception);
        this.step = step;
    }
    public void stop()
    {
        this.stop(new StopTask());
    }
}
