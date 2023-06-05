package mz.mzlib.example;

import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.coroutine.Coroutine;
import mz.mzlib.util.coroutine.Yield;
import mz.mzlib.util.coroutine.YieldBreak;

import java.util.PriorityQueue;

public class ExampleCoroutine
{
	public static class TestCoroutine extends Coroutine
	{
		@Override
		public MzModule getModule()
		{
			return null;
		}
		@Override
		public Yield template()
		{
			Object s="Hello World!";
			for(int i=0;i<((String)s).length();i++)
			{
				if(RuntimeUtil.TRUE) return new TestYield(100);
				System.out.print(((String)s).charAt(i));
			}
			return new YieldBreak();
		}
	}
	public static class TestYield implements Yield
	{
		public long timeMillis;
		
		public TestYield(long timeMillis)
		{
			this.timeMillis=timeMillis;
		}
		@Override
		public void run(Coroutine coroutine)
		{
			coroutinePool.add(new TimedCoroutine(System.currentTimeMillis()+timeMillis,coroutine));
			mainThread.interrupt();
			mainThread.resume();
		}
	}
	public static class TimedCoroutine implements Comparable<TimedCoroutine>
	{
		public long time;
		public Coroutine coroutine;
		
		public TimedCoroutine(long time,Coroutine coroutine)
		{
			this.time=time;
			this.coroutine=coroutine;
		}
		
		@Override
		public int compareTo(TimedCoroutine o)
		{
			return Long.compare(o.time,this.time);
		}
	}
	public static Thread mainThread;
	public static PriorityQueue<TimedCoroutine> coroutinePool=new PriorityQueue<>();
	public static void main(String[] args)
	{
		mainThread=Thread.currentThread();
		new TestCoroutine().run();
		while(true)
		{
			try
			{
				if(coroutinePool.isEmpty())
					mainThread.suspend();
				if(System.currentTimeMillis()>=coroutinePool.peek().time)
					coroutinePool.poll().coroutine.run();
				else
					Thread.sleep(coroutinePool.peek().time-System.currentTimeMillis());
			}
			catch(InterruptedException ignored)
			{
			}
		}
	}
}
