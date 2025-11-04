package mz.mzlib.util.async;

import java.util.concurrent.CompletableFuture;

public class AsyncTask
{
    public static class StopTask extends Throwable
    {
    }

    public CompletableFuture<Void> step = CompletableFuture.completedFuture(null);
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
