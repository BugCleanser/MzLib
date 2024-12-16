package mz.mzlib.example;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.SleepTicks;
import mz.mzlib.util.async.AsyncFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Deprecated
public class ExampleAsyncFunction
{
    public static class Func2 extends AsyncFunction<Void>
    {
        @Override
        public void run()
        {
        }
        
        @Override
        public Void template()
        {
            System.out.println("Hello World2");
            await(new SleepTicks(100));
            System.out.println("Hello World2");
            return null;
        }
    }
    
    public static class Func1 extends AsyncFunction<Void>
    {
        @Override
        public void run()
        {
        }
        
        @Override
        public Void template()
        {
            for(int i = 0; i<10; i++)
            {
                System.out.println("Hello World");
                await(new SleepTicks(20));
            }
            await0(new Func2().start(this.getRunner()));
            await(new SleepTicks(100));
            System.out.println("Hello World");
            return null;
        }
    }
    
    @Test
    public void test()
    {
        assertThrows(NullPointerException.class, ()->
        {
            new Func1().start(MinecraftServer.instance);
        });
    }
}
