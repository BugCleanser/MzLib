package mz.mzlib.demo;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.SleepTicks;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.async.AsyncFunction;

public class ExampleAsyncFunction extends MzModule
{
    public static ExampleAsyncFunction instance = new ExampleAsyncFunction();
    
    @Override
    public void onLoad()
    {
        new Func1().start(MinecraftServer.instance);
        System.out.println("ExampleAsyncFunction is Load");
    }
    
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
}
