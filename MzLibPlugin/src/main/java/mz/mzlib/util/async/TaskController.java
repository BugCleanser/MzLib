package mz.mzlib.util.async;

import java.util.concurrent.CompletableFuture;

public class TaskController
{
    public CompletableFuture<Void> next=CompletableFuture.completedFuture(null);
    public void pause()
    {
        this.next=new CompletableFuture<>();
    }
    public void resume()
    {
        this.next.complete(null);
    }
}
