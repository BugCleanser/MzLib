package mz.mzlib.example;

import mz.mzlib.minecraft.MinecraftMainThreadRunner;
import mz.mzlib.minecraft.SleepTicks;
import mz.mzlib.util.async.AsyncFunction;

@Deprecated
public class ExampleAsyncFunction
{
    public static class Func1 extends AsyncFunction<Void>
    {
        @Override
        public void run()
        {
        }

        @Override
        public Void template()
        {
            for (int i = 0; i < 10; i++)
            {
                System.out.println("Hello World");
                await(new SleepTicks(20));
            }
            await(new SleepTicks(100));
            System.out.println("Hello World");
            return null;
        }
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
            System.out.println("Hello World");
            await(new SleepTicks(100));
            await0(this.launch(new Func1()));
            System.out.println("Hello World");
            return null;
        }
    }

    public static void main(String[] args)
    {
        new Func1().start(MinecraftMainThreadRunner.instance);
    }
}
